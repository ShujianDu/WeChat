package com.yada.wechatbank.service;


import com.yada.wechatbank.model.AmountLimit;
import com.yada.wechatbank.model.BillCost;
import com.yada.wechatbank.model.BillingSummary;
import com.yada.wechatbank.model.CardInfo;

import java.util.List;

/**
 * �˵�����service�ӿ�
 * @author tx
 */
public interface BillInstallmentService {

    /**
     * ��ȡԤ�����Ŀ��б�
     * @return ���ؿ��б�չʾ�������ܿ���Ϣ��
     */
    List<CardInfo> getProessCardNoList(String identityType,String identityNo);

    /**
     * ͨ�����Ż�ȡ�����˵�
     * @param cardNo  ����
     * @return        �����˵�
     */
    BillingSummary getCurrentPeriodBill(String cardNo);


    /**
     * �˵����������߲�ѯ
     * @param cardNo        ����
     * @param currencyCode  ����
     * @return              �˵����������߽��
     */
    AmountLimit getAmountLimit(String cardNo,String currencyCode);

    /**
     * �˵���������
     * @param accountId                �˻�ID
     * @param accountNo                �˻���
     * @param currencyCode             ����
     * @param billLowerAmount          �˵��������޽��
     * @param billActualAmount         �˵�����ʵ�ʽ��
     * @param installmentsNumber       ��������
     * @param feeInstallmentsFlag      �����ѷ��ڱ�ʶ
     * @return                         ����������
     */
    BillCost getBillCost(String accountId,String accountNo,String currencyCode,String billLowerAmount,String billActualAmount, String installmentsNumber,String feeInstallmentsFlag);

    /**
     * �˵�������Ȩ
     * @param accountId                �˻�ID
     * @param accountNo                �˻���
     * @param cardNo                   ����
     * @param billLowerAmount          �˵��������޽��
     * @param billActualAmount         �˵�����ʵ�ʽ��
     * @param installmentsNumber       ��������
     * @param feeInstallmentsFlag      �����ѷ��ڱ�ʶ
     * @return                         �Ƿ����ɹ�
     */
    boolean billInstallment(String accountId,String accountNo,String cardNo,String currencyCode,String billLowerAmount,String billActualAmount, String installmentsNumber,String feeInstallmentsFlag);
}
