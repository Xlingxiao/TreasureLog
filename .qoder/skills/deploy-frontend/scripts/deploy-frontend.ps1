# ============================================================
#  TreasureLife 前端「打包 + 部署」一键脚本 (PowerShell 7)
#  流程：npm build -> zip 打包 dist -> 删旧备份 -> 备份现有 zip -> 上传 -> 覆盖解压
#  说明：通过 SSH 免密别名连接，脚本内不含任何服务器 IP / 账号 / 密码
#  用法：
#     pwsh .qoder/skills/deploy-frontend/scripts/deploy-frontend.ps1
#     pwsh .qoder/skills/deploy-frontend/scripts/deploy-frontend.ps1 -SkipBuild   # 跳过 npm build，用已有 dist
#     pwsh .qoder/skills/deploy-frontend/scripts/deploy-frontend.ps1 -Clean       # 解压前清空构建目录(css/js/img/fonts)，清除旧 hash 残留
# ============================================================

param(
    [switch]$SkipBuild,
    [switch]$Clean
)

$ErrorActionPreference = "Stop"
# 修复 PowerShell 控制台中文乱码
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8

# ===================== 配置区 =====================
$SshHost   = "root@self"                     # SSH 免密别名（root 用户）
$RemoteDir = "/usr/share/nginx/html"         # 前端部署目录（nginx 站点根）
$ZipName   = "dist.zip"                       # 服务器上的 zip 文件名
$SshOpts   = @("-o", "BatchMode=yes", "-o", "ConnectTimeout=20")

# 定位项目根目录（scripts -> deploy-frontend -> skills -> .qoder -> 项目根）
$ProjectRoot = (Resolve-Path (Join-Path $PSScriptRoot "..\..\..\..")).Path
$ClientDir   = Join-Path $ProjectRoot "client"
$DistDir     = Join-Path $ClientDir "dist"
$LocalZip    = Join-Path $ClientDir $ZipName
# ================================================

function Fail($msg) {
    Write-Host "`n[X] $msg" -ForegroundColor Red
    exit 1
}

# ---------- 0a. npm build ----------
if (-not $SkipBuild) {
    Write-Host "`n[0/5] npm run build ..." -ForegroundColor Yellow
    Push-Location $ClientDir
    try {
        & npm.cmd run build
        if ($LASTEXITCODE -ne 0) { Fail "npm build 失败" }
    } finally {
        Pop-Location
    }
} else {
    Write-Host "`n[0/5] 已指定 -SkipBuild，跳过 npm build" -ForegroundColor DarkGray
}

if (-not (Test-Path (Join-Path $DistDir "index.html"))) {
    Fail "dist 未生成：$DistDir`n    请去掉 -SkipBuild 重新构建"
}

# ---------- 1. 本地 zip 打包整个 dist 文件夹（包含顶层目录） ----------
Write-Host "`n[1/5] 打包 dist -> $ZipName ..." -ForegroundColor Yellow
if (Test-Path $LocalZip) { Remove-Item $LocalZip -Force }
# 打包 dist 文件夹本身，而不是里面的内容
Compress-Archive -Path $DistDir -DestinationPath $LocalZip -Force
$localSize = (Get-Item $LocalZip).Length
Write-Host "  已生成 $ZipName ($localSize 字节)" -ForegroundColor Green

# ---------- 2. 删除旧备份 + 备份现有 zip ----------
Write-Host "`n[2/5] 删除旧备份并备份现有 $ZipName ..." -ForegroundColor Yellow
ssh @SshOpts $SshHost "cd $RemoteDir && rm -f $ZipName.bak && if [ -f $ZipName ]; then cp -f $ZipName $ZipName.bak && echo '  已备份: $ZipName -> $ZipName.bak'; else echo '  当前无 $ZipName, 跳过备份'; fi"
if ($LASTEXITCODE -ne 0) { Fail "备份步骤失败" }

# ---------- 3. 上传 zip（先传临时名，校验大小后原子替换） ----------
Write-Host "`n[3/5] 上传 $ZipName (临时文件 $ZipName.new) ..." -ForegroundColor Yellow
scp @SshOpts $LocalZip "${SshHost}:$RemoteDir/$ZipName.new"
if ($LASTEXITCODE -ne 0) { Fail "上传失败" }
$remoteSize = (ssh @SshOpts $SshHost "stat -c %s $RemoteDir/$ZipName.new").Trim()
if ("$remoteSize" -ne "$localSize") {
    ssh @SshOpts $SshHost "rm -f $RemoteDir/$ZipName.new" | Out-Null
    Fail "上传大小不一致 (本地=$localSize 远程=$remoteSize)，已清理临时文件，部署中止"
}
ssh @SshOpts $SshHost "cd $RemoteDir && mv -f $ZipName.new $ZipName"
if ($LASTEXITCODE -ne 0) { Fail "替换 $ZipName 失败" }
Write-Host "  上传并校验通过 ($remoteSize 字节)" -ForegroundColor Green

# ---------- 4. 覆盖解压到部署目录（解压后得到 dist 文件夹） ----------
Write-Host "`n[4/5] 覆盖解压 $ZipName -> $RemoteDir ..." -ForegroundColor Yellow
if ($Clean) {
    # 清空时直接删除整个 dist 目录，避免残留
    Write-Host "  -Clean: 先删除 $RemoteDir/dist" -ForegroundColor DarkYellow
    ssh @SshOpts $SshHost "cd $RemoteDir && rm -rf dist"
    if ($LASTEXITCODE -ne 0) { Fail "删除 dist 目录失败" }
}
ssh @SshOpts $SshHost "cd $RemoteDir && unzip -o $ZipName -d $RemoteDir >/dev/null && echo '  解压完成 (dist 文件夹已生成)'"

# ---------- 5. 验证 ----------
Write-Host "`n[5/5] 验证部署结果 ..." -ForegroundColor Yellow
ssh @SshOpts $SshHost "cd $RemoteDir && echo '--- index.html ---' && ls -la index.html && echo '--- 关键目录 ---' && ls -la css js img 2>/dev/null | head -n 20"

Write-Host "`n[OK] 前端打包+部署流程执行完毕。" -ForegroundColor Green
Write-Host "     回滚方法: ssh $SshHost `"cd $RemoteDir && cp -f $ZipName.bak $ZipName && unzip -o $ZipName -d $RemoteDir`"" -ForegroundColor DarkGray
