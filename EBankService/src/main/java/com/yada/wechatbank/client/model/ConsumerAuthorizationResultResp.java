package com.yada.wechatbank.client.model;

import com.yada.wechatbank.model.ConsumerAuthorizationResult;

/**
 * 消费分期授权行内service返回结果
 * 
 * @author liangtieluan
 *
 */
public class ConsumerAuthorizationResultResp {
	private ConsumerAuthorizationResult bizResult;

	public ConsumerAuthorizationResult getBizResult() {
		return bizResult;
	}

	public void setBizResult(ConsumerAuthorizationResult bizResult) {
		this.bizResult = bizResult;
	}

}
