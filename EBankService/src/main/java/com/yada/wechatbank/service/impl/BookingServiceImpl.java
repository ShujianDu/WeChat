package com.yada.wechatbank.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.yada.wechatbank.service.BookingService;
import com.yada.wx.db.service.dao.BookingDao;
import com.yada.wx.db.service.dao.NuwOrgDao;
import com.yada.wx.db.service.model.Booking;
import com.yada.wx.db.service.model.NuwOrg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 预约办卡Manager
 * @author zm
 *
 */
@Service
public class BookingServiceImpl implements BookingService{

	@Autowired
	private BookingDao bookingDao;
	@Autowired
	private NuwOrgDao nuwOrgDao;
	/**
	 * 获取客户ID的Sequences，前置处理
	 * @return
	 */
	@Override
	public String getSequences(){

		return bookingDao.getSequences();
	}
	
	/**
	 * 预约办卡新增
	 * @param booking
	 * @return
	 */
	@Override
	public boolean insertBooking(Booking booking) {
	    Booking res =bookingDao.save(booking);
		if(res==null){
			return false;
		}
		return true;
	}
	
	/**
	 * 查询地区
	 * @param pOrgId
	 * @return
	 */
	@Override
	public List<NuwOrg> selectNumOrgList(String pOrgId){
		List<NuwOrg> noList = nuwOrgDao.findByPOrgId(pOrgId);
		return noList;
	}
	
	/**
	 * 判断预约办卡是否已存在
	 * @param clientName
	 * @param mobilePhone
	 * @return
	 */
	@Override
	public String isHaveBooking(String clientName, String mobilePhone){
		List<Booking> bookingList = bookingDao.findByClientNameAndPhone(clientName,mobilePhone);
		if(bookingList==null || bookingList.size()==0){
			return null;
		}
		return "true";
	}
	
}