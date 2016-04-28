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
	private List<BillingDetail> data;

	public List<BillingDetail> getData() {
		return data;
	}

	public void setData(List<BillingDetail> data) {
		this.data = data;
	}

}
