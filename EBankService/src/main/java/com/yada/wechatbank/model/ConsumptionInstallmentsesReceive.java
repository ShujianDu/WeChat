package com.yada.wechatbank.model;

import java.util.List;

/**
 * 查询可用消费分期信息行内service返回实体
 * 
 * @author liangtieluan
 *
 */
public class ConsumptionInstallmentsesReceive {
	// 交易数量
	private String transactionNumber;
	// 是否有下一页
	private boolean hasNext;
	// 交易集合
	private List<ConsumptionInstallments> gcsConsumptionInstallmentsEntitys;

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public List<ConsumptionInstallments> getGcsConsumptionInstallmentsEntitys() {
		return gcsConsumptionInstallmentsEntitys;
	}

	public void setGcsConsumptionInstallmentsEntitys(
			List<ConsumptionInstallments> gcsConsumptionInstallmentsEntitys) {
		this.gcsConsumptionInstallmentsEntitys = gcsConsumptionInstallmentsEntitys;
	}
	

}
