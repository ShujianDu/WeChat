package com.yada.wechatbank.model;

/**
 * 额度实体
 *
 * @author Tx
 */
public class Balance {
    // 卡号
    private String cardNo;
    // 金额类型(币种)
    private String currencyCode;
    // 金额中文描述(币种)
    private String currencyChinaCode;
    // 总授信额度
    private String wholeCreditLimit;
    // 总可用额
    private String periodAvailableCreditLimit;
    // 取现可用额度
    private String preCashAdvanceCreditLimit;

    public String getWholeCreditLimit() {
        return wholeCreditLimit;
    }

    public void setWholeCreditLimit(String wholeCreditLimit) {
        this.wholeCreditLimit = wholeCreditLimit;
    }

    public String getPeriodAvailableCreditLimit() {
        return periodAvailableCreditLimit;
    }

    public void setPeriodAvailableCreditLimit(String periodAvailableCreditLimit) {
        this.periodAvailableCreditLimit = periodAvailableCreditLimit;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyChinaCode() {
        return currencyChinaCode;
    }

    public void setCurrencyChinaCode(String currencyChinaCode) {
        this.currencyChinaCode = currencyChinaCode;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPreCashAdvanceCreditLimit() {
        return preCashAdvanceCreditLimit;
    }

    public void setPreCashAdvanceCreditLimit(String preCashAdvanceCreditLimit) {
        this.preCashAdvanceCreditLimit = preCashAdvanceCreditLimit;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Balance{");
        sb.append("cardNo='").append(cardNo).append('\'');
        sb.append(", currencyCode='").append(currencyCode).append('\'');
        sb.append(", currencyChinaCode='").append(currencyChinaCode).append('\'');
        sb.append(", wholeCreditLimit='").append(wholeCreditLimit).append('\'');
        sb.append(", periodAvailableCreditLimit='").append(periodAvailableCreditLimit).append('\'');
        sb.append(", preCashAdvanceCreditLimit='").append(preCashAdvanceCreditLimit).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
