# VueCliFramework
集成Vue-cli4、Axios、ElementUI框架

这里只是把开发环境搭好了，主要工作是使用ElementUI进行展示，使用axios进行数据请求。
其中axios的数据请求统一放在了`src/utils/server-api.js`文件中。
建议将数据的请求都由server-api来做便于统一管理。

### Element UI

Element UI 中的组件都是自己配置的，需要什么组件就在`main.js`里进行配置即可
这种方式可以减少打包后的文件大小。

### Axios

使用Axios默认会有跨域问题，在Vue.config.md中进行配置了一个代理服务器。
使用服务器访问可以避免跨域问题。