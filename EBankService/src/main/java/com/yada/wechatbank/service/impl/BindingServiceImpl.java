package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.model.CardHolderInfo;
import com.yada.wechatbank.model.CardInfo;
import com.yada.wechatbank.model.SMSCodeManagement;
import com.yada.wechatbank.service.BindingService;
import com.yada.wx.db.service.dao.CustomerInfoDao;
import com.yada.wx.db.service.model.CustomerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 
 * @author zm
 *
 */
@Service
public class BindingServiceImpl implements BindingService {

	private final String success = "0";// 成功
	private final String noCard = "1";// 查询不到卡号
	private final String pwdFiled = "2";// 验密失败
	@Value("${bcsp.sms.bsnType}")
	private String bsnType;
	@Value("${sms.bindingContent}")
	private String bindingContent;


	@Autowired
	private CustomerInfoDao customerInfoDao;
	/**
	 * 验证是否已经绑定，返回 成功/失败
	 * @param openId openId
	 * @return 用户是否绑定
	 */
	@Override
	public boolean validateIsBinding(String openId){
		CustomerInfo customerInfo = customerInfoDao.findByOpenId(openId);
		if (customerInfo==null){
			return false;
		}
		return true;
	}

	/**
	 * 判断账户是否锁定
	 * @param openId openId
	 * @param idNum 证件号
	 * @return 账户是否锁定
	 */
	@Override
	public boolean isLocked(String openId,String idNum){

		return false;
	}
	
	/**
	 * 计数器缓存加1
	 */
	@Override
	public void addCountCache(String openId,String idNum){
	}
	
	/**
	 * 身份绑定
	 * @param openId openId
	 * @param idType 证件类型
	 * @param idCardNo 证件号
	 * @param pwd 查询密码
	 * @return
	 */
	@Override
	public String custBinding(String openId, String idType, String idCardNo, String pwd){
		List<String> cardList = new ArrayList<String>();
		//调用adpter根据证件号、证件类型查询卡号
		//TODO
		// 通过卡号和密码验证身份
		String cardNo = "";
		if(cardList!=null && cardList.size()!=0){
			cardNo = cardList.get(0);
		}else{
			//统计错误次数
			//TODO
			return noCard;
		}
		//使用卡号密码验密
		//TODO
		// 获取客户基础信息
		CardHolderInfo cardHolderInfo = null;
		//获取客户基础信息
		//TODO
		//删除数据库中已绑定此证件号的记录
		List<String> openIds = customerInfoDao.getOpenIdByidentityNo(idCardNo);
		if (openIds != null && openIds.size() != 0) {
			for (String bindedOpenId : openIds) {
				customerInfoDao.deleteByOpenId(bindedOpenId);
			}
		}
		// 开始插入绑定信息
		CustomerInfo customerInfo = null;
		customerInfo.setOpenId(openId);
		customerInfo.setIdentityType(idType);
		customerInfo.setIdentityNo(idCardNo);
		customerInfo.setMobilePhone(cardHolderInfo.getMobileNo());
		Calendar cal = Calendar.getInstance();
		String dateStr = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
		customerInfo.setBindingDate(dateStr);
		if (validateIsBinding(openId)) {
			customerInfoDao.deleteByOpenId(openId);
		}
		customerInfoDao.save(customerInfo);
		return success;
	}
	
	/**
	 * 获取所有卡号 绑定默认卡
	 * @param identityNo 证件号
	 * @param identityType 证件类型
	 * @return 卡列表
	 */
	@Override
	public List<CardInfo> selectCardNOs(String identityNo,String identityType){
		//TODO 根据证件号和证件类型调用后台查询卡列表
		List<CardInfo> cardInfoList = null;
		return cardInfoList;
	}
	
	/**
	 * 查询数据库中的客户信息的证件类型有无数据
	 * @param openId
	 * @return
	 */
	@Override
	public Map<String,String> isExistIdType(String openId){
		CustomerInfo customerInfo = customerInfoDao.findByOpenId(openId);
		Map<String,String> result = new HashMap<String, String>();
		result.put("isexist", "false");
		if (customerInfo != null) {
			String idType = customerInfo.getIdentityType();
			if (idType != null && idType != "") {
				result.put("isexist", "true");
			}
			String idNum = customerInfo.getIdentityNo();
			result.put("idNum", idNum);
		}
		return result;
	}
	
