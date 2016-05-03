package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.CardHolderInfo;


/**
 * 个人资料返回实体
 *
 * @author Tx
 */
public class CardHolderInfoResp extends BaseModel {
    private CardHolderInfo data;

    public CardHolderInfo getData() {
        return data;
    }

    public void setData(CardHolderInfo data) {
        this.data = data;
    }

}
