package com.yada.wx.db.service.model;

import javax.persistence.*;

/**
 * 动户通知表
 * Created by QinQiang on 2016/4/15.
 */
@Entity(name = "WXDH_T_B_ALL_CUSTOMER_INFO")
public class AllCustomerInfo {

    @Id
    @GeneratedValue(generator = "T_B_ALL_CUSTOMER_INFO_SEQ")
    @SequenceGenerator(name = "T_B_ALL_CUSTOMER_INFO_SEQ", sequenceName = "WXDH_SEQ_T_B_ALL_CUSTOMER_INFO")
    @Column(name = "ID", columnDefinition = "VARCHAR2(32)", nullable = false)
    private Long id; // ID

    @Column(name = "OPENID", columnDefinition = "VARCHAR2(32)")
    private String openId; // OPENID

    @Column(name = "IDENTITY_TYPE", columnDefinition = "VARCHAR2(255 CHAR)")
    private String identityType; // 证件类型 01-居民身份证 02-#临时身份证 03-护照 04-户口簿 05-军人身份证 06-武装警察身份证 47-港澳居民来往内地通行证（香港）48-港澳居民来往内地通行证（澳门）49-台湾居民往来大陆通行证 08-外交人员身份证 09-外国人居留许可证 10-边民出入境通行证 11-其他 33-组织机构代码

    @Column(name = "IDENTITY_NO", columnDefinition = "VARCHAR2(32)")
    private String identityNo; // 身份证号

    @Column(name = "MOBILE_PHONE", columnDefinition = "VARCHAR2(20)")
    private String mobilePhone; // 手机号

    @Column(name = "FAMILY_NAME", columnDefinition = "VARCHAR2(60)")
    private String familyName; // 姓

    @Column(name = "FIRST_NAME", columnDefinition = "VARCHAR2(60)")
    private String firstName; // 名

    @Column(name = "SEX", columnDefinition = "CHAR(4)")
    private String sex; // 性别:MALE-男性 FEML-女性

    @Column(name = "BINDING_DATE", columnDefinition = "VARCHAR2(8)")
    private String bindingDate; // 绑定日期

    @Column(name = "CARDNO", columnDefinition = "VARCHAR2(19)")
    private String cardNo; // 卡号

    @Column(name = "CARD_TYPE", columnDefinition = "VARCHAR2(32)")
    private String cardType; // 卡类

    @Column(name = "CARD_LAST_FOUR_NUMBER", columnDefinition = "VARCHAR2(4)")
    private String cardLastFourNumber; // 卡号后四位

    @Column(name = "STYLE", columnDefinition = "VARCHAR2(128)")
    private String style; // 产品名称

    @Column(name = "IS_DEFAULT", columnDefinition = "CHAR(1)")
    private String isDefault; // 是否是默认卡(0 是，1 否)

    @Column(name = "ACC_ID", columnDefinition = "VARCHAR2(32)")
    private String accId; // 微信账号ID(关注账号)

    @Column(name = "NOTICE", columnDefinition = "CHAR(1)")
    private String notice; // 动户通知与否 0-不通知，1-通知

    @Column(name = "ADD1", columnDefinition = "VARCHAR2(128)")
    private String add1; // 备用字段1

    @Column(name = "ADD2", columnDefinition = "VARCHAR2(128)")
    private String add2; // 备用字段2

    @Column(name = "ADD3", columnDefinition = "VARCHAR2(128)")
    private String add3; // 备用字段3

    @Column(name = "ADD4", columnDefinition = "VARCHAR2(128)")
    private String add4; // 备用字段4

    @Column(name = "ADD5", columnDefinition = "VARCHAR2(128)")
    private String add5; // 备用字段5

    @Column(name = "BILL_NOTICE", columnDefinition = "CHAR(1)")
    private String billNotice; // 账单通知与否 0-不通知，1-通知 默认为开通

    @Column(name = "REPAYMENT_NOTICE", columnDefinition = "CHAR(1)")
    private String repaymentNotice; // 还款提醒通知与否 0-不通知，1-通知 默认为开通

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

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBindingDate() {
        return bindingDate;
    }

    public void setBindingDate(String bindingDate) {
        this.bindingDate = bindingDate;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardLastFourNumber() {
        return cardLastFourNumber;
    }

    public void setCardLastFourNumber(String cardLastFourNumber) {
        this.cardLastFourNumber = cardLastFourNumber;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getAccId() {
        return accId;
    }

    public void setAccId(String accId) {
        this.accId = accId;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getAdd1() {
        return add1;
    }

    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    public String getAdd2() {
        return add2;
    }

    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    public String getAdd3() {
        return add3;
    }

    public void setAdd3(String add3) {
        this.add3 = add3;
    }

    public String getAdd4() {
        return add4;
    }

    public void setAdd4(String add4) {
        this.add4 = add4;
    }

    public String getAdd5() {
        return add5;
    }

    public void setAdd5(String add5) {
        this.add5 = add5;
    }

    public String getBillNotice() {
        return billNotice;
    }

    public void setBillNotice(String billNotice) {
        this.billNotice = billNotice;
    }

    public String getRepaymentNotice() {
        return repaymentNotice;
    }

    public void setRepaymentNotice(String repaymentNotice) {
        this.repaymentNotice = repaymentNotice;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AllCustomerInfo{");
        sb.append("id=").append(id);
        sb.append(", openId='").append(openId).append('\'');
        sb.append(", identityType='").append(identityType).append('\'');
        sb.append(", identityNo='").append(identityNo).append('\'');
        sb.append(", mobilePhone='").append(mobilePhone).append('\'');
        sb.append(", familyName='").append(familyName).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", sex='").append(sex).append('\'');
        sb.append(", bindingDate='").append(bindingDate).append('\'');
        sb.append(", cardNo='").append(cardNo).append('\'');
        sb.append(", cardType='").append(cardType).append('\'');
        sb.append(", cardLastFourNumber='").append(cardLastFourNumber).append('\'');
        sb.append(", style='").append(style).append('\'');
        sb.append(", isDefault='").append(isDefault).append('\'');
        sb.append(", accId='").append(accId).append('\'');
        sb.append(", notice='").append(notice).append('\'');
        sb.append(", add1='").append(add1).append('\'');
        sb.append(", add2='").append(add2).append('\'');
        sb.append(", add3='").append(add3).append('\'');
        sb.append(", add4='").append(add4).append('\'');
        sb.append(", add5='").append(add5).append('\'');
        sb.append(", billNotice='").append(billNotice).append('\'');
        sb.append(", repaymentNotice='").append(repaymentNotice).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
