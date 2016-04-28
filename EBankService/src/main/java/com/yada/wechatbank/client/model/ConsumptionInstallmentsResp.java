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
	private ConsumptionInstallmentsesReceive data;

	public ConsumptionInstallmentsesReceive getData() {
		return data;
	}

	public void setData(ConsumptionInstallmentsesReceive data) {
		this.data = data;
	}

}
