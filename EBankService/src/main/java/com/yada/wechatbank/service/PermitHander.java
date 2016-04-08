package com.yada.wechatbank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yada.wx.db.service.model.CustomerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermitHander  {
//	@Autowired
//	protected CustomerInfoDao customerInfoDao;

	/**
	 * 验证电话银行密码
	 * param:证件号、电话银行密码
	 */
	public boolean hasPermits(String username, String password,String openId) {
		List<String> cardNo =new ArrayList<String>();
		//通过身份证号查询默认卡号
		try {
			//cardNo =customerInfoExtDao.selectDefCardByIdentityNo(username,openId);
			//从后台查询卡列表
		} catch (Exception e) {
			//logger.debug("通过身份证查询客户默认卡信息异常["+e.getMessage()+"]",e);
		}
		if(cardNo == null||cardNo.size()==0){
			//logger.debug("获取默认卡为空");
			return false;
		}
		try {
			//调用后台验密
//			if(!bizProcImpl.verificationPWD(cardNo.get(0), password)){
//				logger.debug("验证密码失败");
//				return false;
//			}
		} 
		catch (Exception e) {
			//logger.debug("验证密码["+e.getMessage()+"]",e);
			return false;
		}
		return true;
	}
	
	/**
	 * 获得客户信息
	 * @param openId
	 * @param identityNo
	 * @return
	 */
	public Map<String, String> getCustInfo(String openId, String identityNo) {
//		CustomerInfo custInfo = customerInfoDao.getCustInfoByOpenIdAndCustId(openId, identityNo);
		Map<String, String> map = new HashMap<String, String>();
//		if(custInfo != null){
//			map.put("identityNo", custInfo.getIdentityNo());
//			map.put("name", custInfo.getFamilyName()+custInfo.getFirstName());
//		}
		return map;
	}
	
	/**
	 * 根据openID获得客户信息
	 * @param openId
	 * @return
	 */
	public Map<String, String> getCustInfo(String openId){
//		CustInfo custInfo = customerInfoDao.selectCustomerInfoByOpenId(openId);
		Map<String, String> map = new HashMap<String, String>();
//		if(custInfo != null){
//			map.put("identityNo", custInfo.getIdentityNo());
//			map.put("name", custInfo.getFamilyName()+custInfo.getFirstName());
//		}
		return map;
	}

}
