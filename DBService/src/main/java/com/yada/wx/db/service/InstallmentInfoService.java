package com.yada.wx.db.service;


import com.yada.wx.db.service.model.InstallmentInfo;

/**
 * Created by QinQiang on 2016/4/6.
 */
public interface InstallmentInfoService {
    /**
     * 账单分期/消费分期 保存
     *
     * @param info 账单分期/消费分期
     * @return
     */
    public boolean insertInstallmentInfo(InstallmentInfo info);
}
