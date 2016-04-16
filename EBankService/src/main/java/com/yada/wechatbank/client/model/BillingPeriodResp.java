package com.yada.wechatbank.client.model;

import java.util.List;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.BillingPeriod;

/**
 * 查询账期行内service返回实体
 * 
 * @author liangtieluan
 *
 */
public class BillingPeriodResp extends BaseModel {
	private List<BillingPeriod> bizResult;

	public List<BillingPeriod> getBizResult() {
		return bizResult;
	}

	public void setBizResult(List<BillingPeriod> bizResult) {
		this.bizResult = bizResult;
	}

}
