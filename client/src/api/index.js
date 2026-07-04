import HttpClient from "./http";
import treasure from "./modules/treasure";
import invest from "./modules/invest";
import expend from "./modules/expend";
import log from "./modules/log";
import text from "./modules/text";
import common from "./modules/common";
import { dateFormat } from "utils/date";

// 组装 http 实例：保留 this.http.xxx() 的调用方式（向后兼容）
const http = new HttpClient();
Object.assign(
	http,
	treasure(http),
	invest(http),
	expend(http),
	log(http),
	text(http),
	common(http)
);
// 保留原 this.http.dateFormat 兼容
http.dateFormat = dateFormat;

export default http;
