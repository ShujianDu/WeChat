package com.yada.wechatbank.permit;

import java.util.ArrayList;
import java.util.List;
import com.yada.wx.db.service.dao.CustomerInfoDao;
import com.yada.wx.db.service.model.CustomerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermitHander  {
	@Autowired
	protected CustomerInfoDao customerInfoDao;

	/**
	 *
	 * @param identityNo 证件号
	 * @param password 查询密码
	 * @param identityType 证件类型
     * @return boolean
     */
	public boolean hasPermits(String identityNo, String password,String identityType) {
		String cardNo ="";
		//通过证件号去数据库查询默认卡
		List<CustomerInfo> cusList = customerInfoDao.findByIdentityNo(identityNo);
		if(cusList!=null && cusList.size()!=0){
			cardNo=cusList.get(0).getDefCardNo();
		}else{
			//TODO 通过证件号和证件类型去后台查询卡号
		}
		if(cardNo == null || "".equals(cardNo)){//获取卡号为空
			return false;
		}
		try {
			//TODO 调用后台验密
//			if(!bizProcImpl.verificationPWD(cardNo, password)){
//				return false;
//			}
		} 
		catch (Exception e) {
			return false;
		}
		return true;
	}
}
