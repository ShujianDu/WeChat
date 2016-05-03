package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.AmountLimit;

/**
 * 账单上下线查询返回实体
 *
 * @author Tx
 */
public class AmountLimitResp extends BaseModel {
    private AmountLimit data;

    public AmountLimit getData() {
        return data;
    }

    public void setData(AmountLimit data) {
        this.data = data;
    }

}
