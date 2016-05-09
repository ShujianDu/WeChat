package com.yada.wechatbank.util;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 获得页面js-sdk参数工具类
 * 
 * @author liangtieluan
 * 
 */
public class JsMapUtil {

	public static Map<String, String> getJsMapConfig(
			HttpServletRequest request, String businessUrl, String jsTitle) {
		// 页面分享js需要的参数
		@SuppressWarnings("unchecked")
		Map<String, String> jsMap = (Map<String, String>) request
				.getAttribute("jsMap");
		if (jsMap == null) {
			return null;
		}
		String redirect_uri = jsMap.get("DOMAINURL") + "/" + businessUrl;
		try {
			redirect_uri = URLEncoder.encode(redirect_uri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		jsMap.put("linkUrl",
				jsMap.get("linkUrl").replace("BUSINESSURL", redirect_uri));
		jsMap.put("jsTitle", jsTitle);
		return jsMap;
	}

}
