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

    
    
}

export default serverApi