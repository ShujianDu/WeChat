package com.yada.wechatbank.model;

/**
 * 额度测评结果实体
 *
 * @author Tx
 */
public class CreditLimitTemporaryUpReview {

    //授权是否批准 （A-批准 B-拒绝）
    private String principalResultID;
    //建议额度
    private String amount;
    //产品类型（1，个人；2，白金；3，公务卡）
    private String cardStyle;
    //发卡行号
    private String issuingBranchId;
    //当前卡整体信用额度
    private String creditLimit;
    //当前卡的永久额度
    private String pmtCreditLimit;


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPrincipalResultID() {
        return principalResultID;
    }

    public void setPrincipalResultID(String principalResultID) {
        this.principalResultID = principalResultID;
    }

    public String getCardStyle() {
        return cardStyle;
    }

    public void setCardStyle(String cardStyle) {
        this.cardStyle = cardStyle;
    }

    public String getIssuingBranchId() {
        return issuingBranchId;
    }

    public void setIssuingBranchId(String issuingBranchId) {
        this.issuingBranchId = issuingBranchId;
    }

    public String getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getPmtCreditLimit() {
        return pmtCreditLimit;
    }

    public void setPmtCreditLimit(String pmtCreditLimit) {
        this.pmtCreditLimit = pmtCreditLimit;
    }
}
