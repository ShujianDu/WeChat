package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.BillingSummary;

/**
 * 账单摘要返回实体
 * 
 * @author liangtieluan
 *
 */
public class BillingSummaryResp extends BaseModel {
	private BillingSummary data;

	public BillingSummary getData() {
		return data;
	}

	public void setData(BillingSummary data) {
		this.data = data;
	}

}
