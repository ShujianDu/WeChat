package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;

/**
 * 返回String通用实体
 * Created by QinQiang on 2016/4/22.
 */
public class StringValueResp extends BaseModel {

    private String bizResult;

    public String getBizResult() {
        return bizResult;
    }

    public void setBizResult(String bizResult) {
        this.bizResult = bizResult;
    }
}
