package com.yada.wechatbank.service;

import com.yada.wechatbank.model.CreditLimitTemporaryUpReview;
import com.yada.wechatbank.model.CreditLimitTemporaryUpStatus;

import java.util.List;

/**
 * 临增额度Service接口
 *
 * @author Tx
 */
public interface CreditLimitTemporaryUpService {

    /**
     * 获取预处理后的卡列表
     *
     * @param identityType
     * @param identityNo
     * @return 返回卡列表（展示卡，加密卡信息）
     */
    List<String> getProessCardNoList(String identityType, String identityNo);


    /**
     * 临时提额授权
     * @param certType          客户证件类型
     * @param certNum           客户证件号码
     * @param cardNo            卡号
     * @param eosPreAddLimit    客户期望额度
     * @param eosStarLimitDate  增额生效开始日期
     * @param eosEndLimitDate   增额生效结束日期
     * @param cardStyle         产品类型 - 评测接口的返回结果
     * @param issuingBranchId   发卡行号 - 评测接口的返回结果
     * @param pmtCreditLimit    当前卡的永久额度 -评测接口的返回结果
     * @return 授权结果
     */
    Boolean temporaryUpCommit(String certType, String certNum, String cardNo, String eosPreAddLimit, String eosStarLimitDate, String eosEndLimitDate, String cardStyle, String issuingBranchId, String pmtCreditLimit);

    /**
     * 额度测评接口
     * @param certType      证件类型
     * @param certNum       证件号
     * @param cardNo        卡号
     * @return
     */
    CreditLimitTemporaryUpReview getAmount(String certType,String certNum,String cardNo);
    /**
     * 额度提升历史查询
     * @param certType      证件类型
     * @param certNum       证件号
     * @param cardNo        卡号
     * @return              历史提升额度信息
     */
    List<CreditLimitTemporaryUpStatus> getLimitUpHistory(String certType,String certNum,String cardNo);

    /**
     * 获取用户手机号
     * @param identityType   证件类型
     * @param identityNo     证件号
     * @return              手机号
     */
    String getCustMobileNo(String identityType,String identityNo);
}
