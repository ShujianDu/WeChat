package com.yada.wechatbank.service;

import com.yada.wechatbank.model.PointsBalance;
import com.yada.wechatbank.model.PointsDetail;
import com.yada.wechatbank.model.PointsValidates;
import com.yada.wechatbank.model.VerificationCardNoResult;

import java.util.List;

/**
 * Created by Echo on 2016/4/12.
 */
public interface PointsService {

    /**
     * 获取账户积分余额
     *
     * @return
     */
    PointsBalance getPointsBlance(String identityType, String identityNo);

    /**
     * 获取积分明细
     *
     * @param identityType 证件类型
     * @param identityNo   证件号
     * @return List<JifenDetail>
     */
    List<PointsDetail> getPointsDetail(String identityType, String identityNo);

    /**
     * 处理远程接口返回的积分明细集合
     *
     * @param list 积分明细列表
     * @return List<List<JifenDetail>>
     */
    List<List<PointsDetail>> getList(List<PointsDetail> list);

    /**
     * 获取积分有效期
     *
     * @param cardNo 卡号
     * @return List<PointsValidates>
     */
    List<PointsValidates> getPointsValidates(String cardNo);

    /**
     * 获取客户卡号
     *
     * @param identityType
     * @param identityNo
     * @return
     */
    String getCardNo(String identityType, String identityNo);

    /**
     * 通过卡号获取加密后的卡号和sign
     *
     * @param cardNo
     * @return
     */
    VerificationCardNoResult verificationCardNo(String cardNo);
}
