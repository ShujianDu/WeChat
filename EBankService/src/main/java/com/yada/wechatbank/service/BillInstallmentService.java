package com.yada.wechatbank.service;


import com.yada.wechatbank.model.AmountLimit;
import com.yada.wechatbank.model.BillCost;
import com.yada.wechatbank.model.BillingSummary;
import com.yada.wechatbank.model.CardInfo;
import java.util.List;

/**
 * 账单分期service接口
 * @author Tx
 */
public interface BillInstallmentService {

    /**
     * 获取预处理后的卡列表
     * @return 返回卡列表（展示卡，加密卡信息）
     */
    List<CardInfo> getProessCardNoList(String identityType,String identityNo);

    /**
     * 通过卡号获取档期账单
     * @param cardNo  卡号
     * @return        当期账单
     */
    BillingSummary getCurrentPeriodBill(String cardNo);

    /**
     * 账单分期上下线查询
     * @param cardNo        卡号
     * @param currencyCode  币种
     * @return              账单分期上下线结果
     */
    AmountLimit getAmountLimit(String cardNo,String currencyCode);

    /**
     * 账单分期试算
     * @param accountId                账户ID
     * @param accountNo                账户号
     * @param currencyCode             币种
     * @param billLowerAmount          账单分期下限金额
     * @param billActualAmount         账单分期实际金额
     * @param installmentsNumber       分期期数
     * @param feeInstallmentsFlag      手续费分期标识
     * @return                         返回试算结果
     */
    BillCost getBillCost(String accountId,String accountNo,String currencyCode,String billLowerAmount,String billActualAmount, String installmentsNumber,String feeInstallmentsFlag);

    /**
     * 账单分期授权
     * @param accountId                账户ID
     * @param accountNo                账户号
     * @param cardNo                   卡号
     * @param billLowerAmount          账单分期下限金额
     * @param billActualAmount         账单分期实际金额
     * @param installmentsNumber       分期期数
     * @param feeInstallmentsFlag      手续费分期标识
     * @return                         是否办理成功
     */
    boolean billInstallment(String accountId,String accountNo,String cardNo,String currencyCode,String billLowerAmount,String billActualAmount, String installmentsNumber,String feeInstallmentsFlag);

    /**
     * 验证手机号是否为用户注册的手机号
     * @param identityType    证件类型
     * @param identityNo      证件号
     * @param mobileNo        手机号
     * @return               正确返回双引/错误返回信息
     */
    String verificationMobileNo(String identityType,String identityNo,String mobileNo);
}
