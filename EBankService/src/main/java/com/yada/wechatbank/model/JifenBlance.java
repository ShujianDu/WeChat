package com.yada.wechatbank.model;

import java.io.Serializable;

/**
 * 积分余额
 * 
 * @author zm
 * 
 */
public class JifenBlance implements Serializable{

	private static final long serialVersionUID = -8003016463991081793L;
	private String totalPoint;// 积分余额
	private String availPoint;// 有效积分余额
	private String custLevel;// 客户层级
	private String marketFlag;// 营销产品代码
	private String branchBank;// 客户所属分行
	private String vipTotalPoint;// VIP总积分余额
	private String vipAvailPoint;// VIP有效积分余额
	private String vipSign;// VIP标识
	public String getTotalPoint() {
		return totalPoint;
	}
	public void setTotalPoint(String totalPoint) {
		this.totalPoint = totalPoint;
	}
	public String getAvailPoint() {
		return availPoint;
	}
	public void setAvailPoint(String availPoint) {
		this.availPoint = availPoint;
	}
	public String getCustLevel() {
		return custLevel;
	}
	public void setCustLevel(String custLevel) {
		this.custLevel = custLevel;
	}
	public String getMarketFlag() {
		return marketFlag;
	}
	public void setMarketFlag(String marketFlag) {
		this.marketFlag = marketFlag;
	}
	public String getBranchBank() {
		return branchBank;
	}
	public void setBranchBank(String branchBank) {
		this.branchBank = branchBank;
	}
	public String getVipTotalPoint() {
		return vipTotalPoint;
	}
	public void setVipTotalPoint(String vipTotalPoint) {
		this.vipTotalPoint = vipTotalPoint;
	}
	public String getVipAvailPoint() {
		return vipAvailPoint;
	}
	public void setVipAvailPoint(String vipAvailPoint) {
		this.vipAvailPoint = vipAvailPoint;
	}
	public String getVipSign() {
		return vipSign;
	}
	public void setVipSign(String vipSign) {
		this.vipSign = vipSign;
	}
	@Override
	public String toString() {
		return "JifengBlance [totalPoint=" + totalPoint + ", availPoint=" + availPoint + ", custLevel=" + custLevel
				+ ", marketFlag=" + marketFlag + ", branchBank=" + branchBank + ", vipTotalPoint=" + vipTotalPoint
				+ ", vipAvailPoint=" + vipAvailPoint + ", vipSign=" + vipSign + "]";
	}

}