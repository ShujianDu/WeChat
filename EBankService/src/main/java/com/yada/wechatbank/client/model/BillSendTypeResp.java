package com.yada.wechatbank.client.model;

import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.model.BillSendType;
import com.yada.wechatbank.model.BillingPeriod;

import java.util.List;

/**
 * 账单寄送方式返回实体
 * 
 * @author Tx
 *
 */
public class BillSendTypeResp extends BaseModel {
	private BillSendType data;

	public BillSendType getData() {
		return data;
	}

	public void setData(BillSendType data) {
		this.data = data;
	}

}
