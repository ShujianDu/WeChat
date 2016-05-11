package com.yada.wechatbank.service;

import com.yada.wechatbank.model.CardHolderInfo;

/**
 * 个人资料service
 *
 * @author tx
 */

public interface CardHolderInfoService {

    /**
     * * 获取客户个人信息
     *
     * @param identityType
     * @param identityNo
     * @return 隐藏并处理后的个人信息数据
     */

    CardHolderInfo getCardHolderInfo(String identityType, String identityNo);
}
