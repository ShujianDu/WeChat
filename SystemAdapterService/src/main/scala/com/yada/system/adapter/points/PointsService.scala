package com.yada.system.adapter.points

trait PointsService {

  /**
    * 查询积分余额
    *
    * @param cardNo 卡号
    * @return PointsBalance
    */
  def getBalance(cardNo: PointsCardNoParams): PointsBalance

  /**
    * 查询积分有校期
    *
    * @param cardNo 卡号
    * @return 积分校期集合
    */
  def getPointsValidates(cardNo: PointsCardNoParams): List[PointsValidates]

  /**
    * 查询积分明细
    *
    * @param cardNo 卡号
    * @return 积分明细集合
    */
  def getPointsDetails(cardNo: PointsCardNoParams): List[PointsDetail]


  /**
    * 微信授权接口
    *
    * @param cardNo 明文卡号
    * @return (密文卡号，加密验证消息)
    */
  def verificationCardNo(cardNo: PointsCardNoParams): VerificationCardNoResult

  /**
    * 聪明购授权
    *
    * @param verificationCardNoAndMobileNoParams VerificationCardNoAndMobileNoParams
    * @return VerificationCardNoAndMobileNoResult
    */
  def verificationCardNoAndMobileNo(verificationCardNoAndMobileNoParams: VerificationCardNoAndMobileNoParams): VerificationCardNoAndMobileNoResult
}

/**
  * 积分公用的卡号参数
  *
  * @param cardNo 卡号
  */
case class PointsCardNoParams(cardNo: String)

/**
  *
  * @param cardNo   明文卡号
  * @param mobileNo 明文手机号
  */
case class VerificationCardNoAndMobileNoParams(cardNo: String, mobileNo: String)

/**
  *
  * @param encryptCardNo 密文卡号
  * @param encryptMobile 密文手机号
  * @param sign          加密验证消息
  */
case class VerificationCardNoAndMobileNoResult(encryptCardNo: String, encryptMobile: String, sign: String)

/**
  *
  * @param encryptCardNo 密文卡号
  * @param sign          加密验证消息
  */
case class VerificationCardNoResult(encryptCardNo: String, sign: String)

/**
  * @param totalPoint 积分余额
  * @param availPoint 有效积分余额
  */
case class PointsBalance(totalPoint: String, availPoint: String)

/**
  *
  * @param productCode     产品代码
  * @param productName     产品名称
  * @param cardNo          信用卡卡号
  * @param totalPoint      积分数
  * @param pointExpireDate 到期日
  */
case class PointsValidates(productCode: String, productName: String, cardNo: String, totalPoint: String, pointExpireDate: String)

/**
  *
  * @param id          积分ID
  * @param parentId    父ID
  * @param totalPoint  有效积分余额
  * @param productCode 产品代码/账号
  * @param productName 产品名称
  * @param cardNo      信用卡号
  * @param status      账户/卡状态描述
  * @param pointuseFlg 积分账户状态
  */
case class PointsDetail(id: String, parentId: String, totalPoint: String, productCode: String, productName: String, cardNo: String, status: String, pointuseFlg: String)
