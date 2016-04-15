package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.CardInfo;

import java.util.List;

/**
 * Created by Echo on 2016/4/13.
 */
public class CardInfoResp extends BaseModel{
    private List<CardInfo> bizResult;


    public List<CardInfo> getBizResult() {
        return bizResult;
    }

    public void setBizResult(List<CardInfo> bizResult) {
        this.bizResult = bizResult;
    }
}
