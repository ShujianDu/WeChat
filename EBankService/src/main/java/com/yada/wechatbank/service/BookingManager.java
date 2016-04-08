package com.yada.wechatbank.service;

import java.util.ArrayList;
import java.util.List;

import com.yada.wx.db.service.model.Booking;
import com.yada.wx.db.service.model.NuwOrg;
import org.springframework.stereotype.Service;



/**
 * 预约办卡Manager
 * @author zm
 *
 */
@Service
public class BookingManager  {


	/**
	 * 获取客户ID的Sequences，前置处理
	 * @return
	 */
	public String getSequences(){
		return "";
	}
	
	/**
	 * 预约办卡新增
	 * @param booking
	 * @return
	 */
	public boolean insertBooking(Booking booking) {
		return true;
	}
	
	/**
	 * 查询地区
	 * @param pOrgId
	 * @return
	 */
	public List<NuwOrg> selectNumOrgList(String pOrgId){
		List<NuwOrg> noList = new ArrayList<NuwOrg>();
		NuwOrg nuwOrg = new NuwOrg();
		nuwOrg.setOrgId("11111");
		nuwOrg.setOrgName("测试111");
		//nuwOrg.setPorgId("111");
		noList.add(nuwOrg);
		return noList;
	}
	
	/**
	 * 判断预约办卡是否已存在
	 * @param clientName
	 * @param mobilePhone
	 * @return
	 */
	public String isHaveBooking(String clientName, String mobilePhone){
		return "true";
	}
	
}
