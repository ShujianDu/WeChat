package com.yada.wechatbank.cache;

import com.yada.wechatbank.model.SMSCodeManagement;

/**
 * 短信缓存接口
 *
 * @author Tx
 */
public interface ISMSCache {

    /**
     * 缓存验证码
     *
     * @param smsKey            smsKey
     * @param smsCodeManagement SMSCodeManagement
     * @return SMSCodeManagement
     */
    SMSCodeManagement put(String smsKey, SMSCodeManagement smsCodeManagement);

    /**
     * 获取缓存
     *
     * @param smsKey smsKey
     * @return SMSCodeManagement
     */
    SMSCodeManagement get(String smsKey);

    /**
     * 移除缓存
     *
     * @param smsKey smsKey
     */
    void remove(String smsKey);

}
