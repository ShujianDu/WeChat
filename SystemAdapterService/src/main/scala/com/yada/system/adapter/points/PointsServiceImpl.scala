package com.yada.system.adapter.points

import com.yada.sdk.point.protocol.impl._

class PointsServiceImpl extends PointsService {

  /**
    * 查询积分余额
    *
    * @param cardNoParams 卡号
    * @return PointsBalance
    */
  override def getBalance(cardNoParams: PointsCardNoParams): PointsBalance = {
    val getBalance = new P0001_GetBalance(getECIF(cardNoParams.cardNo))
    val result = getBalance.send
    PointsBalance(result.respBodyValue("TotalPoint"), result.respBodyValue("AvailPoint"))
  }

  /**
    * 查询积分明细
    *
    * @param cardNoParams 卡号
    * @return 积分明细集合
    */
  override def getPointsDetails(cardNoParams: PointsCardNoParams): List[PointsDetail] = {
    val getDetails = new P0002_GetDetails(getECIF(cardNoParams.cardNo))
    getDetails.send.respBodyListValues(m => {
      PointsDetail(m.getOrElse("RowId", ""), m.getOrElse("ParentId", ""), m("TotalPoint"),
        m("ProductCode"), m("ProductName"), m.getOrElse("CardNo", ""), m.getOrElse("Status", ""),
        m("PointuseFlg"))
    })
  }

  /**
    * 查询积分有校期
    *
    * @param cardNoParams 卡号
    * @return 积分校期集合
    */
  override def getPointsValidates(cardNoParams: PointsCardNoParams): List[PointsValidates] = {
    val getProductBalance = new P0013_GetProductBalance(getECIF(cardNoParams.cardNo), cardNoParams.cardNo)
    getProductBalance.send.respBodyListValues(m => {
      PointsValidates(m.getOrElse("ProductCode", ""), m.getOrElse("ProductName", ""), m.getOrElse("CardNo", ""),
        m.getOrElse("TotalPoint", ""), m.getOrElse("PointExpireDate", ""))
    })
  }

  /**
    * 微信授权接口
    *
    * @param cardNoParams 明文卡号
    * @return (密文卡号，加密验证消息)
    */
  override def verificationCardNo(cardNoParams: PointsCardNoParams): VerificationCardNoResult = {
    val auth = new P0118_WeChatUserAuthentication(cardNoParams.cardNo)
    VerificationCardNoResult(auth.getReqBodyProps("EncryptCardNo"), auth.send.respBodyValue("Sign"))
  }

  /**
    * 聪明购授权
    *
    * @param verificationCardNoAndMobileNoParams VerificationCardNoAndMobileNoParams
    * @return (密文卡号，密文手机号，加密验证消息)
    */
  override def verificationCardNoAndMobileNo(verificationCardNoAndMobileNoParams: VerificationCardNoAndMobileNoParams): VerificationCardNoAndMobileNoResult = {

    val auth = new P0154_WeChatUserAuthenticationForCMG(verificationCardNoAndMobileNoParams.cardNo, verificationCardNoAndMobileNoParams.mobileNo)
    VerificationCardNoAndMobileNoResult(auth.getReqBodyProps("EncryptCardNo"), auth.getReqBodyProps("EncryptMobile"), auth.send.respBodyValue("Sign"))
  }

  private def getECIF(cardNo: String): String = {
    val getECIF = new P0004_GetECIF(cardNo)
    getECIF.send.respBodyValue("EcifNo")
  }
}

object PointsServiceImpl extends PointsServiceImpl
