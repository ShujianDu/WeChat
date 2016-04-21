package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.ConsumptionInstallmentsesReceive;

/**
 * 行内service返回消费分期查询信息
 * 
 * @author liangtieluan
 *
 */
public class ConsumptionInstallmentsResp extends BaseModel {
	private ConsumptionInstallmentsesReceive bizResult;

	public ConsumptionInstallmentsesReceive getBizResult() {
		return bizResult;
	}

	public void setBizResult(ConsumptionInstallmentsesReceive bizResult) {
		this.bizResult = bizResult;
	}

}
