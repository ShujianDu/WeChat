package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.BillingSummary;

/**
 * Created by liangtieluan on 2016/4/15.
 */
public class BillingSummaryResp extends BaseModel {
    private BillingSummary bizResult;

    public BillingSummary getBizResult() {
        return bizResult;
    }

    public void setBizResult(BillingSummary bizResult) {
        this.bizResult = bizResult;
    }
}
