package com.yada.wechatbank.model;

/**
 * 历史额度提升结果
 *
 * @author Tx
 */
public class CreditLimitTemporaryUpStatus {

    //工单ID
    private String eosId;
    //工单状态 70-审批批准并已同步CSR、0-待分配、0-待重新分配、20-待领取、30-审批中、40-转上级审批中、
    // 50-审批批准、60-审批拒绝、80-审批拒绝并已同步CSR、21-联机工单接入
    private String eosState;
    //申请时间
    private String eosImpTime;
    //工单额度
    private String eosLimit;
    //增额生效开始日期 格式YYYY-MM-DD
    private String eosStarLimitDate;
    //增额生效结束日期 格式YYYY-MM-DD
    private String eosEndLimitDate;

    public String getEosEndLimitDate() {
        return eosEndLimitDate;
    }

    public void setEosEndLimitDate(String eosEndLimitDate) {
        this.eosEndLimitDate = eosEndLimitDate;
    }

    public String getEosId() {
        return eosId;
    }

    public void setEosId(String eosId) {
        this.eosId = eosId;
    }

    public String getEosState() {
        return eosState;
    }

    public void setEosState(String eosState) {
        this.eosState = eosState;
    }

    public String getEosImpTime() {
        return eosImpTime;
    }

    public void setEosImpTime(String eosImpTime) {
        this.eosImpTime = eosImpTime;
    }

    public String getEosLimit() {
        return eosLimit;
    }

    public void setEosLimit(String eosLimit) {
        this.eosLimit = eosLimit;
    }

    public String getEosStarLimitDate() {
        return eosStarLimitDate;
    }

    public void setEosStarLimitDate(String eosStarLimitDate) {
        this.eosStarLimitDate = eosStarLimitDate;
    }



}
