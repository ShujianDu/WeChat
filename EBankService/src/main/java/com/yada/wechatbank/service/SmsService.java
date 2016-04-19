package com.yada.wechatbank.service;

/**
 * 短信发送，验证服务类
 * @author Tx
 */
public interface SmsService {
    /**
     * 发送短信验证码
     * @param identityNo    证件号
     * @param mobileNo      手机号
     * @param channelCode   渠道号
     * @return  是否成功发送
     */
    boolean sendSMS(String identityNo,String mobileNo, String channelCode);

    /**
     * 发送预约办卡短信验证码
     *
     * @param identityNo  证件号
     * @param mobileNo    手机号
     * @param channelCode 渠道号
     * @return true-成功，false-失败
     */
    boolean sendCardApplySMS(String identityNo, String mobileNo, String channelCode);


    /**
     * 发送分期短信验证码
     *
     * @param identityNo  证件号
     * @param mobileNo    手机号
     * @param channelCode 渠道号
     * @return true-成功，false-失败
     */
    boolean sendInstallmentSMS(String identityNo, String mobileNo, String channelCode);

    /**
     * 验证各个渠道的短信验证码。失败次数3次
     * @param identityNo    证件号
     * @param mobile        手机号
     * @param channelCode   渠道号
     * @param smsCode       短信验证码
     * @return true-验证成功，false-验证失败
     */
    boolean checkSMSCode(String identityNo,String mobile,String channelCode,String smsCode);
}
