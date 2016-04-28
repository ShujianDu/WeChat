package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.VerificationCardNoResult;

/**
 * Created by Echo on 2016/4/22.
 */
public class VerificationCardNoResultResp extends BaseModel{
    private VerificationCardNoResult data;

    public VerificationCardNoResult getData() {
        return data;
    }

    public void setData(VerificationCardNoResult data) {
        this.data = data;
    }
}
