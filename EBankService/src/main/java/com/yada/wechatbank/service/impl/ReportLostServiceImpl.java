package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.model.BooleanResp;
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

    private static final String CHANNEL_CODE = "EBank_ReportLost";

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
        BooleanResp resp = httpClient.send(tempCreditCardReportLost, param, BooleanResp.class);
        return resp == null ? false : resp.getBizResult();
    }

    @Override
    public boolean creditCardReportLost(String cardNo, String identityType, String identityNo, String lostReason) {
        String familyName = getFamilyName(cardNo);
        Map<String, String> param = initGcsParam();
        param.put("cardNo", cardNo);
        param.put("idType", identityType);
        param.put("idNum", identityNo);
        param.put("familyName", familyName);
        param.put("lostReason", lostReason);
        BooleanResp resp = httpClient.send(creditCardReportLost, param, BooleanResp.class);
        return resp == null ? false : resp.getBizResult();
    }

    @Override
    public boolean relieveCreditCardTempReportLost(String cardNo, String identityType, String identityNo) {
        String familyName = getFamilyName(cardNo);
        Map<String, String> param = initGcsParam();
        param.put("cardNo", cardNo);
        param.put("idType", identityType);
        param.put("idNum", identityNo);
        param.put("familyName", familyName);
        BooleanResp resp = httpClient.send(relieveCreditCardTempReportLost, param, BooleanResp.class);
        return resp == null ? false : resp.getBizResult();
    }

    /**
     * 调取后台接口获取姓
     *
     * @return String
     */
    private String getFamilyName(String cardNo) {
        String familyName = "";
        Map<String, String> param = initGcsParam();
        param.put("cardNo", cardNo);
        // TODO QQ 调用HttpClient完成持卡人信息查询
        return familyName;
    }

    @Override
    public boolean sendSMS(String identityNo, String mobileNo) {
        return smsService.sendSMS(identityNo, mobileNo, CHANNEL_CODE);
    }

    @Override
    public boolean checkSMSCode(String identityNo, String mobileNo, String code) {
        return smsService.checkSMSCode(identityNo, mobileNo, CHANNEL_CODE, code);
    }
}
