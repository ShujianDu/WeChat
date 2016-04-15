package com.yada.wechatbank.service;

import com.yada.wechatbank.model.PointsDetail;
import com.yada.wechatbank.model.PointsValidates;

import java.util.List;

/**
 * Created by Echo on 2016/4/12.
 */
public interface PointsService {
    /**
     * 获取积分明细
     * @param identityNo 证件号
     * @param identityType 证件类型
     * @return List<JifenDetail>
     */
    List<PointsDetail> getPointsDetail(String identityNo, String identityType);

    /**
     * 处理远程接口返回的积分明细集合
     * @param list 积分明细列表
     * @return List<List<JifenDetail>>
     */
    List<List<PointsDetail>> getList(List<PointsDetail> list);

    /**
     * 获取积分有效期
     * @param cardNo 卡号
     * @return List<PointsValidates>
     */
    List<PointsValidates> getPointsValidates(String cardNo);
}
