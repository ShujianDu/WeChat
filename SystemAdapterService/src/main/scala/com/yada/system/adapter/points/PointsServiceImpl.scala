package com.yada.system.adapter.points

import com.yada.sdk.point.protocol.impl._

class PointsServiceImpl extends PointsService{

  /**
    * 查询积分余额
    *
    * @param cardNo 卡号
    * @return PointsBalance
    */
  override def getBalance(cardNo: String): PointsBalance = {
    val getBalance = new P0001_GetBalance(getECIF(cardNo))
    val result = getBalance.send
    PointsBalance(result.respBodyValue("TotalPoint"),result.respBodyValue("AvailPoint"))
  }

  /**
    * 查询积分明细
    *
    * @param cardNo 卡号
    * @return 积分明细集合
    */
  override def getPointsDetails(cardNo: String): List[PointsDetail] = {
    val getDetails = new P0002_GetDetails(getECIF(cardNo))
    getDetails.send.respBodyListValues(m=>{
      PointsDetail(m.getOrElse("RowId",""),m.getOrElse("ParentId",""),m("TotalPoint"),
        m("ProductCode"),m("ProductName"),m.getOrElse("CardNo",""),m.getOrElse("Status",""),
        m("PointuseFlg"))
    })
  }

  /**
    * 查询积分有校期
    *
    * @param cardNo 卡号
    * @return 积分校期集合
    */
  override def getPointsValidates(cardNo: String): List[PointsValidates] = {
    val getProductBalance = new P0013_GetProductBalance(getECIF(cardNo),cardNo)
    getProductBalance.send.respBodyListValues(m=>{
      PointsValidates(m.getOrElse("ProductCode",""),m.getOrElse("ProductName",""),m.getOrElse("CardNo",""),
        m.getOrElse("TotalPoint",""),m.getOrElse("PointExpireDate",""))
    })
  }

  /**
    * 微信授权接口
    *
    * @param cardNo 明文卡号
    * @return (密文卡号，加密验证消息)
    */
  override def verificationCardNo(cardNo: String): (String, String) = {
    val auth = new P0118_WeChatUserAuthentication(cardNo)
    (auth.getReqBodyProps("EncryptCardNo"), auth.send.respBodyValue("Sign"))
  }

  /**
    * 聪明购授权
    *
    * @param cardNo 明文卡号
    * @param mobileNo 明文手机号
    * @return (密文卡号，密文手机号，加密验证消息)
    */
  override def verificationCardNoAndMobileNo(cardNo: String, mobileNo: String): (String, String,String) = {
    val auth = new P0154_WeChatUserAuthenticationForCMG(cardNo,mobileNo)
    (auth.getReqBodyProps("EncryptCardNo"),auth.getReqBodyProps("EncryptMobile"),auth.send.respBodyValue("Sign"))
  }

  private def getECIF(cardNo:String): String ={
    val getECIF = new P0004_GetECIF(cardNo)
    getECIF.send.respBodyValue("EcifNo")
  }
}
