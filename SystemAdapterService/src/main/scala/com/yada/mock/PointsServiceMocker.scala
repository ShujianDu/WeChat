package com.yada.mock

import com.yada.system.adapter.points._

class PointsServiceMocker extends PointsService{
  /**
    * 查询积分余额
    *
    * @param cardNoParams 卡号
    * @return PointsBalance
    */
  override def getBalance(cardNoParams: PointsCardNoParams): PointsBalance = {
      PointsMockConfig.pointsBalanceResult(cardNoParams.cardNo)
  }

  /**
    * 查询积分明细
    *
    * @param cardNoParams 卡号
    */
  override def getPointsDetails(cardNoParams: PointsCardNoParams): List[PointsDetail] = {
    PointsMockConfig.pointsDetailResult(cardNoParams.cardNo)
  }

  /**
    * 查询积分有校期
    *
    * @param cardNoParams 卡号
    */
  override def getPointsValidates(cardNoParams: PointsCardNoParams): List[PointsValidates] = {
    PointsMockConfig.pointsValidatesResult(cardNoParams.cardNo)
  }

  /**
    * 微信授权接口
    *
    * @param cardNoParams 明文卡号
    * @return (密文卡号，加密验证消息)
    */
  override def verificationCardNo(cardNoParams: PointsCardNoParams): VerificationCardNoResult = ???

  /**
    * 聪明购授权
    *
    * @param verificationCardNoAndMobileNoParams   VerificationCardNoAndMobileNoParams
    * @return VerificationCardNoAndMobileNoResult
    */
  override def verificationCardNoAndMobileNo(verificationCardNoAndMobileNoParams: VerificationCardNoAndMobileNoParams): VerificationCardNoAndMobileNoResult = ???
}
