package com.yada.wx.around.client.model;

import java.util.List;

/**
 * Created by Echo on 2016/4/13.
 */
public class CardInfoResp extends BaseModel {

    private List<CardInfo> data;

    public List<CardInfo> getData() {
        return data;
    }

    public void setData(List<CardInfo> data) {
        this.data = data;
    }
}
