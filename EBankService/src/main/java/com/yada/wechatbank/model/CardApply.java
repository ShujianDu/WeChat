package com.yada.wechatbank.model;

/**
 * 预约办卡信息
 * Created by QinQiang on 2016/4/11.
 */
public class CardApply {
    // 申请编号
    private String applyNo;
    // 卡产品
    private String pdnDes;
    // 申请通过日期
    private String passDate;
    // 阶段编码
    private String phase;
    // 阶段描述
    private String phaseDesc;

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public String getPdnDes() {
        return pdnDes;
    }

    public void setPdnDes(String pdnDes) {
        this.pdnDes = pdnDes;
    }

    public String getPassDate() {
        return passDate;
    }

    public void setPassDate(String passDate) {
        this.passDate = passDate;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getPhaseDesc() {
        return phaseDesc;
    }

    public void setPhaseDesc(String phaseDesc) {
        this.phaseDesc = phaseDesc;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CardApply{");
        sb.append("applyNo='").append(applyNo).append('\'');
        sb.append(", pdnDes='").append(pdnDes).append('\'');
        sb.append(", passDate='").append(passDate).append('\'');
        sb.append(", phase='").append(phase).append('\'');
        sb.append(", phaseDesc='").append(phaseDesc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
