'use strict';

var axios = require('axios');

var serverApi = function() {

	let context;

	let config = {
		headers: {
			'Content-Type': 'application/json',
		},
		timeout: 35000, 
		withCredentials: true
	};

    // 发送post请求
	this.post = function(url, data,checkErr) {
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

	this.showMessage= function(content,type) {
		this.context.$message({
			message: content,
			type: type
		});
	}

	this.errorHandle = function(errObj) {
		let context = this.context;
		if(!errObj) return;
		this.showMessage(errObj.message,"warning");
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
			"userAccount":user.account,
			"password":user.password,
			"key": aesInfo.key,
			"iv": aesInfo.iv
		}
		this.post("/login",params).then(res=>{
			let token = res.token;
			config.headers.Authorization = token
			localStorage.setItem('token', token);
			this.showMessage("登录成功！")
			this.context.$router.push({
				path: '/'
			});
		})
	}

	this.isLogged = function() {
		return this.post("/getChannels",{},true)
	}

	// 根据userAccount获取渠道数据
	this.getLatestChannleData = function(data){
		return this.post("/getChannels",data);
	}

	// 获取投资信息
	this.getInvestInfo = function(data) {
		return this.post("/invest/getInfo",data);
	}

	// 获取大笔支出信息
	this.getMainExpend = function(data) {
		return this.post("/expend/getInfo",data);
	}
	// 输入一条完整记录
	this.addCompleteLog = function(data) {
		return this.post("/insert/complete",data);
	}

	// 输入一条投资记录
	this.addInvestLog = function(data) {
		return this.post("/invest/onceLog",data)
	}

	// 记录一次消费
	this.addExpendLog = function(data) {
		return this.post("/expend/log",data)
	}
	// 获取总资产走势
	this.getWealthCurve = function(data) {
		return this.post("/getWealthCurve",data)
	}

	// 获取每月消费走势
    this.getExpendCurve = function(data) {
		return this.post("/getExpentCurve",data)
	}

	//获取支出收入信息
	this.getSpendInfo = function(data) {
		return this.post("/expend/getSpendInfo",data);
	}

	//获取文字记录
	this.getTextInfo = function(data) {
		return this.post("/text/getText",data);
	}

	//记录一条文字
	this.addOneText = function(data) {
		return this.post("/text/addOneText",data);
	}

	this.getKey = function(){
		return ;
	}
}

export default serverApi