package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;

import java.util.List;

/**
 * 返回list<String>通用实体
 * Created by pangChangSong on 2016/4/21.
 */
public class ListStringResp  extends BaseModel {

    private List<String> bizResult;

    public List<String> getBizResult() {
        return bizResult;
    }

    public void setBizResult(List<String> bizResult) {
        this.bizResult = bizResult;
    }
}
