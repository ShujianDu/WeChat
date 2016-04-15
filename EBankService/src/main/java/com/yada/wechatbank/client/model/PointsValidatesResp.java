package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.PointsValidates;

import java.util.List;

/**
 * Created by Echo on 2016/4/13.
 */
public class PointsValidatesResp extends BaseModel {
    private List<PointsValidates> bizResult;

    public List<PointsValidates> getBizResult() {
        return bizResult;
    }

    public void setBizResult(List<PointsValidates> bizResult) {
        this.bizResult = bizResult;
    }
}
