package com.yada.wechatbank.rmi.interfaces;

public interface IWCBInterface{
	

	String getToken();
	/**
	 * 通过授权code获取用户openId 如获取失败或其他原因，则返回双引空
	 * return openId
	 */
	String getOpenIdByCode(String code);
	/**
	 * 判断当前用户是否关注此公众号 返回0是未关注，1是已关注
	 * 
	 * @return
	 */
	String checkSubscribe(String openId);
}
