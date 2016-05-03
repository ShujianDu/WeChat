package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.CreditLimitTemporaryUpStatus;

import java.util.List;

/**
 * 临增额度Resp
 * Created by Tx on 2016/4/21.
 */
public class CreditLimitTemporaryUpStatusResp extends BaseModel{

    private List<CreditLimitTemporaryUpStatus> data;

    public List<CreditLimitTemporaryUpStatus> getData() {
        return data;
    }

    public void setData(List<CreditLimitTemporaryUpStatus> data) {
        this.data = data;
    }
}
