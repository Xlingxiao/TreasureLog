# TreasureLog - 个人财富管理系统的

[![Java](https://img.shields.io/badge/Java-8-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.2.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Vue](https://img.shields.io/badge/Vue-2.6-green.svg)](https://vuejs.org/)
[![MyBatis](https://img.shields.io/badge/MyBatis-2.2.2-blue.svg)](https://mybatis.org/mybatis-3/)

TreasureLog 是一个用于记录和管理个人财富分布的全栈应用程序。通过该系统，用户可以记录收入支出、投资情况、信用卡信息等多种财务数据，并提供财富曲线分析功能。

## 项目结构

```
TreasureLog/
├── server/          # 后端服务 (Spring Boot)
│   ├── src/
│   │   └── main/
│   │       ├── java/com/lx/treasure/
│   │       │   ├── module/          # 业务模块
│   │       │   │   ├── login/       # 用户登录模块
│   │       │   │   ├── treasure/    # 财富管理核心模块
│   │       │   │   ├── creditcard/  # 信用卡管理模块
│   │       │   │   └── TextRalation/# 文本记录模块
│   │       │   ├── config/          # 配置类
│   │       │   ├── bean/            # 数据对象
│   │       │   └── common/          # 通用组件
│   │       └── resources/           # 配置文件
│   └── pom.xml
├── client/          # 前端应用 (Vue.js)
│   ├── src/
│   ├── package.json
│   └── ...
└── README.md
```

## 技术栈

### 后端 (Server)

| 技术 | 版本 | 说明 |
|------|------|------|
| Java | 8 | 开发语言 |
| Spring Boot | 2.2.4.RELEASE | 应用框架 |
| Spring Boot Web | - | Web 服务 |
| Spring Boot Data JPA | - | 数据访问 |
| MyBatis | 2.2.2 | ORM 框架 |
| MySQL | - | 数据库 |
| JWT (jjwt) | 0.10.5 | 身份认证 |
| Lombok | 1.18.8 | 代码简化 |
| FastJSON | 1.2.70 | JSON 处理 |

### 前端 (Client)

| 技术 | 版本 | 说明 |
|------|------|------|
| Vue | 2.6.11 | 前端框架 |
| Vue Router | 3.3.4 | 路由管理 |
| Vuex | 3.5.1 | 状态管理 |
| Element UI | 2.13.2 | UI 组件库 |
| Axios | 0.19.2 | HTTP 客户端 |
| ECharts | 4.8.0 | 数据可视化 |
| Crypto-JS | 4.1.1 | 加密处理 |

## 功能模块

### 核心功能

- **用户认证**: 基于 JWT 的用户登录和身份验证
- **财富记录**: 记录和管理个人收入、支出信息
- **投资追踪**: 管理投资记录和收益情况
- **信用卡管理**: 记录信用卡账单和还款信息
- **财富曲线**: 可视化展示财富变化趋势
- **支出分析**: 分析主要支出类别和分布

### API 接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/login` | POST | 用户登录 |
| `/getWealthCurve` | POST | 获取财富曲线数据 |
| `/insert/ordinary` | POST | 插入普通记录 |
| `/getChannels` | POST | 获取用户渠道信息 |
| `/getMainExpend` | POST | 获取主要支出 |
| `/getIncomeInfoList` | POST | 获取所有收支记录 |
| `/getTreasureStatus` | POST | 获取财富渠道状态 |
| `/getTreasureClassInfo` | POST | 获取财富分类信息 |
| `/insert/complete` | POST | 添加完成日志 |
| `/init` | POST | 初始化字段 |

## 快速开始

### 环境要求

- JDK 1.8+
- Maven 3.6+
- MySQL 5.7+
- Node.js 12+
- npm 6+

### 后端启动

1. 克隆项目
```bash
git clone <repository-url>
cd TreasureLog/server
```

2. 配置数据库
编辑 `src/main/resources/application-dev.yml` 或 `src/main/resources/application-prod.yml`，配置 MySQL 连接信息。

3. 构建并运行
```bash
# 开发环境
mvn spring-boot:run -Pdev

# 或打包后运行
mvn clean package -Pdev
java -jar target/TreasureLife-0.0.1-SNAPSHOT.jar
```

### 前端启动

1. 进入前端目录
```bash
cd TreasureLog/client
```

2. 安装依赖
```bash
npm install
```

3. 启动开发服务器
```bash
npm run serve
```

4. 构建生产版本
```bash
npm run build
```

## 配置文件说明

项目支持多环境配置：

| 配置文件 | 环境 | 说明 |
|---------|------|------|
| `application.yml` | 默认 | 通用配置 |
| `application-dev.yml` | 开发 | 开发环境配置 |
| `application-prod.yml` | 生产 | 生产环境配置 |

使用 `-Pdev` 或 `-Pprod` 参数切换环境。

## 安全说明

- 使用 JWT 进行用户身份认证
- 敏感数据采用 AES 加密存储
- 登录过滤器实现请求拦截和验证

## 开发计划

- [ ] 完善单元测试
- [ ] 添加数据导出功能
- [ ] 支持多币种
- [ ] 移动端适配优化
- [ ] 添加预算提醒功能

## 许可证

本项目仅供个人学习使用。

## 作者

LX

---

> 如有问题或建议，欢迎提交 Issue。
