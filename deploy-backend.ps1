# ============================================================
#  TreasureLife 后端一键部署脚本 (PowerShell 7)
#  流程：删旧备份 -> 备份当前 jar -> 上传本地 jar -> 替换部署 -> 验证
#  说明：通过 SSH 免密别名连接，脚本内不含任何服务器 IP / 账号 / 密码
#  用法：在项目根目录执行  ./deploy-backend.ps1
# ============================================================

$ErrorActionPreference = "Stop"

# ===================== 配置区 =====================
$SshHost   = "root@self"                              # SSH 免密别名（root 用户）
$RemoteDir = "/home/lx/java"                          # 服务器部署目录
$JarName   = "TreasureLife-0.0.1-SNAPSHOT.jar"        # jar 文件名（与 start.sh 中一致）
$LocalJar  = Join-Path $PSScriptRoot "server\target\$JarName"   # 本地 jar 路径
$SshOpts   = @("-o", "BatchMode=yes", "-o", "ConnectTimeout=20")
# ================================================

function Fail($msg) {
    Write-Host "`n[X] $msg" -ForegroundColor Red
    exit 1
}

# ---------- 前置检查 ----------
if (-not (Test-Path $LocalJar)) {
    Fail "本地 jar 不存在：$LocalJar`n    请先执行打包：cd server; mvn clean package -Pprod -DskipTests"
}
$localSize = (Get-Item $LocalJar).Length
Write-Host "本地 jar : $LocalJar"       -ForegroundColor Cyan
Write-Host ("大小     : {0:N0} 字节" -f $localSize) -ForegroundColor Cyan
Write-Host ("目标     : {0}:{1}/{2}" -f $SshHost, $RemoteDir, $JarName) -ForegroundColor Cyan

# ---------- 1. 删除旧备份 + 备份当前运行的 jar ----------
Write-Host "`n[1/4] 删除旧备份并备份当前 jar ..." -ForegroundColor Yellow
ssh @SshOpts $SshHost "cd $RemoteDir && rm -f $JarName.bak $JarName.prev && if [ -f $JarName ]; then cp -f $JarName $JarName.bak && echo '  已备份: $JarName -> $JarName.bak'; else echo '  当前无 jar, 跳过备份'; fi"
if ($LASTEXITCODE -ne 0) { Fail "备份步骤失败" }

# ---------- 2. 上传本地 jar（先传临时名） ----------
Write-Host "`n[2/4] 上传本地 jar (临时文件 $JarName.new) ..." -ForegroundColor Yellow
scp @SshOpts $LocalJar "${SshHost}:$RemoteDir/$JarName.new"
if ($LASTEXITCODE -ne 0) { Fail "上传失败" }

# ---------- 3. 校验大小 -> 原子替换 -> 执行 start.sh ----------
Write-Host "`n[3/4] 校验大小并部署 ..." -ForegroundColor Yellow
$remoteSize = (ssh @SshOpts $SshHost "stat -c %s $RemoteDir/$JarName.new").Trim()
if ("$remoteSize" -ne "$localSize") {
    ssh @SshOpts $SshHost "rm -f $RemoteDir/$JarName.new" | Out-Null
    Fail "上传大小不一致 (本地=$localSize 远程=$remoteSize)，已清理临时文件，部署中止"
}
Write-Host "  大小校验通过 ($remoteSize 字节)" -ForegroundColor Green
ssh @SshOpts $SshHost "cd $RemoteDir && mv -f $JarName.new $JarName && ./start.sh"
if ($LASTEXITCODE -ne 0) { Fail "部署 (start.sh) 执行失败" }

# ---------- 4. 验证启动 ----------
Write-Host "`n[4/4] 等待并验证启动日志 ..." -ForegroundColor Yellow
ssh @SshOpts $SshHost "sleep 14 && echo '--- app.log 尾部 ---' && tail -n 18 $RemoteDir/app.log && echo '--- 进程 ---' && (ps -ef | grep $JarName | grep -v grep)"

Write-Host "`n[OK] 部署流程执行完毕。若日志出现 'Started TreasureLifeApplication' 即启动成功。" -ForegroundColor Green
Write-Host "     回滚方法: ssh $SshHost `"cd $RemoteDir && cp -f $JarName.bak $JarName && ./start.sh`"" -ForegroundColor DarkGray
