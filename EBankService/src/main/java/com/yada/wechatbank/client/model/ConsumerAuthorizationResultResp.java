package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;

/**
 * 消费分期授权行内service返回结果
 *
 * @author liangtieluan
 */
public class ConsumerAuthorizationResultResp extends BaseModel {
	private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
