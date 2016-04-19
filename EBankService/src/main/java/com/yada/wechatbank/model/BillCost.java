package com.yada.wechatbank.model;

import java.io.Serializable;

public class BillCost implements Serializable {

	private static final long serialVersionUID = 3649680086352326093L;
	/**
	 * 本期账单最小还款额
	 */
	public String currentBillMinimum;
	/**
	 * 分期手续费
	 */
	public String installmentsfee;
	/**
	 * 分期后每期应还金额-首期
	 */
	public String installmentsAlsoAmountFirst;
	/**
	 * 分期后每期应还金额-后每期
	 */
	public String installmentsAlsoAmountEach;
	/**
	 * 本期账单剩余还款金额
	 */
	public String currentBillSurplusAmount;
	/**
	 * 分期手续费收取方式
	 */
	public String billFeeMeans;
	/**
	 * 分期期数
	 */
	public String installmentsNumber;
	/**
	 * 渠道标识
	 */
	public String channelId;

	/**
	 * 本期账单最小还款额
	 */
	public String getCurrentBillMinimum() {
		return currentBillMinimum;
	}

	/**
	 * 本期账单最小还款额
	 */
	public void setCurrentBillMinimum(String currentBillMinimum) {
		this.currentBillMinimum = currentBillMinimum;
	}

	/**
	 * 分期手续费
	 */
	public String getInstallmentsfee() {
		return installmentsfee;
	}

	/**
	 * 分期手续费
	 */
	public void setInstallmentsfee(String installmentsfee) {
		this.installmentsfee = installmentsfee;
	}

	/**
	 * 分期后每期应还金额-首期
	 */
	public String getInstallmentsAlsoAmountFirst() {
		return installmentsAlsoAmountFirst;
	}

	/**
	 * 分期后每期应还金额-首期
	 */
	public void setInstallmentsAlsoAmountFirst(
			String installmentsAlsoAmountFirst) {
		this.installmentsAlsoAmountFirst = installmentsAlsoAmountFirst;
	}

	/**
	 * 分期后每期应还金额-后每期
	 */
	public String getInstallmentsAlsoAmountEach() {
		return installmentsAlsoAmountEach;
	}

	/**
	 * 分期后每期应还金额-后每期
	 */
	public void setInstallmentsAlsoAmountEach(String installmentsAlsoAmountEach) {
		this.installmentsAlsoAmountEach = installmentsAlsoAmountEach;
	}

	/**
	 * 本期账单剩余还款金额
	 */
	public String getCurrentBillSurplusAmount() {
		return currentBillSurplusAmount;
	}

	/**
	 * 本期账单剩余还款金额
	 */
	public void setCurrentBillSurplusAmount(String currentBillSurplusAmount) {
		this.currentBillSurplusAmount = currentBillSurplusAmount;
	}

	/**
	 * 分期手续费收取方式
	 */
	public String getBillFeeMeans() {
		return billFeeMeans;
	}

	/**
	 * 分期手续费收取方式
	 */
	public void setBillFeeMeans(String billFeeMeans) {
		this.billFeeMeans = billFeeMeans;
	}

	/**
	 * 分期期数
	 */
	public String getInstallmentsNumber() {
		return installmentsNumber;
	}

	/**
	 * 分期期数
	 */
	public void setInstallmentsNumber(String installmentsNumber) {
		this.installmentsNumber = installmentsNumber;
	}

	/**
	 * 渠道标识
	 */
	public String getChannelId() {
		return channelId;
	}

	/**
	 * 渠道标识
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Override
	public String toString() {
		return "BillCost [currentBillMinimum=" + currentBillMinimum
				+ ", installmentsfee=" + installmentsfee
				+ ", installmentsAlsoAmountFirst="
				+ installmentsAlsoAmountFirst + ", installmentsAlsoAmountEach="
				+ installmentsAlsoAmountEach + ", currentBillSurplusAmount="
				+ currentBillSurplusAmount + ", billFeeMeans=" + billFeeMeans
				+ ", installmentsNumber=" + installmentsNumber + ", channelId="
				+ channelId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((billFeeMeans == null) ? 0 : billFeeMeans.hashCode());
		result = prime * result
				+ ((channelId == null) ? 0 : channelId.hashCode());
		result = prime
				* result
				+ ((currentBillMinimum == null) ? 0 : currentBillMinimum
						.hashCode());
		result = prime
				* result
				+ ((currentBillSurplusAmount == null) ? 0
						: currentBillSurplusAmount.hashCode());
		result = prime
				* result
				+ ((installmentsAlsoAmountEach == null) ? 0
						: installmentsAlsoAmountEach.hashCode());
		result = prime
				* result
				+ ((installmentsAlsoAmountFirst == null) ? 0
						: installmentsAlsoAmountFirst.hashCode());
		result = prime
				* result
				+ ((installmentsNumber == null) ? 0 : installmentsNumber
						.hashCode());
		result = prime * result
				+ ((installmentsfee == null) ? 0 : installmentsfee.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BillCost other = (BillCost) obj;
		if (billFeeMeans == null) {
			if (other.billFeeMeans != null)
				return false;
		} else if (!billFeeMeans.equals(other.billFeeMeans))
			return false;
		if (channelId == null) {
			if (other.channelId != null)
				return false;
		} else if (!channelId.equals(other.channelId))
			return false;
		if (currentBillMinimum == null) {
			if (other.currentBillMinimum != null)
				return false;
		} else if (!currentBillMinimum.equals(other.currentBillMinimum))
			return false;
		if (currentBillSurplusAmount == null) {
			if (other.currentBillSurplusAmount != null)
				return false;
		} else if (!currentBillSurplusAmount
				.equals(other.currentBillSurplusAmount))
			return false;
		if (installmentsAlsoAmountEach == null) {
			if (other.installmentsAlsoAmountEach != null)
				return false;
		} else if (!installmentsAlsoAmountEach
				.equals(other.installmentsAlsoAmountEach))
			return false;
		if (installmentsAlsoAmountFirst == null) {
			if (other.installmentsAlsoAmountFirst != null)
				return false;
		} else if (!installmentsAlsoAmountFirst
				.equals(other.installmentsAlsoAmountFirst))
			return false;
		if (installmentsNumber == null) {
			if (other.installmentsNumber != null)
				return false;
		} else if (!installmentsNumber.equals(other.installmentsNumber))
			return false;
		if (installmentsfee == null) {
			if (other.installmentsfee != null)
				return false;
		} else if (!installmentsfee.equals(other.installmentsfee))
			return false;
		return true;
	}

}
