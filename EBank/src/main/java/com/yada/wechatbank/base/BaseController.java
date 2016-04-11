package com.yada.wechatbank.base;

import org.springframework.stereotype.Controller;

/**
 * controller公共元素
 * 
 * @author liangtieluan
 *
 */
@Controller
public class BaseController {
	// 权限过期跳转URL
	public static final String TIMEOUTURL = "wechatbank_pages/timeOut";
	// 数据查询异常跳转URL
	public static final String BUSYURL = "wechatbank_pages/busy";
	// 无信用卡信息跳转URL
	public static final String NOCARDURL = "wechatbank_pages/nocard";
	// 错误页面
	public static final String ERROR = "wechatbank_pages/error";
}
