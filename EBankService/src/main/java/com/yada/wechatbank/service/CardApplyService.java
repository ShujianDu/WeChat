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
    public CardApplyList getCrdCardSchedule(String name, String identityType, String identityNo, int currentPage);
}
