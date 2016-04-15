package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;

/**
 * Created by Echo on 2016/4/13.
 */
public class CustMobileResp extends BaseModel{
    private String bizResult;

    public String getBizResult() {
        return bizResult;
    }

    public void setBizResult(String bizResult) {
        this.bizResult = bizResult;
    }
}
