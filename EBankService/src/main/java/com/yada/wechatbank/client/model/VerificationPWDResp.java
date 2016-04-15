package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;

/**
 * Created by Echo on 2016/4/13.
 */
public class VerificationPWDResp extends BaseModel{
    private boolean result;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
