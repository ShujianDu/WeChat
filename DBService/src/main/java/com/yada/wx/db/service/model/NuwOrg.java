package com.yada.wx.db.service.model;

/**
 * Created by QinQiang on 2016/4/6.
 */
public class NuwOrg {

    private String orgId; // 机构ID
    private String pOrgId; // 父机构ID
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
