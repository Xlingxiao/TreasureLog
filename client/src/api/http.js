'use strict';

var axios = require('axios');

// HttpClient：核心网络封装（请求/错误处理/登录登出/上下文）
var HttpClient = function() {

	let config = {
		headers: {
			'Content-Type': 'application/json',
		},
		timeout: 35000,
		withCredentials: true
	};

	// 发送post请求
	this.post = function(url, data, checkErr) {
		url = "/treasureServer" + url
		let _this = this;
		if(!config.headers.Authorization) {
			config.headers.Authorization = localStorage.getItem('token');
		}
		return new Promise(function(resolve, reject) {
			axios.post(url, data, config)
				.then(res => {
					resolve(res.data)
				})
				.catch(err => {
					// 如果是检查是否登录交易就直接返回
					if(checkErr) {
						reject(err);
						return;
					}
					let errObj = JSON.parse(err.response.request.response)
					console.log(errObj)
					_this.errorHandle(errObj);
					reject(err)
				})
		})
	};

	// 发送get请求
	this.get = function(url) {
		return new Promise(function(resolve, reject) {
			axios.get(url, config)
				.then(res => {
					resolve(res.data)
				})
				.catch(res => {
					reject(res)
				})
		})
	}

	this.showMessage = function(content, type) {
		this.context.$message({
			message: content,
			type: type
		});
	}

	this.errorHandle = function(errObj) {
		let context = this.context;
		if(!errObj) return;
		this.showMessage(errObj.message, "warning");
		if(errObj.status == '401') {
			// 跳转登录页
			context.$router.push({
				path: '/login'
			});
		}
	}

	this.initContext = function(obj) {
		this.context = obj;
	}

	// 进行登录
	this.login = function(user) {
		let aesInfo = this.context.Crypto.genAesKey();
		let params = {
			"userAccount": user.account,
			"password": user.password,
			"key": aesInfo.key,
			"iv": aesInfo.iv
		}
		this.post("/login", params).then(res => {
			let token = res.token;
			config.headers.Authorization = token
			localStorage.setItem('token', token);
			this.showMessage("登录成功！")
			this.context.$router.push({
				path: '/'
			});
		})
	}

	this.logout = function() {
		// 先调用后端接口使 token 失效
		this.post("/logout", {}).catch(function() {});
		localStorage.removeItem('token');
		config.headers.Authorization = null;
	}

	this.isLogged = function() {
		return this.post("/getChannels", {}, true)
	}
}

export default HttpClient
