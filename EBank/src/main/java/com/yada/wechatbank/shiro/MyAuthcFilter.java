package com.yada.wechatbank.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

public class MyAuthcFilter extends FormAuthenticationFilter {

	public static final String DEFAULT_VERIFICATION_PARAM = "verification";
	private String verificationParam = DEFAULT_VERIFICATION_PARAM;     //验证码
	private String openId = "openId";     //openId
	
	/**
	 * 重写父类创建token方法
	 */
	protected AuthenticationToken createToken(ServletRequest request,
			ServletResponse response) {
		String username = getUsername(request);
		char[] password = getPassword(request).toCharArray();
		String verification = getVerificationParam(request);
		String openId = getOpenIdParam(request);
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		return new MyToken(username, password, verification, rememberMe, host,openId);
	}

	protected String getVerificationParam(ServletRequest request) {
		return WebUtils.getCleanParam(request, getVerificationParam());
	}
	protected String getOpenIdParam(ServletRequest request) {
		return WebUtils.getCleanParam(request, getOpenId());
	}
	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getVerificationParam() {
		return verificationParam;
	}

	public void setVerificationParam(String verificationParam) {
		this.verificationParam = verificationParam;
	}
}
