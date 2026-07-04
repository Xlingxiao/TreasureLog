// 支出/消费相关接口
export default http => ({
	// 获取大笔支出信息
	getMainExpend: data => http.post("/expend/getInfo", data),
	// 记录一次消费
	addExpendLog: data => http.post("/expend/log", data),
	// 获取每月消费走势
	getExpendCurve: data => http.post("/getExpentCurve", data),
	// 获取支出收入信息
	getSpendInfo: data => http.post("/expend/getSpendInfo", data)
});
