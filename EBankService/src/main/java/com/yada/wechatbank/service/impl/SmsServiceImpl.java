package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.cache.ISMSCache;
import com.yada.wechatbank.client.HttpClient;
import com.yada.wechatbank.client.model.BooleanResp;
import com.yada.wechatbank.kafka.MessageProducer;
import com.yada.wechatbank.kafka.TopicEnum;
import com.yada.wechatbank.model.SMSCodeManagement;
import com.yada.wechatbank.service.SmsService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 短信管理类
 * Created by QinQiang on 2016/4/14.
 */
@Service
public class SmsServiceImpl extends BaseService implements SmsService {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected HttpClient httpClient;

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private ISMSCache sMSCacheImpl;

    @Value("${bcsp.sms.sysId}")
    private String bcspSmsSysId;
    @Value("${bcsp.sms.bsnType}")
    private String bcspSmsBsnType;
    //预约办卡专用type
    @Value("${bcsp.sms.cardApplyBsnType}")
    private String bcspSmsCardApplyBsnType;

    // 发送短信验证码URL
    @Value("${url.sendSMS}")
    private String sendSMS;


    @Value("${sms.content}")
    private String smsContent; // 短信验证发送的内容
    @Value("${sms.bindingContent}")
    private String bindingContent; // 绑定短信验证发送的内容
    @Value("${sms.installmentContent}")
    private String installmentContent; // 分期交易验证码发送
    @Value("${sms.cardApplyContent}")
    private String cardApplyContent; // 预约办卡进度查询验证码发送
    @Value("${sms.creditLimitTemporaryUpContent}")
    private String creditLimitTemporaryUpContent; // 临增额度验证码发送

    /**
     * 发送短信验证码
     *
     * @param identityNo 证件号
     * @param mobileNo   手机号
     * @param bizCode    业务英文编号
     * @return true-成功，false-失败
     */
    @Override
    public boolean sendSMS(String identityNo, String mobileNo, String bizCode) {
        return assemblySMS(identityNo, mobileNo, bizCode, smsContent, bcspSmsBsnType);
    }

    /**
     * 发送预约办卡短信验证码
     *
     * @param identityNo 证件号
     * @param mobileNo   手机号
     * @param bizCode    业务英文编号
     * @return true-成功，false-失败
     */
    @Override
    public boolean sendCardApplySMS(String identityNo, String mobileNo, String bizCode) {
        return assemblySMS(identityNo, mobileNo, bizCode, cardApplyContent, bcspSmsCardApplyBsnType);
    }

    /**
     * 发送分期短信验证码
     *
     * @param identityNo 证件号
     * @param mobileNo   手机号
     * @param bizCode    渠道号
     * @return true-成功，false-失败
     */
    @Override
    public boolean sendInstallmentSMS(String identityNo, String mobileNo, String bizCode) {
        return assemblySMS(identityNo, mobileNo, bizCode, installmentContent, bcspSmsBsnType);
    }

    /**
     * 发送提额短信验证码
     *
     * @param identityNo 证件号
     * @param mobileNo   手机号
     * @param bizCode    渠道号
     * @return true-成功，false-失败
     */
    @Override
    public boolean sendCreditLimitTemporaryUpSMS(String identityNo, String mobileNo, String bizCode) {
        return assemblySMS(identityNo, mobileNo, bizCode, creditLimitTemporaryUpContent, bcspSmsBsnType);
    }

    /**
     * 发送绑定短信验证码
     *
     * @param identityNo 证件号
     * @param mobileNo   手机号
     * @param bizCode    业务英文编号
     * @return true-成功，false-失败
     */
    @Override
    public boolean sendBinDingSMS(String identityNo, String mobileNo, String bizCode) {
        return assemblySMS(identityNo, mobileNo, bizCode, bindingContent, bcspSmsCardApplyBsnType);
    }

    /**
     * @param identityNo 证件号
     * @param mobileNo   手机号
     * @param bizCode    业务英文编号
     * @param smsContent 短信模板
     * @param bxnType    SMS模板类型
     * @return 是否发送和保存成功
     */
    private boolean assemblySMS(String identityNo, String mobileNo, String bizCode, String smsContent, String bxnType) {
        String code = generateSMSCode();
        String content = smsContent.replace("#sms.msg#", code);
        Map<String, String> param = new HashMap<>();
        param.put("sysId", bcspSmsSysId);
        param.put("bsnType", bxnType);
        param.put("handsetNo", mobileNo);
        param.put("content", content);
        BooleanResp result = httpClient.send(sendSMS, param, BooleanResp.class);
        //发送成功保存缓存
        if (result != null && result.getData()) {
            saveSMSCodeToCache(identityNo, mobileNo, bizCode, code);
        }
        //kafka事件推送
        messageProducer.send(TopicEnum.EBANK_QUERY,bizCode+"_SMS",param);
        return result == null ? false : result.getData();
    }


    @Override
    public boolean checkSMSCode(String identityNo, String mobile, String bizCode, String smsCode) {
        boolean r = false;
        String key = identityNo + "_" + mobile + "_" + bizCode;
        SMSCodeManagement c = sMSCacheImpl.get(key);
        if (c != null) {
            if (c.getSmsCode().equals(smsCode)) {
                r = true;
                logger.debug("用户identityNo[{}],手机号[{}]通过[{}]渠道使用验证码：[{}]", identityNo, mobile, bizCode, smsCode);
                sMSCacheImpl.remove(key);
            } else {
                c.setCount(c.getCount() + 1);
            }
            if (c.getCount() >= 3) {
                logger.warn("用户identityNo[{}]通过[{}]渠道验证短信验证码超次", identityNo, bizCode);
                sMSCacheImpl.remove(key);
            }
        }
        return r;
    }

    /**
     * 获取随机码
     *
     * @return 随机6位码
     */

    public String generateSMSCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999));
    }

    /**
     * 获取短信验证码 获取同时数据会保存在ehcache中，key的组合为：证件号 + _ + 手机号  + _ + channelCode
     * （证件号+下划线+手机号+下划线+渠道编号）value为实体
     *
     * @param identityNo 证件号
     * @param mobile     手机号
     * @param bizCode    业务英文编号
     * @param code       验证码
     */
    private void saveSMSCodeToCache(String identityNo, String mobile, String bizCode, String code) {
        SMSCodeManagement smsCode = new SMSCodeManagement();
        smsCode.setSmsCode(code);
        smsCode.setMobile(mobile);
        smsCode.setBizCode(bizCode);
        smsCode.setIdentityNo(identityNo);
        //加入缓存
        sMSCacheImpl.put(identityNo + "_" + mobile + "_" + bizCode, smsCode);
        logger.info("为用户identityNo[{}]、手机号[{}]在[{}]渠道生成的验证码[{}]", identityNo, mobile, bizCode, smsCode.getSmsCode());
    }
}
