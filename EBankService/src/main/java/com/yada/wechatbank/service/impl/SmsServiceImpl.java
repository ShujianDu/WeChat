package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.cacheImpl.SMSCache;
import com.yada.wechatbank.client.HttpClient;
import com.yada.wechatbank.client.model.BooleanResp;
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
 * ·
 * Created by QinQiang on 2016/4/14.
 */
@Service
public class SmsServiceImpl extends BaseService implements SmsService {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected HttpClient httpClient;

    private Random random;

    @Autowired
    private SMSCache smsCache;

    @Value("${bcsp.sms.sysId}")
    private String bcspSmsSysId;
    @Value("${bcsp.sms.bsnType}")
    private String bcspSmsBsnType;
    @Value("${bcsp.sms.cardApplyBsnType}")
    private String bcspSmsCardApplyBsnType;

    // 发送短信验证码URL
    @Value(value = "${url.sendSMS}")
    private String sendSMS;

    /**
     * 发送短信验证码
     *
     * @param identityNo  证件号
     * @param mobileNo    手机号
     * @param channelCode 渠道号
     * @param smsContent  短信模板
     * @return true-成功，false-失败
     */
    @Override
    public boolean sendSMS(String identityNo, String mobileNo, String channelCode,String smsContent) {

        String code = generateSMSCode();
        if (code == null || "".equals(code)) {
            logger.warn("为用户identityNo[{}]，mobile[{}]在渠道[{}]生成验证码失败", identityNo, mobileNo, channelCode);
            return false;
        }
        String content = smsContent.replace("#sms.msg#", code);
        Map<String, String> param = new HashMap<>();
        param.put("sysId", bcspSmsSysId);
        param.put("bsnType", bcspSmsBsnType);
        param.put("handsetNo", mobileNo);
        param.put("content", content);
        BooleanResp result = httpClient.send(sendSMS, param, BooleanResp.class);
        //发送成功保存缓存
        if (!result.getBizResult()) {
            saveSMSCodeToCache(identityNo, mobileNo, channelCode, code);
        }
        return result.getBizResult();
    }

    @Override
    public boolean checkSMSCode(String identityNo, String mobile, String channelCode, String smsCode) {
        boolean r = false;
        String key = identityNo + "_" + mobile + "_" + channelCode;
        SMSCodeManagement c = smsCache.get(key);
        if (c.getSmsCode().equals(smsCode)) {
            r = true;
            logger.debug("用户identityNo[{}],手机号[{}]通过[{}]渠道使用验证码：[{}]", identityNo, mobile, channelCode, smsCode);
            smsCache.remove(key);
        } else {
            c.setCount(c.getCount() + 1);
            smsCache.put(key, c);
        }
        if (c.getCount() >= 3) {
            logger.warn("用户identityNo[{}]通过[{}]渠道验证短信验证码超次", identityNo, channelCode);
            smsCache.remove(key);
        }
        return r;
    }

    /**
     * 获取随机码
     *
     * @return 随机6位码
     */

    private String generateSMSCode() {
        return String.format("%06d", random.nextInt(999999));
    }

    /**
     * 获取短信验证码 获取同时数据会保存在ehcache中，key的组合为：证件号 + _ + 手机号  + _ + channelCode
     * （证件号+下划线+手机号+下划线+渠道编号）value为实体
     *
     * @param identityNo  证件号
     * @param mobile      手机号
     * @param channelCode 渠道号
     * @param code        验证码
     * @return 返回生成的验证码
     */
    private void saveSMSCodeToCache(String identityNo, String mobile, String channelCode, String code) {
        SMSCodeManagement smsCode = new SMSCodeManagement();
        smsCode.setSmsCode(code);
        smsCode.setMobile(mobile);
        smsCode.setChannelCode(channelCode);
        //加入缓存
        smsCache.put(identityNo + "_" + mobile + "_" + channelCode, smsCode);
        logger.debug("为用户identityNo[{}]、手机号[{}]在[{}]渠道生成的验证码[{}]", identityNo, mobile, channelCode, smsCode.getSmsCode());
    }
}
