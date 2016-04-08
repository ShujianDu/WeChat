package com.yada.wechatbank.base;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

public abstract class WcbBaseService<Entity, Id> extends BaseService<Entity, Id> {
	
	/**
	 * @author QQ
	 * 生成身份验证码
	 * */
	public void createCode(WcbBaseQuery wcbBaseQuery,HttpServletRequest request){
		// 生成新的身份认证码
		Random random = new Random();
		String validateCode = "" + random.nextInt(99999);
		request.getSession().setAttribute("validateCode", validateCode);// 放入Session
		wcbBaseQuery.setValidateCode(validateCode);// 放入Query实体
	}
	
	/**
	 * @author QQ
	 * 验证是否有权限,并生成新的身份验证码
	 * */
	public boolean validateCodePermit(WcbBaseQuery wcbBaseQuery,HttpServletRequest request) {
		// 从Session中获取身份验证码
		String validateCode = (String) request.getSession().getAttribute("validateCode");
		String reqCode = wcbBaseQuery.getValidateCode();
		if(validateCode==null||validateCode.equals("")||reqCode==null||reqCode.equals("")){
			return false;
		}
		// 如果不为空，则验证身份验证码是否合法
		if (!validateCode.equals(wcbBaseQuery.getValidateCode())) {
			return false;
		}
		// 生成新的身份认证码
		Random random = new Random();
		validateCode = "" + random.nextInt(99999);
		request.getSession().setAttribute("validateCode", validateCode);// 放入Session
		wcbBaseQuery.setValidateCode(validateCode);// 放入Query实体
		return true;
	}
}
