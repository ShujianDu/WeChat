package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;

/**
 * 返回Boolean通用实体
 * Created by QinQiang on 2016/4/15.
 */
public class BooleanResp extends BaseModel {

    private Boolean data;

    public Boolean getData() {
        return data;
    }

    public void setData(Boolean data) {
        this.data = data;
    }
}
