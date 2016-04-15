package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.PointsDetail;

import java.util.List;

/**
 * Created by Echo on 2016/4/13.
 */
public class PointsDetailResp extends BaseModel {
    private List<PointsDetail> bizResult;

    public List<PointsDetail> getBizResult() {
        return bizResult;
    }

    public void setBizResult(List<PointsDetail> bizResult) {
        this.bizResult = bizResult;
    }
}
