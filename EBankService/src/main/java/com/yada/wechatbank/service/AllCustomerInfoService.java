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

    /**
     * 根据证件号修改推送状态
     *
     * @param notice          动户通知与否 0-不通知，1-通知
     * @param billNotice      账单通知与否 0-不通知，1-通知 默认为开通
     * @param repaymentNotice 还款提醒通知与否 0-不通知，1-通知 默认为开通
     * @param identityNo      证件号
     * @return Boolean
     */
    boolean updateNoticeByIdentityNo(String notice, String billNotice, String repaymentNotice, String identityNo);
}
