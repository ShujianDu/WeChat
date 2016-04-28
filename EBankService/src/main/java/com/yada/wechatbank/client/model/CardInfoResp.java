package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.CardInfo;

import java.util.List;

/**
 * Created by Echo on 2016/4/13.
 */
public class CardInfoResp extends BaseModel{
    private List<CardInfo> data;


    public List<CardInfo> getData() {
        return data;
    }

    public void setData(List<CardInfo> data) {
        this.data = data;
    }
}
