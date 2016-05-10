package com.yada.wechatbank.rmi.interfaces;

import java.util.Map;

public interface IWCBInterface{


	/***
	 * 根据OPENID查询客户信息及卡列表信息
	 *  虽然返回大量域，如后台返回域为空，则会出现取得的属性为NULL，请知晓
	 * @param openId 微信平台定义的openId
	 *
	 * @return 返回Map(id|id,openId|openId,wechatNo|微信号,
	 * identityNo|身份证号,bindingDate|绑定日期,customerId|客户号,identityType|证件类型,
	 * accId|微信账号ID(关注账号),mobilePhone|手机号,sex|性别MALE-男性 FEML-女性,familyName|姓,firstName|名,
	 * add1|备注1,add2|备注2,add3|备注3,add4|备注4,add5|备注5,add6|备注6,notice|动户通知与否-0-不通知 1-通知,
	 *
	 * customerInfoExts|用户卡信息列表:List(Map(id|id,cardNo|卡号,cardType|卡类,
	 * updateDate|更新时间,infoId|关联客户表ID,openId|openId,customerId|客户号,isDefault|是否是默认卡-0 是1 否,
	 * currency|币种 ,style|产品名称,dataDt|数据日期 ,billDate|账单日,repayDate|还款日,curType01|币种1,
	 * crtTermBal01|币种1本期余额,lowestRepayNum01|币种1最小还款金额,curType02|币种2,crtTermBal02|币种2本期余额,
	 * lowestRepayNum02|币种2最小还款金额,reminderTime|提醒时间,reminderFlag|是否已经提醒标识-0 未提醒 1 已经提醒,
	 * notice|是否要提醒标识-0 不提醒1 提醒,cardLastFourNumber|卡号后四位 ,mainFlag|主副卡标识-MAIN-主卡SUPP-辅卡 )))
	 *
	 */
	public Map<String,Object> queryAllCustInfo(String openId);
	/***
	 * 根据OPENID查询客户信息及卡列表信息
	 *  虽然返回大量域，如后台返回域为空，则会出现取得的属性为NULL，请知晓
	 * @param openId 微信平台定义的openId
	 * @param identityNo 用户证件号
	 *
	 * @return 返回Map(id|id,openId|openId,wechatNo|微信号,
	 * identityNo|身份证号,bindingDate|绑定日期,customerId|客户号,identityType|证件类型,
	 * accId|微信账号ID(关注账号),mobilePhone|手机号,sex|性别MALE-男性 FEML-女性,familyName|姓,firstName|名,
	 * add1|备注1,add2|备注2,add3|备注3,add4|备注4,add5|备注5,add6|备注6,notice|动户通知与否-0-不通知 1-通知,
	 *
	 * customerInfoExts|用户卡信息列表:List(Map(id|id,cardNo|卡号,cardType|卡类,
	 * updateDate|更新时间,infoId|关联客户表ID,openId|openId,customerId|客户号,isDefault|是否是默认卡-0 是1 否,
	 * currency|币种 ,style|产品名称,dataDt|数据日期 ,billDate|账单日,repayDate|还款日,curType01|币种1,
	 * crtTermBal01|币种1本期余额,lowestRepayNum01|币种1最小还款金额,curType02|币种2,crtTermBal02|币种2本期余额,
	 * lowestRepayNum02|币种2最小还款金额,reminderTime|提醒时间,reminderFlag|是否已经提醒标识-0 未提醒 1 已经提醒,
	 * notice|是否要提醒标识-0 不提醒1 提醒,cardLastFourNumber|卡号后四位 ,mainFlag|主副卡标识-MAIN-主卡SUPP-辅卡 )))
	 *
	 */
	public Map<String,Object> queryAllCustInfo(String openId,String identityNo);

	/***
	 * 根据OPENID查询客户信息及卡列表信息
	 *  虽然返回大量域，如后台返回域为空，则会出现取得的属性为NULL，请知晓
	 * @param openId 微信平台定义的openId
	 *
	 * @return 返回Map(id|id,openId|openId,wechatNo|微信号,
	 * identityNo|身份证号,bindingDate|绑定日期,customerId|客户号,identityType|证件类型,
	 * accId|微信账号ID(关注账号),mobilePhone|手机号,sex|性别MALE-男性 FEML-女性,familyName|姓,firstName|名,
	 * add1|备注1,add2|备注2,add3|备注3,add4|备注4,add5|备注5,add6|备注6,notice|动户通知与否-0-不通知 1-通知)
	 *
	 */
	public Map<String,String> getCustInfo(String openId);

	/***
	 * 根据OPENID查询客户信息及卡列表信息
	 *  虽然返回大量域，如后续返回域为空，则会出现取得的属性为NULL，请知晓
	 * @param openId 微信平台定义的openId
	 *
	 * @return 返回Map(id|id,openId|openId,wechatNo|微信号,
	 * identityNo|身份证号,bindingDate|绑定日期,customerId|客户号,identityType|证件类型,
	 * accId|微信账号ID(关注账号),mobilePhone|手机号,sex|性别MALE-男性 FEML-女性,familyName|姓,firstName|名,
	 * add1|备注1,add2|备注2,add3|备注3,add4|备注4,add5|备注5,add6|备注6,notice|动户通知与否-0-不通知 1-通知,
	 *
	 * customerInfoExt|用户默认卡信息:(Map(id|id,cardNo|卡号,cardType|卡类,
	 * updateDate|更新时间,infoId|关联客户表ID,openId|openId,customerId|客户号,isDefault|是否是默认卡-0 是1 否,
	 * currency|币种 ,style|产品名称,dataDt|数据日期 ,billDate|账单日,repayDate|还款日,curType01|币种1,
	 * crtTermBal01|币种1本期余额,lowestRepayNum01|币种1最小还款金额,curType02|币种2,crtTermBal02|币种2本期余额,
	 * lowestRepayNum02|币种2最小还款金额,reminderTime|提醒时间,reminderFlag|是否已经提醒标识-0 未提醒 1 已经提醒,
	 * notice|是否要提醒标识-0 不提醒1 提醒,cardLastFourNumber|卡号后四位 ,mainFlag|主副卡标识-MAIN-主卡SUPP-辅卡 ))
	 *
	 */
	public Map<String,Object> getDefCardCustInfo(String openId);



	/***
	 * openId是否绑定
	 * @param openId
	 * @return true绑定，false未绑定
	 */
	public Boolean openIdIsBinding(String openId);
	/**
	 * 获取token
	 * @return 返回accexssToken
	 */
	public String getToken();
	/**
	 * 通过授权code获取用户openId 如获取失败或其他原因，则返回双引空
	 * return openId
	 */
	public String getOpenIdByCode(String code);
	/**
	 * 判断当前用户是否关注此公众号 返回0是未关注，1是已关注
	 *
	 * @return
	 */
	public String checkSubscribe(String openId);
	/**
	 * 获取js签名
	 * @return 	url|url地址 ，jsapi_ticket|jsToken,noncestr|随机字符串,timestamp|时间戳,signature|签名
	 */
	public Map<String,String> getJsSign(String url);
}
