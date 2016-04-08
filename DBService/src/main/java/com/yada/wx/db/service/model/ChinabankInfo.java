package com.yada.wx.db.service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by QinQiang on 2016/4/6.
 */
@Entity(name = "T_B_CHINABANK_INFO")
public class ChinabankInfo {

    @Id
    @Column(name = "ID", columnDefinition = "NUMBER", nullable = false)
    private String id; //主键

    @Column(name = "BRANCH_NO", columnDefinition = "NUMBER")
    private String branchNo; //分行号

    @Column(name = "NAME", columnDefinition = "VARCHAR2(255)")
    private String name; //名称

    @Column(name = "ADDR", columnDefinition = "VARCHAR2(255)")
    private String addr; //详细地址

    @Column(name = "AREA1", columnDefinition = "VARCHAR2(100)")
    private String area1; //所在省

    @Column(name = "AREA2", columnDefinition = "VARCHAR2(100)")
    private String area2; //所在市

    @Column(name = "AREA3", columnDefinition = "VARCHAR2(100)")
    private String area3; //所在区

    @Column(name = "LATITUDE", columnDefinition = "VARCHAR2(60)")
    private String latitude; //纬度

    @Column(name = "LONGITUDE", columnDefinition = "VARCHAR2(60)")
    private String longitude; //经度

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getArea1() {
        return area1;
    }

    public void setArea1(String area1) {
        this.area1 = area1;
    }

    public String getArea2() {
        return area2;
    }

    public void setArea2(String area2) {
        this.area2 = area2;
    }

    public String getArea3() {
        return area3;
    }

    public void setArea3(String area3) {
        this.area3 = area3;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
