package com.yada.wechatbank.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 
 * 防止页面重复提交
 *
 */
public class TokenUtil {

	private TokenUtil() {
	}

	/**
	 * 在提交页面放唯一token
	 * 
	 * @param request
	 */
	public static void addToken(HttpServletRequest request) {
		String key = UUID.randomUUID().toString();
		request.setAttribute("keycode", key);// 保存页面放在隐藏域中
	
		
		request.getSession().setAttribute("keyInSession", key);
	}

	/**
	 * 保存页面比较拿到的值是否相等
	 * 
	 * @param request
	 * @return 值是否相等
	 */
	public static boolean validateCode(HttpServletRequest request) {
		String key = request.getParameter("keycode");// 从保存页面拿隐藏域
		String keyInSession = (String) request.getSession().getAttribute(
				"keyInSession");
		return StringUtils.isNotEmpty(keyInSession)
				&& StringUtils.isNotEmpty(key)
				&& key.equals(keyInSession);
	}

	/**
	 * 保存完毕后，清除session中的值
	 * 
	 * @param request
	 */
	public static void removeCode(HttpServletRequest request) {
		
		request.getSession().removeAttribute("keyInSession");
	}
	
	public static void main(String args[]){
		String keyInSession=null;
		String key="test";
		boolean b= StringUtils.isNotEmpty(keyInSession)
				&& StringUtils.isNotEmpty(key)
				&& key.trim().equals(keyInSession.trim());
		System.out.println(b);
	}

}
