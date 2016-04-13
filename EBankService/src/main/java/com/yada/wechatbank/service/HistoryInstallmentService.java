package com.yada.wechatbank.service;


import java.util.List;
import java.util.Map;

/**
 * Created by Echo on 2016/4/12.
 */
public interface HistoryInstallmentService {

    /**
     * 查询分期历史
     * @param cardNo 卡号
     * @param startNumber 开始号
     * @param selectNumber 查询号
     * @return 分期历史信息
     */
    public Map<String, Object> queryHistoryInstallment(String cardNo, String startNumber, String selectNumber);

    /**
     * 查询卡列表
     * @param identityNo 证件号
     * @param identityType 证件类型
     * @return 卡列表
     */
    public List<String> selectCardNOs(String identityNo,String identityType);

}
