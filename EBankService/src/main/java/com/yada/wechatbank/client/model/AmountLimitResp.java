package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.AmountLimit;
import com.yada.wechatbank.model.Balance;

import java.util.List;

/**
 * 账单上下线查询返回实体
 * 
 * @author Tx
 *
 */
public class AmountLimitResp extends BaseModel {
	private AmountLimit bizResult;

	public AmountLimit getBizResult() {
		return bizResult;
	}

	public void setBizResult(AmountLimit bizResult) {
		this.bizResult = bizResult;
	}

}
