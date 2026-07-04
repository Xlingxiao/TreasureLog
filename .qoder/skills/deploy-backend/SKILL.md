---
name: deploy-backend
description: Package and deploy the TreasureLife Spring Boot backend to the remote server. Runs Maven prod build, backs up the running jar, uploads the new jar over SSH, and restarts via start.sh. Use when the user asks to build, package, deploy, publish, or release the backend / 后台 / server jar.
---

# 后端打包 + 部署

一键完成 TreasureLife 后端的 **Maven 打包** 与 **远程部署**。通过 SSH 免密别名连接服务器，全程不涉及服务器 IP / 账号 / 密码。

## 前提条件

- 本机已配置 SSH 免密别名 `self`，且可用 `root@self` 免密登录（`ssh -o BatchMode=yes root@self` 不提示密码）
- 服务器部署目录 `/home/lx/java` 下已存在 `start.sh`（负责停旧进程、`nohup java -jar` 启动、写 `app.log`）
- 本机安装 JDK 8（打包用）；服务器 JDK 11 可运行 JDK8 编译的 jar

## 执行流程

运行脚本即可（在项目根目录）：

```bash
pwsh .qoder/skills/deploy-backend/scripts/deploy-backend.ps1
```

跳过打包、直接部署已有 jar：

```bash
pwsh .qoder/skills/deploy-backend/scripts/deploy-backend.ps1 -SkipBuild
```

脚本按顺序执行，任一步失败即中止：

```
- [ ] 0. Maven 打包        mvnw clean package -Pprod -DskipTests
- [ ] 1. 删旧备份 + 备份当前运行的 jar 为 .bak
- [ ] 2. 上传本地 jar（临时名 .new）
- [ ] 3. 校验大小一致 -> 原子替换 -> 执行 ./start.sh
- [ ] 4. 等待 14s -> 输出 app.log 尾部与进程状态
```

## 成功判定

- `app.log` 出现 `Started TreasureLifeApplication in X seconds`
- 进程列表可见 `java -jar TreasureLife-0.0.1-SNAPSHOT.jar`
- Tomcat 监听端口 `8928`，profile `prod` 生效

## 回滚

部署后如需回滚到上一版本：

```bash
ssh root@self "cd /home/lx/java && cp -f TreasureLife-0.0.1-SNAPSHOT.jar.bak TreasureLife-0.0.1-SNAPSHOT.jar && ./start.sh"
```

## 关键配置（如环境变化需同步修改脚本配置区）

| 项 | 值 |
|----|----|
| SSH 别名 | `root@self` |
| 部署目录 | `/home/lx/java` |
| jar 名 | `TreasureLife-0.0.1-SNAPSHOT.jar` |
| 打包命令 | `mvnw clean package -Pprod -DskipTests` |
| 启动脚本 | 服务器 `/home/lx/java/start.sh` |
