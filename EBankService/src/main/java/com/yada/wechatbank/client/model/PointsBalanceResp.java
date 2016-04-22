package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.PointsBalance;

/**
 * Created by Echo on 2016/4/21.
 */
public class PointsBalanceResp extends BaseModel{
    private PointsBalance bizResult;

    public PointsBalance getBizResult() {
        return bizResult;
    }

    public void setBizResult(PointsBalance bizResult) {
        this.bizResult = bizResult;
    }
}
