// 财富/资产相关接口
export default http => ({
	// 根据userAccount获取渠道数据
	getLatestChannleData: data => http.post("/getChannels", data),
	// 获取个人指定时间的所有财富渠道状态
	getTreasureStatus: data => http.post("/getTreasureStatus", data),
	// 获取汇总的资产状态信息
	getTreasureClassInfo: data => http.post("/getTreasureClassInfo", data),
	// 获取历史收支明细
	getIncomeInfoList: data => http.post("/getIncomeInfoList", data),
	// 获取总资产走势
	getWealthCurve: data => http.post("/getWealthCurve", data)
});
