# TreasureLog 后端服务

TreasureLog 后端服务基于 Spring Boot 框架构建，提供个人财富管理系统的数据接口和业务逻辑处理。

## 技术栈

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

## 项目结构

```
server/
├── src/
│   └── main/
│       ├── java/com/lx/treasure/
│       │   ├── module/          # 业务模块
│       │   │   ├── login/       # 用户登录模块
│       │   │   ├── treasure/    # 财富管理核心模块
│       │   │   ├── creditcard/  # 信用卡管理模块
│       │   │   └── TextRalation/# 文本记录模块
│       │   ├── config/          # 配置类
│       │   ├── bean/            # 数据对象
│       │   └── common/          # 通用组件
│       └── resources/           # 配置文件
└── pom.xml                      # Maven 依赖管理
```

## 核心功能

### 1. 用户认证模块
- 基于 JWT 的用户登录和身份验证
- 登录过滤器实现请求拦截和验证

### 2. 财富管理模块
- 收支记录的增删改查
- 财富曲线数据生成
- 财富分类统计
- 主要支出分析

### 3. 信用卡管理模块
- 信用卡信息管理
- 账单和还款记录

### 4. 数据统计模块
- 财富趋势分析
- 支出类别统计
- 渠道信息统计

## API 接口

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

## 配置文件

项目支持多环境配置：

| 配置文件 | 环境 | 说明 |
|---------|------|------|
| `application.yml` | 默认 | 通用配置 |
| `application-dev.yml` | 开发 | 开发环境配置 |
| `application-prod.yml` | 生产 | 生产环境配置 |

## 安全措施

- 使用 JWT 进行用户身份认证
- 敏感数据采用 AES 加密存储
- 登录过滤器实现请求拦截和验证

## 快速开始

### 环境要求

- JDK 1.8+
- Maven 3.6+
- MySQL 5.7+

### 启动步骤

1. 配置数据库连接
编辑 `src/main/resources/application-dev.yml` 或 `src/main/resources/application-prod.yml`，配置 MySQL 连接信息。

2. 构建并运行
```bash
# 开发环境
mvn spring-boot:run -Pdev

# 或打包后运行
mvn clean package -Pdev
java -jar target/TreasureLife-0.0.1-SNAPSHOT.jar
```

## 打包发布

```bash
# 开发环境打包
mvn clean package -Pdev

# 生产环境打包
mvn clean package -Pprod
```

## 依赖管理

使用 Maven 进行依赖管理，主要依赖项包括：
- Spring Boot Starter Web
- Spring Boot Data JPA
- MyBatis Spring Boot Starter
- JWT 认证库
- FastJSON
- MySQL 连接器
- Lombok

## 开发规范

- 使用 RESTful API 设计风格
- 统一的错误处理机制
- 统一的数据返回格式
- 日志记录规范

## 错误码

| 服务编号 | 功能 |
|----------|------|
| 4xx | 用户输入错误 |
| 5xx | 服务器内部错误 |

### 服务编号-功能对照表

#### 获取数据

| 服务编号 | 功能 |
|----------|------|
| G002 | 获取投资信息 |
| G003 | 获取主要支出详细信息 |
| G004 | 获取支出信息 |

#### 插入数据

| 服务编号 | 功能 |
|----------|------|
| I001 | 插入一条基本记录 |
| I002 | 插入一条投资记录 |

## 测试

项目包含单元测试，可通过以下命令运行：
```bash
mvn test
```

## 许可证

本项目仅供个人学习使用。

---

> 如有问题或建议，欢迎提交 Issue。