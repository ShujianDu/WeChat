package com.yada.wechatbank.service;


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
    List<String> getProessCardNoList(String identityType,String identityNo);


}
