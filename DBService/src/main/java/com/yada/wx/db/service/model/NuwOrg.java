package com.yada.wx.db.service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by QinQiang on 2016/4/6.
 */
@Entity(name = "T_B_NUW_ORG")
public class NuwOrg {

    @Id
    @Column(name = "ORG_ID", columnDefinition = "CHAR(6)", nullable = false)
    private String orgId; // 机构ID

    @Column(name = "P_ORG_ID", columnDefinition = "CHAR(6)")
    private String pOrgId; // 父机构ID

    @Column(name = "ORG_NAME", columnDefinition = "VARCHAR2(64)")
    private String orgName; // 机构名称

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getpOrgId() {
        return pOrgId;
    }

    public void setpOrgId(String pOrgId) {
        this.pOrgId = pOrgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
