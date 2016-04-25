package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.BillCost;


/**
 * 账单分期试算结果
 *
 * @author Tx
 */
public class BillCostResp extends BaseModel {
    private BillCost bizResult;

    public BillCost getBizResult() {
        return bizResult;
    }

    public void setBizResult(BillCost bizResult) {
        this.bizResult = bizResult;
    }
}
