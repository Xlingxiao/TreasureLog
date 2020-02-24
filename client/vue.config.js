var path = require('path')
function resolve(dir) {
  return path.join(__dirname, dir)
}

let argvs = JSON.parse(process.env.npm_config_argv).original.splice(2);
console.log("目录参数", argvs)
//如果有参数，则打包到不同目录
var subDir = ""
if(argvs[0] != '--mode'){
    subDir = argvs.length == 0 ? "" : "/" + argvs[0]
}

module.exports = {
	publicPath: './',
	outputDir: 'dist',
	chainWebpack: config => {
        // config.optimization.delete('splitChunks')
		if(process.env.NODE_ENV === 'production') {
			// 移除 prefetch 插件
			config.plugins.delete("prefetch");
			// 移除 preload 插件
			config.plugins.delete('preload');
			// 压缩代码
			config.optimization.minimize(true);
			// 分割代码
			// config.optimization.splitChunks({
			//     chunks: 'all'
			// })    
		}
        config.resolve.alias
            .set('common', resolve('public/static'))
            .set('assets', resolve('src/assets'))
            .set('components', resolve('src/components'))
            .set('utils', resolve('src/utils'))
        config.plugin('copy').tap(args => {
            console.log("args", args[0][0])
            if(process.env.NODE_ENV === 'production') {
                if(subDir == '/index'){
                   args[0][0] = {
                        from: resolve("public"),
                        to: resolve("dist"),
                        toType: 'dir',
                        ignore: [ '.DS_Store', '*.html' ]
                   } 
                }
            }
            return args
        })
        config.entry('polyfill').add('babel-polyfill')
        config.module
            .rule('images')
            .test(/\.(png|jpe?g|gif|webp)(\?.*)?$/)
            .use('url-loader')
            .loader('url-loader')
            .tap(options => {
                // 修改它的选项...
                // options.fallback.options.name = 'img/[name].['+Version+'].[ext]'; 
                // options.limit = 5;
                console.log("options", options)
                return options
            });
    },
	devServer: {
        port: 8929, // 端口
        disableHostCheck: true,
        proxy: {
            '/*': {
                ws: false, 
                //要访问的跨域的域名
                target: "http://localhost:8928",
                //开启代理：在本地会创建一个虚拟服务端，然后发送请求的数据，并同时接收请求的数据，这样客户端端和服务端进行数据的交互就不会有跨域问题
                changOrigin: true,
            },
        }
	},
	
	
}
