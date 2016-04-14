package com.yada.wechatbank.service;

/**
 * Created by QinQiang on 2016/4/14.
 */
public interface SmsService {
    /**
     * 发送短信验证码
     *
     * @param mobileNo 手机号
     * @param code     验证码
     * @return true-成功，false-失败
     */
    public boolean sendSMS(String mobileNo, String code);

    /**
     * 发送预约办卡短信验证码
     *
     * @param mobileNo 手机号
     * @param code     验证码
     * @return true-成功，false-失败
     */
    public boolean sendCardApplySMS(String mobileNo, String code);
}
