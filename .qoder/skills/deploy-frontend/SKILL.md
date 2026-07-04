---
name: deploy-frontend
description: Package and deploy the TreasureLife Vue frontend to the remote nginx server. Runs npm build, zips the dist folder, backs up the existing zip, uploads over SSH, and overwrite-extracts into the nginx web root. Use when the user asks to build, package, deploy, publish, or release the frontend / 前端 / dist / web static assets.
---

# 前端打包 + 部署

一键完成 TreasureLife 前端的 **npm 构建** 与 **远程部署**（zip 上传 + 覆盖解压到 nginx 站点根）。通过 SSH 免密别名连接，全程不涉及服务器 IP / 账号 / 密码。

## 前提条件

- 本机已配置 SSH 免密别名 `self`，可用 `root@self` 免密登录
- 本机安装 Node.js / npm（构建 dist）
- 服务器已安装 `unzip`，部署目录 `/usr/share/nginx/html` 为 nginx 站点根
- 该目录除前端资源外还存有用户自有文件（如 `portfolio_*.html`），部署用 `unzip -o` **只覆盖同名文件**，不清空目录

## 执行流程

在项目根目录运行：

```bash
pwsh .qoder/skills/deploy-frontend/scripts/deploy-frontend.ps1
```

跳过 npm build、用已有 dist 部署：

```bash
pwsh .qoder/skills/deploy-frontend/scripts/deploy-frontend.ps1 -SkipBuild
```

干净部署（解压前清空 `css/js/img/fonts` 等构建目录，清除旧 hash 残留文件；保留根目录下你的自有文件）：

```bash
pwsh .qoder/skills/deploy-frontend/scripts/deploy-frontend.ps1 -Clean
```

脚本按顺序执行，任一步失败即中止：

```
- [ ] 0. npm run build            生成 client/dist
- [ ] 1. zip 打包 dist 内容        -> client/dist.zip（不含顶层 dist 目录）
- [ ] 2. 删旧备份 dist.zip.bak + 将服务器现有 dist.zip 备份为 dist.zip.bak
- [ ] 3. 上传 dist.zip（临时名 .new，校验大小后原子替换）
- [ ] 4. unzip -o 覆盖解压到 /usr/share/nginx/html
- [ ] 5. 验证 index.html 与关键目录
```

## 关键设计

- **zip 内容**：打包 `dist/*`（index.html、css/、js/、img/、fonts/…），解压后直接落在站点根，与现有部署结构一致
- **覆盖而非清空**：`unzip -o` 仅覆盖 zip 内的同名文件，保留目录中其他自有文件

## 回滚

```bash
ssh root@self "cd /usr/share/nginx/html && cp -f dist.zip.bak dist.zip && unzip -o dist.zip -d /usr/share/nginx/html"
```

## 关键配置（如环境变化需同步修改脚本配置区）

| 项 | 值 |
|----|----|
| SSH 别名 | `root@self` |
| 部署目录 | `/usr/share/nginx/html` |
| zip 名 | `dist.zip` |
| 构建命令 | `npm run build`（产物 `client/dist`） |
| 解压命令 | `unzip -o dist.zip -d /usr/share/nginx/html` |
