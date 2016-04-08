package com.yada.wx.db.service.model;

import javax.persistence.*;

/**
 * Created by QinQiang on 2016/4/6.
 * 预约办卡
 */
@Entity(name = "T_B_BOOKING")
public class Booking {

    @Id
    @GeneratedValue(generator="BOOKING_SEQ")
    @SequenceGenerator(name="BOOKING_SEQ", sequenceName="SEQ_T_B_BOOKING")
    @Column(name = "BOOKING_ID", columnDefinition = "CHAR(32)", nullable = false)
    private Long bookingId; //ID

    @Column(name = "CLIENT_ID", columnDefinition = "VARCHAR2(20)", nullable = false)
    private String clientId; //客户ID

    @Column(name = "CLIENT_NAME", columnDefinition = "VARCHAR2(50)", nullable = false)
    private String clientName; //客户姓名

    @Column(name = "PROV_ID", columnDefinition = "VARCHAR2(20)", nullable = false)
    private String provId; //省ID

    @Column(name = "CITY_ID", columnDefinition = "VARCHAR2(20)", nullable = false)
    private String cityId; //市ID

    @Column(name = "AREA_ID", columnDefinition = "VARCHAR2(20)", nullable = false)
    private String areaId; //区ID

    @Column(name = "PHONE", columnDefinition = "VARCHAR2(20)")
    private String phone; //联系电话

    @Column(name = "MOBILE_PHONE", columnDefinition = "VARCHAR2(20)", nullable = false)
    private String mobilePhone; //手机号

    @Column(name = "SERVICE_ADDR", columnDefinition = "VARCHAR2(500)", nullable = false)
    private String serviceAddr; //联系地址

    @Column(name = "APPLY_DT", columnDefinition = "CHAR(8)", nullable = false)
    private String applyDt; //申请日期

    @Column(name = "STATE", columnDefinition = "CHAR(1)")
    private String state; //状态（1 已经处理，0 未处理）

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getProvId() {
        return provId;
    }

    public void setProvId(String provId) {
        this.provId = provId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getServiceAddr() {
        return serviceAddr;
    }

    public void setServiceAddr(String serviceAddr) {
        this.serviceAddr = serviceAddr;
    }

    public String getApplyDt() {
        return applyDt;
    }

    public void setApplyDt(String applyDt) {
        this.applyDt = applyDt;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
