package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.PointsBalance;

/**
 * Created by Echo on 2016/4/21.
 */
public class PointsBalanceResp extends BaseModel{
    private PointsBalance pointsBlance;

    public PointsBalance getPointsBlance() {
        return pointsBlance;
    }

    public void setPointsBlance(PointsBalance pointsBlance) {
        this.pointsBlance = pointsBlance;
    }
}
