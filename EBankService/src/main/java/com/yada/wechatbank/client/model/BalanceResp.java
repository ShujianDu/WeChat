package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.Balance;
import java.util.List;

/**
 * 额度查询返回实体
 *
 * @author Tx
 */
public class BalanceResp extends BaseModel {

    private List<Balance> data;

    public List<Balance> getData() {
        return data;
    }

    public void setData(List<Balance> data) {
        this.data = data;
    }
}
