package com.yada.wechatbank.service;

import com.yada.wechatbank.model.JifenDetail;
import com.yada.wechatbank.model.JifenValidate;

import java.util.List;

/**
 * Created by Echo on 2016/4/12.
 */
public interface JifenService {
    /**
     * 获取积分明细
     * @param identityNo 证件号
     * @param identityType 证件类型
     * @return List<JifenDetail>
     */
    public List<JifenDetail> getJifenDetail(String identityNo,String identityType);

    /**
     * 处理远程接口返回的积分明细集合
     * @param list 积分明细列表
     * @return List<List<JifenDetail>>
     */
    public List<List<JifenDetail>> getList(List<JifenDetail> list);

    /**
     * 获取积分有效期
     * @param cardNo 卡号
     * @return List<JifenValidate>
     */
    public List<JifenValidate> getJifenValidates(String cardNo);
}
