package com.yada.wechatbank.model;

import java.util.List;

/**
 * Created by Echo on 2016/4/21.
 */
public class HistoryInstallmentList {
    private String transactionNumber;

    private boolean isFollowUp;

    private List<HistoryInstallment> historyInstallmentList;

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

    public List<HistoryInstallment> getHistoryInstallmentList() {
        return historyInstallmentList;
    }

    public void setHistoryInstallmentList(List<HistoryInstallment> historyInstallmentList) {
        this.historyInstallmentList = historyInstallmentList;
    }
}
