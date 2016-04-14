package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.HttpClient;
import com.yada.wechatbank.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送短信验证码
 * Created by QinQiang on 2016/4/14.
 */
@Service
public class SmsServiceImpl extends BaseService implements SmsService {

    @Autowired
    protected HttpClient httpClient;

    @Value("${bcsp.sms.sysId}")
    private String bcspSmsSysId;
    @Value("${bcsp.sms.bsnType}")
    private String bcspSmsBsnType;
    @Value("${bcsp.sms.cardApplyBsnType}")
    private String bcspSmsCardApplyBsnType;

    @Value("${sms.content}")
    private String smsContent; // 短信验证发送的内容
    @Value("${sms.bindingContent}")
    private String bindingContent; // 绑定短信验证发送的内容
    @Value("${sms.installmentContent}")
    private String installmentContent; // 分期交易验证码发送
    @Value("${sms.cardApplyContent}")
    private String cardApplyContent; // 预约办卡进度查询验证码发送

    // 发送短信验证码URL
    @Value(value = "${url.sendSMS}")
    private String sendSMS;

    /**
     * 发送短信验证码
     *
     * @param mobileNo 手机号
     * @param code     验证码
     * @return true-成功，false-失败
     */
    @Override
    public boolean sendSMS(String mobileNo, String code) {
        String content = smsContent.replace("#sms.msg#", code);
        Map<String, String> param = new HashMap<>();
        param.put("sysId", bcspSmsSysId);
        param.put("bsnType", bcspSmsBsnType);
        param.put("handsetNo", mobileNo);
        param.put("content", content);
        Boolean result = httpClient.send(sendSMS, param, Boolean.class);
        return result;
    }

    /**
     * 预约办卡发送短信验证码
     *
     * @param mobileNo 手机号
     * @param code     验证码
     * @return true-成功，false-失败
     */
    @Override
    public boolean sendCardApplySMS(String mobileNo, String code) {
        String content = cardApplyContent.replace("#sms.msg#", code);
        Map<String, String> param = new HashMap<>();
        param.put("sysId", bcspSmsSysId);
        param.put("bsnType", bcspSmsCardApplyBsnType);
        param.put("handsetNo", mobileNo);
        param.put("content", content);
        Boolean result = httpClient.send(sendSMS, param, Boolean.class);
        return result;
    }
}
