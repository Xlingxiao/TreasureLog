// 完整记录相关接口
export default http => ({
	// 输入一条完整记录
	addCompleteLog: data => http.post("/insert/complete", data)
});
