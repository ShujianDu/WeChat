package com.yada.wx.db.service.model;

/**
 * Created by QinQiang on 2016/4/6.
 */
public class InstallmentInfo {

    private String id;
    private String cardNo;
    private String tradingDec;
    private String currencyCode;
    private String instalCount;
    private String instalAmount;
    private String feeMethod;
    private String tradingDate;
    private String openId;
    private String status;
    private String gcsCode;
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getTradingDec() {
        return tradingDec;
    }

    public void setTradingDec(String tradingDec) {
        this.tradingDec = tradingDec;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getInstalCount() {
        return instalCount;
    }

    public void setInstalCount(String instalCount) {
        this.instalCount = instalCount;
    }

    public String getInstalAmount() {
        return instalAmount;
    }

    public void setInstalAmount(String instalAmount) {
        this.instalAmount = instalAmount;
    }

    public String getFeeMethod() {
        return feeMethod;
    }

    public void setFeeMethod(String feeMethod) {
        this.feeMethod = feeMethod;
    }

    public String getTradingDate() {
        return tradingDate;
    }

    public void setTradingDate(String tradingDate) {
        this.tradingDate = tradingDate;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGcsCode() {
        return gcsCode;
    }

    public void setGcsCode(String gcsCode) {
        this.gcsCode = gcsCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
