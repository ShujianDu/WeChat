package com.yada.wechatbank.cache;

import com.yada.wechatbank.model.SMSCodeManagement;

/**
 * 短信缓存接口
 * @author Tx
 *
 */
public interface ISMSCache {

	/**
	 * 缓存验证码
	 * @param smsKey
	 * @param smsCodeManagement
	 * @return
	 */
	SMSCodeManagement put(String smsKey, SMSCodeManagement smsCodeManagement);
	
	SMSCodeManagement get(String smsKey);
	
	void remove(String smsKey);
	
}
