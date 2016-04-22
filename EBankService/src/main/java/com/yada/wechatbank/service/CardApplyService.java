package com.yada.wechatbank.service;

import com.yada.wechatbank.model.CardApplyList;

/**
 * 预约办卡进度查询
 * Created by QinQiang on 2016/4/11.
 */
public interface CardApplyService {
    /**
     * 预约办卡进度查询
     *
     * @param name         姓名
     * @param identityType 证件类型
     * @param identityNo   证件号
     * @param currentPage  当前页
     * @return CardApplyList
     */
    CardApplyList getCrdCardSchedule(String name, String identityType, String identityNo, int currentPage);

    /**
     * 发送短信验证码
     *
     * @param identityType 证件类型
     * @param identityNo   证件号
     * @param mobileNo     手机号
     * @return String
     */
    String sendCardApplySMS(String identityType, String identityNo, String mobileNo);

    /**
     * 验证短信验证码
     *
     * @param identityNo 证件号
     * @param mobileNo   手机号
     * @param code       验证码
     * @return boolean
     */
    boolean checkCardApplySMSCode(String identityNo, String mobileNo, String code);
}
