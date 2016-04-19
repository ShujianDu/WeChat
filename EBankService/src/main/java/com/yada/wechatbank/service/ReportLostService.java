package com.yada.wechatbank.service;

import com.yada.wechatbank.model.CardInfo;

import java.util.List;

/**
 * 信用卡挂失
 * Created by QinQiang on 2016/4/18.
 */
public interface ReportLostService {

    /**
     * 获取卡号列表
     *
     * @param identityType 证件类型
     * @param identityNo   证件号
     * @return List<String>
     */
    List<String> selectCardNoList(String identityType, String identityNo);

    /**
     * 临时挂失
     *
     * @param cardNo       卡号
     * @param entyMethod   卡号录入方式 01-手工  07-接触 98-非接
     * @param identityType 证件类型
     * @param identityNo   证件号
     * @param lostReason   挂失原因
     * @return boolean
     */
    boolean tempCreditCardReportLost(String cardNo, String entyMethod, String identityType, String identityNo, String lostReason);

    /**
     * 永久挂失
     *
     * @param cardNo       卡号
     * @param identityType 证件类型
     * @param identityNo   证件号
     * @param lostReason   挂失原因
     * @return boolean
     */
    boolean creditCardReportLost(String cardNo, String identityType, String identityNo, String lostReason);


    /**
     * 解除临时挂失
     * @param cardNo 卡号
     * @param identityType 证件类型
     * @param identityNo 证件号
     * @return boolean
     */
    boolean relieveCreditCardTempReportLost(String cardNo,String identityType, String identityNo);
}
