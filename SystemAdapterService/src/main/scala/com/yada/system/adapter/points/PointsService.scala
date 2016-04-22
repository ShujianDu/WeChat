package com.yada.system.adapter.points

import play.api.libs.functional.syntax._
import play.api.libs.json._

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

object PointsCardNoParams {
  implicit val pointsCardNoParamsReads: Reads[PointsCardNoParams] = Reads(jsValue => JsSuccess(PointsCardNoParams(jsValue.\("cardNo").as[String])))
}

/**
  *
  * @param cardNo   明文卡号
  * @param mobileNo 明文手机号
  */
case class VerificationCardNoAndMobileNoParams(cardNo: String, mobileNo: String)

object VerificationCardNoAndMobileNoParams {
    implicit val verificationCardNoAndMobileNoParamsReads: Reads[VerificationCardNoAndMobileNoParams] = (
      (__ \ "cardNo").read[String] ~ (__ \ "mobileNo").read[String]
      ) (VerificationCardNoAndMobileNoParams.apply _)

  implicit val verificationCardNoAndMobileNoParamsWrites: Writes[VerificationCardNoAndMobileNoParams] = (
    (__ \ "cardNo").write[String] ~ (__ \ "mobileNo").write[String]
    ) (unlift(VerificationCardNoAndMobileNoParams.unapply))
}

/**
  *
  * @param encryptCardNo 密文卡号
  * @param encryptMobile 密文手机号
  * @param sign          加密验证消息
  */
case class VerificationCardNoAndMobileNoResult(encryptCardNo: String, encryptMobile: String, sign: String)

object VerificationCardNoAndMobileNoResult {
//  implicit val verificationCardNoAndMobileNoResultReads: Reads[VerificationCardNoAndMobileNoResult] = (
//    (__ \ "encryptCardNo").read[String] ~ (__ \ "encryptMobile").read[String]~ (__ \ "sign").read[String]
//    ) (VerificationCardNoAndMobileNoResult.apply _)

  implicit val verificationCardNoAndMobileNoResultWrites: Writes[VerificationCardNoAndMobileNoResult] = (
    (__ \ "encryptCardNo").write[String] ~ (__ \ "encryptMobile").write[String]~ (__ \ "sign").write[String]
    ) (unlift(VerificationCardNoAndMobileNoResult.unapply))
}

/**
  *
  * @param encryptCardNo 密文卡号
  * @param sign          加密验证消息
  */
case class VerificationCardNoResult(encryptCardNo: String, sign: String)

object VerificationCardNoResult {
  implicit val verificationCardNoResultWrites: Writes[VerificationCardNoResult] = (
    (__ \ "encryptCardNo").write[String] ~ (__ \ "sign").write[String]
    ) (unlift(VerificationCardNoResult.unapply))
}

/**
  * @param totalPoint 积分余额
  * @param availPoint 有效积分余额
  */
case class PointsBalance(totalPoint: String, availPoint: String)

object PointsBalance {
    implicit val pointsBalanceReads: Reads[PointsBalance] = (
      (__ \ "totalPoint").read[String] ~ (__ \ "availPoint").read[String]
      ) (PointsBalance.apply _)

  implicit val pointsBalanceWrites: Writes[PointsBalance] = (
    (__ \ "totalPoint").write[String] ~ (__ \ "availPoint").write[String]
    ) (unlift(PointsBalance.unapply))
}

/**
  *
  * @param productCode     产品代码
  * @param productName     产品名称
  * @param cardNo          信用卡卡号
  * @param totalPoint      积分数
  * @param pointExpireDate 到期日
  */
case class PointsValidates(productCode: String, productName: String, cardNo: String, totalPoint: String, pointExpireDate: String)

object PointsValidates {
  implicit val pointsValidatesReads: Reads[PointsValidates] = (
    (__ \ "productCode").read[String] ~ (__ \ "productName").read[String]~ (__ \ "cardNo").read[String]~ (__ \ "totalPoint").read[String]~ (__ \ "pointExpireDate").read[String]
    ) (PointsValidates.apply _)

  implicit val pointsValidatesWrites: Writes[PointsValidates] = (
    (__ \ "productCode").write[String] ~ (__ \ "productName").write[String]~ (__ \ "cardNo").write[String]~ (__ \ "totalPoint").write[String]~ (__ \ "pointExpireDate").write[String]
    ) (unlift(PointsValidates.unapply))
}

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

object PointsDetail {
  implicit val pointsDetailReads: Reads[PointsDetail] = (
    (__ \ "id").read[String] ~ (__ \ "parentId").read[String]~ (__ \ "totalPoint").read[String]~ (__ \ "productCode").read[String]~ (__ \ "productName").read[String]
      ~ (__ \ "cardNo").read[String]~ (__ \ "status").read[String]~ (__ \ "pointuseFlg").read[String]
    ) (PointsDetail.apply _)

  implicit val pointsDetailWrites: Writes[PointsDetail] = (
    (__ \ "id").write[String] ~ (__ \ "parentId").write[String]~ (__ \ "totalPoint").write[String]~ (__ \ "productCode").write[String]~ (__ \ "productName").write[String]
      ~ (__ \ "cardNo").write[String]~ (__ \ "status").write[String]~ (__ \ "pointuseFlg").write[String]
    ) (unlift(PointsDetail.unapply))
}