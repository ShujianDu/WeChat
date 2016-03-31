package com.yada.mock

import com.yada.system.adapter.points.{PointsBalance, PointsDetail, PointsValidates, PointsService}

class PointsServiceMocker extends PointsService{
  /**
    * 查询积分余额
    *
    * @param cardNo 卡号
    * @return PointsBalance
    */
  override def getBalance(cardNo: String): PointsBalance = {
      PointsMockConfig.pointsBalanceResult(cardNo)
  }

  /**
    * 查询积分明细
    *
    * @param cardNo 卡号
    */
  override def getPointsDetails(cardNo: String): List[PointsDetail] = {
    PointsMockConfig.pointsDetailResult(cardNo)
  }

  /**
    * 查询积分有校期
    *
    * @param cardNo 卡号
    */
  override def getPointsValidates(cardNo: String): List[PointsValidates] = {
    PointsMockConfig.pointsValidatesResult(cardNo)
  }

  /**
    * 微信授权接口
    *
    * @param cardNo 明文卡号
    * @return (密文卡号，加密验证消息)
    */
  override def verificationCardNo(cardNo: String): (String, String) = ???

  /**
    * 聪明购授权
    *
    * @param cardNo   明文卡号
    * @param mobileNo 明文手机号
    * @return (密文卡号，密文手机号，加密验证消息)
    */
  override def verificationCardNoAndMobileNo(cardNo: String, mobileNo: String): (String, String, String) = ???
}
