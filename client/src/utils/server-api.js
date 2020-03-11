'use strict';

var axios = require('axios');

var serverApi = function() {


	let confing = {
		headers: {
			'Content-Type': 'application/json',
			'contentType': 'application/json',
		},
		timeout: 35000, //120 s
		withCredentials: true
	};

    // 发送post请求
	this.post = function(url, data) {
		url = "/treasureServer" + url
		return new Promise(function(resolve, reject) {
			axios.post(url, data, confing)
				.then(res => {
					resolve(res.data)
				})
				.catch(res => {
					reject(res)
				})
		})
	};

    // 发送get请求
    this.get = function(url) {
        return new Promise(function(resolve, reject) {
			axios.get(url, confing)
				.then(res => {
					resolve(res.data)
				})
				.catch(res => {
					reject(res)
				})
		})
    }

	
	// 根据userAccount获取渠道数据
	this.getLatestChannleData = function(data){
		return this.post("/getChannels",data);
	}

	// 获取投资信息
	this.getInvestInfo = function(data) {
		return this.post("/invest/index",data);
	}

	// 获取大笔支出信息
	this.getMainExpend = function(data) {
		return this.post("/getMainExpend",data);
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
    
}

export default serverApi