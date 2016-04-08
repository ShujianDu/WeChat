package com.yada.wx.db.service.model;

import javax.persistence.*;

/**
 * Created by QinQiang on 2016/4/6.
 * 客户信息
 */
@Entity(name = "T_B_CUSTOMER_INFO")
public class CustomerInfo {

    @Id
    @GeneratedValue(generator="CUSTOMER_INFO_SEQ")
    @SequenceGenerator(name="CUSTOMER_INFO_SEQ", sequenceName="SEQ_T_B_CUSTOMER_INFO")
    @Column(name = "ID", columnDefinition = "VARCHAR2(32)", nullable = false)
    private Long id; // ID

    @Column(name = "OPENID", columnDefinition = "VARCHAR2(32)")
    private String openId; // OPENID

    @Column(name = "IDENTITY_TYPE", columnDefinition = "VARCHAR2(10)")
    private String identityType; // 证件类型

    @Column(name = "IDENTITY_NO", columnDefinition = "VARCHAR2(32)")
    private String identityNo; // 证件号

    @Column(name = "MOBILE_PHONE", columnDefinition = "VARCHAR2(20)")
    private String mobilePhone; // 手机号

    @Column(name = "DEF_CARD_NO", columnDefinition = "VARCHAR2(19)")
    private String defCardNo; // 默认卡号

    @Column(name = "BINDING_DATE", columnDefinition = "VARCHAR2(8)")
    private String bindingDate; // 绑定日期

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getDefCardNo() {
        return defCardNo;
    }

    public void setDefCardNo(String defCardNo) {
        this.defCardNo = defCardNo;
    }

    public String getBindingDate() {
        return bindingDate;
    }

    public void setBindingDate(String bindingDate) {
        this.bindingDate = bindingDate;
    }
}
