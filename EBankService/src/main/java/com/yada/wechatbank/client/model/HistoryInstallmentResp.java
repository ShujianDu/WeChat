package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.HistoryInstallmentList;

/**
 * Created by Echo on 2016/4/21.
 */
public class HistoryInstallmentResp extends BaseModel {
    private HistoryInstallmentList data;

    public HistoryInstallmentList getData() {
        return data;
    }

    public void setData(HistoryInstallmentList data) {
        this.data = data;
    }
}
