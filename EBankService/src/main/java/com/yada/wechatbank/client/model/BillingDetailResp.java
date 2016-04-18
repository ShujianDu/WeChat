package com.yada.wechatbank.client.model;

import java.util.List;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.BillingDetail;

/**
 * 账单明细返回实体
 * 
 * @author liangtieluan
 *
 */
public class BillingDetailResp extends BaseModel {
	private List<BillingDetail> bizResult;

	public List<BillingDetail> getBizResult() {
		return bizResult;
	}

	public void setBizResult(List<BillingDetail> bizResult) {
		this.bizResult = bizResult;
	}

}
