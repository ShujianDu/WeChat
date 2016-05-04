package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.model.BooleanResp;
import com.yada.wechatbank.client.model.CardHolderInfoResp;
import com.yada.wechatbank.kafka.MessageProducer;
import com.yada.wechatbank.kafka.TopicEnum;
import com.yada.wechatbank.service.ReportLostService;
import com.yada.wechatbank.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 信用卡挂失
 * Created by QinQiang on 2016/4/18.
 */
@Service
public class ReportLostServiceImpl extends BaseService implements ReportLostService {

    private static final String BIZ_CODE = "EBank_ReportLost";

    @Autowired
    MessageProducer messageProducer;

    @Autowired
    private SmsService smsService;

    // 临时挂失
    @Value(value = "${url.tempCreditCardReportLost}")
    protected String tempCreditCardReportLost;
    // 永久挂失
    @Value(value = "${url.creditCardReportLost}")
    protected String creditCardReportLost;
    // 取消临时挂失
    @Value(value = "${url.relieveCreditCardTempReportLost}")
    protected String relieveCreditCardTempReportLost;
    // 通过证件类型、证件号获取手机号
    @Value(value = "${url.getCustMobile}")
    protected String getCustMobile;
    // 获取持卡人信息
    @Value("${url.getCardHolderInfo}")
    private String getCardHolderInfo;

    @Override
    public List<String> selectCardNoList(String identityType, String identityNo) {
        return super.selectCardNoList(identityType, identityNo);
    }

    @Override
    public boolean tempCreditCardReportLost(String cardNo, String entyMethod, String identityType, String identityNo, String lostReason) {
        String familyName = getFamilyName(cardNo);
        Map<String, String> param = initGcsParam();
        param.put("cardNo", cardNo);
        param.put("entyMethod", entyMethod);
        param.put("idType", identityType);
        param.put("idNum", identityNo);
        param.put("familyName", familyName);
        param.put("lostReason", lostReason);
        messageProducer.send(TopicEnum.EBANK_QUERY, "ReportLostTempCreditCardReportLost", param);
        BooleanResp resp = httpClient.send(tempCreditCardReportLost, param, BooleanResp.class);
        return resp == null ? false : resp.getData();
    }

    @Override
    public boolean creditCardReportLost(String cardNo, String identityType, String identityNo, String lostReason) {
        String familyName = getFamilyName(cardNo);
        if (familyName == null || familyName.isEmpty()) {
            return false; // 获取姓失败
        }
        Map<String, String> param = initGcsParam();
        param.put("cardNo", cardNo);
        param.put("idType", identityType);
        param.put("idNum", identityNo);
        param.put("familyName", familyName);
        param.put("lostReason", lostReason);
        messageProducer.send(TopicEnum.EBANK_QUERY, "ReportLostCreditCardReportLost", param);
        BooleanResp resp = httpClient.send(creditCardReportLost, param, BooleanResp.class);
        return resp == null ? false : resp.getData();
    }

    @Override
    public boolean relieveCreditCardTempReportLost(String cardNo, String identityType, String identityNo) {
        String familyName = getFamilyName(cardNo);
        Map<String, String> param = initGcsParam();
        param.put("cardNo", cardNo);
        param.put("idType", identityType);
        param.put("idNum", identityNo);
        param.put("familyName", familyName);
        messageProducer.send(TopicEnum.EBANK_QUERY, "ReportLostRelieveCreditCardTempReportLost", param);
        BooleanResp resp = httpClient.send(relieveCreditCardTempReportLost, param, BooleanResp.class);
        return resp == null ? false : resp.getData();
    }

    /**
     * 调取后台接口获取姓
     *
     * @return String
     */
    private String getFamilyName(String cardNo) {
        String name = null;
        Map<String, String> param = initGcsParam();
        param.put("cardNo", cardNo);
        // 调用HttpClient完成持卡人信息查询
        messageProducer.send(TopicEnum.EBANK_QUERY, "ReportLostGetFamilyName", param);
        CardHolderInfoResp resp = httpClient.send(getCardHolderInfo, param, CardHolderInfoResp.class);
        if (resp != null && resp.getData() != null) {
            name = resp.getData().getFamilyName() + resp.getData().getFirstName();
        }
        return name;
    }

    @Override
    public String sendSMS(String identityType, String identityNo, String mobileNo) {
        // 通过证件类型证件号获取手机号
        String respMobileNo = getCustMobileNo(identityType, identityNo);
        messageProducer.send(TopicEnum.EBANK_QUERY, "ReportLostSendSMS", "用户通过证件类型["+identityType+"]和证件号["+identityNo+"]获取手机号");
        // 验证手机号的正确性
        if (respMobileNo == null || !respMobileNo.equals(mobileNo)) {
            messageProducer.send(TopicEnum.EBANK_QUERY, "ReportLostSendSMS", "用户["+identityNo
                    +"]通过后台获取手机号["+mobileNo+"]与后台返回的手机号匹配失败");
            return "wrongMobilNo";
        }
        // 发送短信验证码
        boolean sendResult = smsService.sendSMS(identityNo, mobileNo, BIZ_CODE);
        return Boolean.toString(sendResult).toLowerCase();
    }

    @Override
    public boolean checkSMSCode(String identityNo, String mobileNo, String code) {
        return smsService.checkSMSCode(identityNo, mobileNo, BIZ_CODE, code);
    }
}
