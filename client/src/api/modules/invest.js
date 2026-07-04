// 投资相关接口
export default http => ({
	// 获取投资信息
	getInvestInfo: data => http.post("/invest/getInfo", data),
	// 输入一条投资记录
	addInvestLog: data => http.post("/invest/onceLog", data)
});
