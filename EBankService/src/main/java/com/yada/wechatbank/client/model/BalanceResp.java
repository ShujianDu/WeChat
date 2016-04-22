package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.Balance;
import com.yada.wechatbank.model.BillingDetail;

import java.util.List;

/**
 * 额度查询返回实体
 * 
 * @author Tx
 *
 */
public class BalanceResp extends BaseModel {
	private List<Balance> bizResult;

	public List<Balance> getBizResult() {
		return bizResult;
	}

	public void setBizResult(List<Balance> bizResult) {
		this.bizResult = bizResult;
	}

}
