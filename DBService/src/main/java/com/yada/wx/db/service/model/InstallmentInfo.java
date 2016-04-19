package com.yada.wx.db.service.model;

import javax.persistence.*;

/**
 * Created by QinQiang on 2016/4/6.
 * 分期流水表
 */
@Entity(name = "T_B_INSTALLMENT_INFO")
public class InstallmentInfo {

    @Id
    @GeneratedValue(generator="INSTALLMENT_INFO_SEQ")
    @SequenceGenerator(name="INSTALLMENT_INFO_SEQ", sequenceName="SEQ_T_B_INSTALLMENT_INFO")
    @Column(name = "ID", columnDefinition = "VARCHAR2(32)", nullable = false)
    private Long id; //ID

    @Column(name = "CARDNO", columnDefinition = "VARCHAR2(19)")
    private String cardNo; //卡号

    @Column(name = "TRADINGDEC", columnDefinition = "VARCHAR2(32)")
    private String tradingDec; //交易描述

    @Column(name = "CURRENCYCODE", columnDefinition = "VARCHAR2(8)")
    private String currencyCode; //币种

    @Column(name = "INSTALCOUNT", columnDefinition = "CHAR(4)")
    private String instalCount; //分期期数

    @Column(name = "INSTALAMOUNT", columnDefinition = "VARCHAR2(128)")
    private String instalAmount; //分期金额

    @Column(name = "FEEMETHOD", columnDefinition = "CHAR(1)")
    private String feeMethod; //分期手续费收取方式

    @Column(name = "TRADINGDATE", columnDefinition = "VARCHAR2(14)")
    private String tradingDate; //交易日期

    @Column(name = "OPENID", columnDefinition = "VARCHAR2(32)")
    private String openId; //openid

    @Column(name = "STATUS", columnDefinition = "CHAR(1)")
    private String status; //交易状态,0,失败,1,成功

    @Column(name = "GCSCODE", columnDefinition = "VARCHAR2(10)")
    private String gcsCode; //GCS报文头返回码（+GC00000=Success）

    @Column(name = "REMARK", columnDefinition = "NVARCHAR2(50)")
    private String remark; //备注

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public InstallmentInfo(String cardNo, String tradingDec, String currencyCode,
                           String instalAmount, String instalCount, String feeMethod,String tradingDate) {
        this.cardNo = cardNo;
        this.tradingDec = tradingDec;
        this.currencyCode = currencyCode;
        this.instalAmount = instalAmount;
        this.instalCount = instalCount;
        this.feeMethod = feeMethod;
        this.tradingDate = tradingDate;
    }
    public InstallmentInfo(){}




}
