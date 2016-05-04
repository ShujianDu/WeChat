package com.yada.wechatbank.permit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.model.BooleanResp;
import com.yada.wechatbank.kafka.MessageProducer;
import com.yada.wechatbank.kafka.TopicEnum;
import com.yada.wechatbank.model.CardInfo;
import com.yada.wechatbank.util.IdTypeUtil;
import com.yada.wx.db.service.dao.CustomerInfoDao;
import com.yada.wx.db.service.model.CustomerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PermitHander  extends BaseService{
	@Autowired
	protected CustomerInfoDao customerInfoDao;
	@Value("${url.verificationPWD}")
	private String verificationPWD;
	@Autowired
	private MessageProducer messageProducer;
	/**
	 *
	 * @param identityNo 证件号
	 * @param password 查询密码
	 * @param identityType 证件类型
     * @return boolean
     */
	public boolean hasPermits(String identityNo, String password,String identityType) {
		String cardNo ="";
		boolean result;
		//通过证件号和证件类型去后台查询卡号
		List<CardInfo> cardInfoList = selectCardNos(IdTypeUtil.numIdTypeTransformToECode(identityType),identityNo);
		if(cardInfoList!=null && cardInfoList.size()!=0) {
			cardNo = cardInfoList.get(0).getCardNo();
		}
		if(cardNo == null || "".equals(cardNo)){//获取卡号为空
			result =  false;
		}else {
			try {
				Map<String,String> map = new HashMap<>();
				map.put("cardNo",cardNo);
				map.put("pwd",password);
				//调用后台验密
				BooleanResp booleanResp = httpClient.send(verificationPWD,map,BooleanResp.class);
				result = booleanResp.getData();
			}
			catch (Exception e) {
				result = false;
			}
		}
		Map<String,Object> map = new HashMap<>();
		map.put("identityType",identityType);
		map.put("identityNo",identityNo);
		map.put("result",result);
		messageProducer.send(TopicEnum.EBANK_QUERY,"hasPermits",map);
		return result;
	}
}
