package com.yada.wechatbank.model;

import java.util.List;

/**
 * Created by Echo on 2016/4/21.
 */
public class HistoryInstallmentList {
	private String transactionNumber;

	private boolean isFollowUp;

	private List<HistoryInstallment> entityList;

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public boolean isFollowUp() {
		return isFollowUp;
	}

	public void setFollowUp(boolean followUp) {
		isFollowUp = followUp;
	}

	public List<HistoryInstallment> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<HistoryInstallment> entityList) {
		this.entityList = entityList;
	}

}
