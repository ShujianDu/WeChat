package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.AmountLimit;
import com.yada.wechatbank.model.CreditLimitTemporaryUpReview;

/**
 * 账单上下线查询返回实体
 *
 * @author Tx
 */
public class CreditLimitTemporaryUpReviewResp extends BaseModel {

    private CreditLimitTemporaryUpReview data;

    public CreditLimitTemporaryUpReview getData() {
        return data;
    }

    public void setData(CreditLimitTemporaryUpReview data) {
        this.data = data;
    }

}
