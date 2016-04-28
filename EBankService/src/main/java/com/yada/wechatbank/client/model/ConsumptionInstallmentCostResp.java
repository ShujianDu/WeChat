package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.ConsumptionInstallmentCost;

/**
 * 消费分期试算行内service返回结果
 * 
 * @author liangtieluan
 *
 */
public class ConsumptionInstallmentCostResp extends BaseModel {
	private ConsumptionInstallmentCost data;

	public ConsumptionInstallmentCost getData() {
		return data;
	}

	public void setData(ConsumptionInstallmentCost data) {
		this.data = data;
	}

}
