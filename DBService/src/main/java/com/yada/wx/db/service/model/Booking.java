package com.yada.wx.db.service.model;

/**
 * Created by QinQiang on 2016/4/6.
 */
public class Booking {

    private String bookingId; //ID
    private String clientId; //客户ID
    private String clientName; //客户姓名
    private String provId; //省ID
    private String cityId; //市ID
    private String areaId; //区ID
    private String phone; //联系电话
    private String mobilePhone; //手机号
    private String serviceAddr; //联系地址
    private String applyDt; //申请日期
    private String state; //状态（1 已经处理，0 未处理）

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
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
