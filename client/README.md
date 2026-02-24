# TreasureLog 前端应用

TreasureLog 前端应用基于 Vue.js 框架构建，提供个人财富管理系统的用户界面和交互体验。

## 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Vue | 2.6.11 | 前端框架 |
| Vue Router | 3.3.4 | 路由管理 |
| Vuex | 3.5.1 | 状态管理 |
| Element UI | 2.13.2 | UI 组件库 |
| Axios | 0.19.2 | HTTP 客户端 |
| ECharts | 4.8.0 | 数据可视化 |
| Crypto-JS | 4.1.1 | 加密处理 |
| UUID | 9.0.0 | 唯一标识符生成 |

## 项目结构

```
client/
├── public/                    # 静态资源
│   ├── index.html             # 主页面
│   └── ...
├── src/                       # 源代码
│   ├── assets/                # 静态资源
│   ├── components/            # 公共组件
│   ├── views/                 # 页面组件
│   ├── router/                # 路由配置
│   ├── store/                 # Vuex 状态管理
│   ├── utils/                 # 工具函数
│   ├── plugins/               # 插件配置
│   ├── network/               # 网络请求封装
│   └── App.vue                # 根组件
├── package.json              # 项目配置和依赖
└── vue.config.js             # Vue CLI 配置
```

## 核心功能

### 1. 用户界面
- 登录/注册页面
- 财富概览仪表板
- 数据录入表单
- 图表可视化展示

### 2. 数据可视化
- 财富曲线图表 (ECharts)
- 支出分类饼图
- 趋势分析柱状图

### 3. 数据管理
- 收支记录表格
- 信用卡信息管理
- 投资记录跟踪

### 4. 交互功能
- 表单验证
- 数据筛选和排序
- 实时搜索功能
- 响应式布局

## 依赖说明

### 生产依赖 (dependencies)

| 包名 | 版本 | 用途 |
|------|------|------|
| axios | ^0.19.2 | HTTP 请求客户端 |
| element-ui | ^2.13.2 | UI 组件库 |
| vue | ^2.6.11 | 核心框架 |
| vue-router | ^3.3.4 | 路由管理 |
| vuex | ^3.5.1 | 状态管理 |
| echarts | ^4.8.0 | 图表可视化 |
| crypto-js | ^4.1.1 | 数据加密 |
| uuid | ^9.0.0 | 生成唯一标识符 |
| jsencrypt | ^3.3.1 | RSA 加密 |
| js-crypto-rsa | ^1.0.4 | RSA 加密算法 |

### 开发依赖 (devDependencies)

| 包名 | 版本 | 用途 |
|------|------|------|
| @vue/cli-plugin-babel | ^4.2.3 | Babel 编译插件 |
| @vue/cli-service | ^4.2.3 | 服务插件 |
| compression-webpack-plugin | ^3.0.0 | Webpack 压缩插件 |
| vue-template-compiler | ^2.6.11 | 模板编译器 |

## 快速开始

### 环境要求

- Node.js 12+
- npm 6+

### 安装依赖

```bash
# 进入前端目录
cd TreasureLog/client

# 安装依赖
npm install
```

### 开发模式

```bash
# 启动开发服务器
npm run serve

# 访问地址: http://localhost:8080
```

### 生产构建

```bash
# 构建生产版本
npm run build

# 构建后的文件位于 dist/ 目录
```

### 代码检查

```bash
# 检查并修复代码
npm run lint
```

## 配置文件

### vue.config.js

Vue CLI 配置文件，包含：
- 代理配置（用于解决跨域问题）
- webpack 自定义配置
- 构建输出配置

### package.json

项目基本信息和脚本配置：
- 项目名称和版本
- 依赖管理
- 构建脚本

## 路由配置

应用的主要路由包括：
- `/` - 首页/登录页
- `/dashboard` - 仪表板
- `/income` - 收支管理
- `/investment` - 投资管理
- `/credit-card` - 信用卡管理
- `/charts` - 图表分析

## API 集成

前端通过 Axios 与后端 API 进行通信，网络请求封装在 `network/` 目录中，统一管理请求和响应处理。

## 组件设计

- 使用 Element UI 组件库保证 UI 一致性
- 组件按功能模块进行组织
- 采用单文件组件 (SFC) 结构

## 状态管理

使用 Vuex 进行全局状态管理，主要包括：
- 用户认证信息
- 财富数据缓存
- UI 状态控制

## 代码规范

- 使用 ESLint 进行代码检查
- 遵循 Vue 官方风格指南
- 组件命名采用 PascalCase
- 代码缩进使用 2 个空格

## 部署

### 静态资源部署

1. 执行 `npm run build` 生成静态资源
2. 将 `dist/` 目录下的文件部署到 Web 服务器

### 环境变量

根据不同环境配置相应的 API 地址和其他参数。

## 性能优化

- 代码分割和懒加载
- 图片压缩和优化
- 第三方库按需引入
- 路由级别缓存

## 特殊配置说明

根据项目需求，我们进行了以下特殊配置：

### Element UI 按需引入
Element UI 中的组件都是按需配置的，需要什么组件就在 `main.js` 里进行配置。这种方式可以减少打包后的文件大小。

### Axios 网络请求管理
使用 Axios 进行数据请求，默认会有跨域问题，已在 `vue.config.js` 中配置代理服务器。建议将数据的请求都统一管理，便于维护。

### 代理服务器
使用服务器代理可以避免跨域问题，确保前后端正常通信。

## 许可证

本项目仅供个人学习使用。

---

> 如有问题或建议，欢迎提交 Issue。