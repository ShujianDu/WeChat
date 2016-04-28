package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.BillCost;


/**
 * 账单分期试算结果
 *
 * @author Tx
 */
public class BillCostResp extends BaseModel {
    private BillCost data;

    public BillCost getData() {
        return data;
    }

    public void setData(BillCost data) {
        this.data = data;
    }
}
