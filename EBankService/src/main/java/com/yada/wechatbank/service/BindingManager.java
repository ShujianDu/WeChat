package com.yada.wechatbank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yada.wechatbank.model.CardInfo;
import org.springframework.stereotype.Service;


/**
 * 
 * @author zm
 *
 */
@Service
public class BindingManager {

	/**
	 * 验证是否已经绑定，返回 成功/失败
	 * @param openId
	 * @return
	 */
	public boolean validateIsBinding(String openId){
		return false;
	}

	/**
	 * 判断账户是否锁定
	 * @param openId
	 * @param idNum
	 * @return
	 */
	public boolean isLocked(String openId,String idNum){

		return false;
	}
	
	/**
	 * 计数器缓存加1
	 */
	public void addCountCache(String openId,String idNum){
	}
	
	/**
	 * 身份绑定
	 * @param openId
	 * @param idType
	 * @param idCardNo
	 * @param pwd
	 * @param toUser
	 * @return
	 */
	public String custBinding(String openId, String idType, String idCardNo, String pwd, String toUser){
		return "0";
	}
	
	/**
	 * 获取所有卡号 绑定默认卡
	 * @param openId
	 * @return
	 */
	public List<CardInfo> selectCardNOs(String openId){
		List<CardInfo> ciList = new ArrayList<CardInfo>();
		CardInfo cardInfo = new CardInfo();
		cardInfo.setCardNo("1111111111");
		cardInfo.setCardType("1");
		cardInfo.setCurrency("CNY");
		cardInfo.setCustomerId("1111");
		ciList.add(cardInfo);
		return ciList;
	}
	
	/**
	 * 查询数据库中的客户信息的证件类型有无数据
	 * @param openId
	 * @return
	 */
	public Map<String,String> isExistIdType(String openId){
		Map<String,String> tMap = new HashMap<String, String>();
		tMap.put("isexist", "false");
		tMap.put("idNum", "1111");
		return tMap;
	}
	
	/**
	 * 获取已绑定卡
	 * @param openId
	 * @return
	 */
	public String getDefCardNo(String openId) {
		return "111111111111111";
	}
	
	/**
	 * 绑定默认卡
	 * @param openId
	 * @param cardInfoList
	 * @return
	 */
	public boolean defCardBinding(String openId, List<CardInfo> cardInfoList){
		return true;
	}
	
	/**
	 * 绑定时获取短信验证码
	 * @param identityType
	 * @param identityNo
	 * @param openId
	 * @param mobilNo
	 * @return
	 */
	public String bindingSend(String identityType,String identityNo,String openId,String mobilNo){
		return "true";
	}
	
	/**
	 * 验证绑定的短信验证码
	 * @param openId
	 * @param idNumber
	 * @param code
	 * @return
	 */
	public boolean bindingVerificationCode(String openId,String idNumber,String code) {
		return true;
	}
	
	/**
	 * 补充证件类型是否正确
	 * @param identityNo
	 * @param identityType
	 * @return
	 */
	public boolean isCorrectIdentityType(String identityNo, String identityType){
		return true;
	}
	
	/**
	 * 补充证件类型插入数据库是否成功
	 * @param openId
	 * @param identityNo
	 * @param identityType
	 * @return
	 */
	public boolean fillIdentityType(String openId, String identityNo, String identityType){
		return true;
	}
}
