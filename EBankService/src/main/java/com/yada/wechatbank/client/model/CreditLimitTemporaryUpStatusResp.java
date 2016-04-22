package com.yada.wechatbank.client.model;

import com.yada.wechatbank.model.CreditLimitTemporaryUpStatus;

import java.util.List;

/**
 * Created by Tx on 2016/4/21.
 */
public class CreditLimitTemporaryUpStatusResp {

    private List<CreditLimitTemporaryUpStatus> bizResult;

    public List<CreditLimitTemporaryUpStatus> getBizResult() {
        return bizResult;
    }

    public void setBizResult(List<CreditLimitTemporaryUpStatus> bizResult) {
        this.bizResult = bizResult;
    }
}
