package com.yada.wechatbank.service;

import com.yada.wx.db.service.model.AllCustomerInfo;

import java.util.List;

/**
 * 推送管理
 * Created by QinQiang on 2016/4/15.
 */
public interface AllCustomerInfoService {
    /**
     * 通过证件号获取推送管理信息
     *
     * @param identityNo 证件号
     * @return List<AllCustomerInfo>
     */
    List<AllCustomerInfo> findByIdentityNo(String identityNo);

    boolean updateNoticeByIdentityNo(String notice, String billNotice, String repaymentNotice, String identityNo);
}
