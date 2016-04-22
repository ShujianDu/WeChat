package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;

/**
 * 返回String通用实体
 * @author Tx
 */
public class StringResp extends BaseModel {

    private String bizResult;

    public String getBizResult() {
        return bizResult;
    }

    public void setBizResult(String bizResult) {
        this.bizResult = bizResult;
    }
}
