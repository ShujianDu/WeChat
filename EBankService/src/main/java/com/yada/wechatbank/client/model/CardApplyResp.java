package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.CardApplyList;

/**
 * 预约办卡进度查询
 * Created by QinQiang on 2016/4/15.
 */
public class CardApplyResp extends BaseModel {

    private CardApplyList bizResult;

    public CardApplyList getBizResult() {
        return bizResult;
    }

    public void setBizResult(CardApplyList bizResult) {
        this.bizResult = bizResult;
    }
}
