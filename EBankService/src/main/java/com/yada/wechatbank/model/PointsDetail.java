package com.yada.wechatbank.model;

import com.yada.wechatbank.base.BaseModel;

/**
 * Created by Echo on 2016/4/13.
 */
public class PointsDetail {

    //积分ID
    private String id;
    //父ID
    private String parentId;
    //有效积分余额
    private String totalPoint;
    //产品代码/账号
    private String productCode;
    //产品名称
    private String productName;
    //信用卡号
    private String cardNo;
    //账户/卡状态描述
    private String status;
    //积分账户状态
    private String pointuseFlg;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(String totalPoint) {
        this.totalPoint = totalPoint;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPointuseFlg() {
        return pointuseFlg;
    }

    public void setPointuseFlg(String pointuseFlg) {
        this.pointuseFlg = pointuseFlg;
    }
}
