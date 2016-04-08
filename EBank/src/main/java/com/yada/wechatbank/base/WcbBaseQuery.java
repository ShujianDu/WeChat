package com.yada.wechatbank.base;

public class WcbBaseQuery extends BaseQuery {
	
	private String validateCode;
	private String openId;

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	
}
