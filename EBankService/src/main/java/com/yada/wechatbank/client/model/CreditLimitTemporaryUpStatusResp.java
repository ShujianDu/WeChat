package com.yada.wechatbank.client.model;

import com.yada.wechatbank.model.CreditLimitTemporaryUpStatus;

import java.util.List;

/**
 * Created by Tx on 2016/4/21.
 */
public class CreditLimitTemporaryUpStatusResp {

    private List<CreditLimitTemporaryUpStatus> data;

    public List<CreditLimitTemporaryUpStatus> getData() {
        return data;
    }

    public void setData(List<CreditLimitTemporaryUpStatus> data) {
        this.data = data;
    }
}
