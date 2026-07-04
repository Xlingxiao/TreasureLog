// 文字记录相关接口
export default http => ({
	// 获取文字记录
	getTextInfo: data => http.post("/text/getText", data),
	// 记录一条文字
	addOneText: data => http.post("/text/addOneText", data)
});
