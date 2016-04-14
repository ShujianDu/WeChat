package com.yada.wechatbank.service;


import java.util.List;

/**
 * 账单分期service接口
 * @author tx
 */
public interface BillInstallmentService {


    /**
     * 获取预处理后的卡列表
     * @return 返回卡列表（展示卡，加密卡信息）
     */
    List<String> getProessCardNoList(String identityType,String identityNo);


}
