package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;

import java.util.List;

/**
 * 返回list<String>通用实体
 * Created by pangChangSong on 2016/4/21.
 */
public class ListStringResp  extends BaseModel {

    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
