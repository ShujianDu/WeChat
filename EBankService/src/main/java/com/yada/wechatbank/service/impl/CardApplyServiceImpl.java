package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.model.CardApplyResp;
import com.yada.wechatbank.client.model.StringResp;
import com.yada.wechatbank.kafka.MessageProducer;
import com.yada.wechatbank.kafka.TopicEnum;
import com.yada.wechatbank.model.CardApplyList;
import com.yada.wechatbank.service.CardApplyService;
import com.yada.wechatbank.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 预约办卡进度查询
 * Created by QinQiang on 2016/4/11.
 */
@Service
public class CardApplyServiceImpl extends BaseService implements CardApplyService {

    private static final String CHANNEL_CODE = "EBank_CardApply";

    @Autowired
    private SmsService smsService;

    @Autowired
    MessageProducer messageProducer;

    @Value(value = "${url.cardApply}")
    protected String cardApplyUrl; // 预约办卡进度查询
    @Value(value = "${url.getMobilePhone}")
    protected String getMobilePhone; // 获取预约办卡手机号

    @Override
    public CardApplyList getCrdCardSchedule(String name, String identityType, String identityNo, int currentPage) {
        // 调用行内SAS获取JSON数据
        Map<String, String> param = initDirectSaleParam();
        param.put("name", name);
        param.put("idType", identityType);
        param.put("id", identityNo);
        param.put("currentPage", "" + currentPage);
        messageProducer.send(TopicEnum.EBANK_QUERY, "CardApplyGetCrdCardSchedule", param);
        CardApplyResp cardApplyResp = httpClient.send(cardApplyUrl, param, CardApplyResp.class);
        return cardApplyResp == null ? null : cardApplyResp.getData();
    }

    @Override
    public String sendCardApplySMS(String identityType, String identityNo, String mobileNo) {
        // 通过证件类型、证件号获取手机号
        Map<String, String> param = initGcsParam();
        param.put("idType", identityType);
        param.put("idNo", identityNo);
        messageProducer.send(TopicEnum.EBANK_QUERY, "CardApplySendCardApplySMS", param);
        StringResp resp = httpClient.send(getMobilePhone, param, StringResp.class);
        if (resp == null) {
            messageProducer.send(TopicEnum.EBANK_QUERY, "CardApplySendCardApplySMS", "用户["+identityNo
                    +"]通过后台获取手机号为null");
            return "exception";
        }
        // 验证手机号的正确性
        if (resp.getData() == null || resp.getData().isEmpty() || !resp.getData().equals(mobileNo)) {
            messageProducer.send(TopicEnum.EBANK_QUERY, "CardApplySendCardApplySMS", "用户["+identityNo
                    +"]通过后台获取手机号["+mobileNo+"]与后台返回的手机号匹配失败");
            return "wrongMobileNo";
        }
        // 发送短信验证码
        boolean sendResult = smsService.sendCardApplySMS(identityNo, mobileNo, CHANNEL_CODE);
        return Boolean.toString(sendResult).toLowerCase();
    }

    @Override
    public boolean checkCardApplySMSCode(String identityNo, String mobileNo, String code) {
        return smsService.checkSMSCode(identityNo, mobileNo, CHANNEL_CODE, code);
    }
}
