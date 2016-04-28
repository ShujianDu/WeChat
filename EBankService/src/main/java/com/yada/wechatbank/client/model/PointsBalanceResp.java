package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.PointsBalance;

/**
 * Created by Echo on 2016/4/21.
 */
public class PointsBalanceResp extends BaseModel{
    private PointsBalance data;

    public PointsBalance getData() {
        return data;
    }

    public void setData(PointsBalance data) {
        this.data = data;
    }
}
