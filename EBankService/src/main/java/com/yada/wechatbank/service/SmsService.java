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
     * @param bizCode     业务英文编号
     * @return  是否成功发送
     */
    boolean sendSMS(String identityNo,String mobileNo, String bizCode);

    /**
     * 发送预约办卡短信验证码
     *
     * @param identityNo  证件号
     * @param mobileNo    手机号
     * @param bizCode     业务英文编号
     * @return true-成功，false-失败
     */
    boolean sendCardApplySMS(String identityNo, String mobileNo, String bizCode);


    /**
     * 发送分期短信验证码
     *
     * @param identityNo  证件号
     * @param mobileNo    手机号
     * @param bizCode     业务英文编号
     * @return true-成功，false-失败
     */
    boolean sendInstallmentSMS(String identityNo, String mobileNo, String bizCode);
    /**
     * 发送临时提额授权短信验证码
     *
     * @param identityNo  证件号
     * @param mobileNo    手机号
     * @param bizCode     业务英文编号
     * @return true-成功，false-失败
     */
    boolean sendCreditLimitTemporaryUpSMS(String identityNo, String mobileNo, String bizCode);

    /**
     * 验证各个渠道的短信验证码。失败次数3次
     * @param identityNo    证件号
     * @param mobile        手机号
     * @param bizCode       业务英文编号
     * @param smsCode       短信验证码
     * @return true-验证成功，false-验证失败
     */
    boolean checkSMSCode(String identityNo,String mobile,String bizCode,String smsCode);

    /**
     * 发送绑定短信验证码
     * @param identityNo 证件号
     * @param mobileNo 手机号
     * @param channelCode 杜鳌好
     * @return true-成功，false-失败
     */
    boolean sendBinDingSMS(String identityNo, String mobileNo, String channelCode);
}