	/**
	 * 获取已绑定卡
	 * @param openId
	 * @return
	 */
	@Override
	public String getDefCardNo(String openId) {
		CustomerInfo customerInfo = customerInfoDao.findByOpenId(openId);
		String defCardNo = customerInfo.getDefCardNo();
		return defCardNo;
	}
	
	/**
	 * 绑定默认卡
	 * @param openId
	 * @param defCardNO
	 * @return
	 */
	@Override
	public boolean defCardBinding(String openId,String defCardNO){
		CustomerInfo customerInfo = customerInfoDao.findByOpenId(openId);
		if (customerInfo == null) {
			return false;
		}
		customerInfo.setDefCardNo(defCardNO);
		customerInfoDao.save(customerInfo);
		//获取卡列表
		//TODO
		//发事件给动户通知
		//TODO
		return true;
	}
	
	/**
	 * 绑定时获取短信验证码
	 * @param identityType 证件类型
	 * @param identityNo 证件号
	 * @param openId openId
	 * @param mobilNo 手机号
	 * @return String
	 */
	@Override
	public String bindingSend(String identityType,String identityNo,String openId,String mobilNo){
		//TODO 判断是否锁定
		//TODO 走GCS获取手机号
		String mobile=null;

		if (mobile==null) {
			//TODO 证件号手机号操作次数记录限制
			//countSMSCodeCache.put(openId, identityNo);
			return "exception";
		}
		if ("".equals(mobile.trim())) {
			//TODO countSMSCodeCache.put(openId, identityNo);
			return "noMobileNumber";
		}
		if(!mobile.equals(mobilNo)){
			//TODO countSMSCodeCache.put(openId, identityNo);
			return "wrongMobilNo";
		}
		//TODO 证件号手机号输入正确，次数清零
		//countSMSCodeCache.remove(openId);
		//TODO 生成验证码放入内存
		//String msg = SMSCodeManagementService.generateSMSCode(openId, "binding");
		//TODO 替换消息内容
		//String content = bindingContent.replace("#sms.msg#", msg);
		try {
			//TODO 调用发送短信验证码方法
			//return bizProc.sendSMS(bsnType, content, mobile).toString();
		} catch (Exception e) {
			return "false";
		}
		//TODO 删除
		return "";
	}
	
	/**
	 * 验证绑定的短信验证码
	 * @param openId openId
	 * @param idNumber
	 * @param code
	 * @return
	 */
	@Override
	public boolean bindingVerificationCode(String openId,String idNumber,String code) {
		SMSCodeManagement smsCode = new SMSCodeManagement();
		openId = openId + idNumber;
		smsCode.setOpenId(openId);
		smsCode.setSmsCode(code);
		smsCode.setChannelCode("binding");
		smsCode.setCreatTime(Calendar.getInstance().getTimeInMillis());
//		if(SMSCodeManagementService.checkSMSCode(smsCode)==true){
			//验证码输入正确，次数清零
//			countSMSCodeCache.remove(openId);
//			return true;
//		}else{
			//证件号手机号操作次数记录限制
//			countSMSCodeCache.put(openId, idNumber);
//			return false;
//		}
		return true;
	}
	
	/**
	 * 补充证件类型是否正确
	 * @param identityNo 证件号
	 * @param identityType 证件类型
	 * @return boolean
	 */
	@Override
	public boolean isCorrectIdentityType(String identityNo, String identityType){
		CustomerInfo customerInfo = customerInfoDao.findByIdentityTypeAndIdentityNo(identityType,identityNo);
		if (customerInfo == null)
			return false;
		return true;
	}
	
	/**
	 * 补充证件类型插入数据库是否成功
	 * @param openId
	 * @param identityNo
	 * @param identityType
	 * @return
	 */
	@Override
	public boolean fillIdentityType(String openId, String identityNo, String identityType){
		CustomerInfo customerInfo = customerInfoDao.findByOpenIdAndIdentityNo(openId,identityNo);
		customerInfo.setIdentityType(identityType);
		customerInfoDao.updateIdentityTypeByIdentityNo(identityNo,identityType);
		return true;
	}

}
