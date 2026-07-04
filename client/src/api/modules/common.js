// 通用数据源操作接口
export default http => ({
	// 数据源相关
	detailOpt: (dataSource, opt, data) => http.post('/detail_opt/' + dataSource + '/' + opt, data)
});
