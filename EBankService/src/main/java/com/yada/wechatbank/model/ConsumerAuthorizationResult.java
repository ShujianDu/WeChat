package com.yada.wechatbank.model;

/**
 * 消费分期授权结果
 * 
 * @author liangtieluan
 *
 */
public class ConsumerAuthorizationResult {
	// 分期结果 1为成功，其余失败
	private String returnCode;

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

}
