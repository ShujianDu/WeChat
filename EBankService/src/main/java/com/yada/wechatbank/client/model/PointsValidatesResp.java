package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.PointsValidates;

import java.util.List;

/**
 * Created by Echo on 2016/4/13.
 */
public class PointsValidatesResp extends BaseModel {
    private List<PointsValidates> pointsValidatesList;

    public List<PointsValidates> getPointsValidatesList() {
        return pointsValidatesList;
    }

    public void setPointsValidatesList(List<PointsValidates> pointsValidatesList) {
        this.pointsValidatesList = pointsValidatesList;
    }
}
