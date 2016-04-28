package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.PointsValidates;

import java.util.List;

/**
 * Created by Echo on 2016/4/13.
 */
public class PointsValidatesResp extends BaseModel {
    private List<PointsValidates> data;

    public List<PointsValidates> getData() {
        return data;
    }

    public void setData(List<PointsValidates> data) {
        this.data = data;
    }
}
