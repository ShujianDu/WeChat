package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;

/**
 * 返回String通用实体
 *
 * @author Tx
 */
public class StringResp extends BaseModel {

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
