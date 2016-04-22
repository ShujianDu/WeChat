package com.yada.system.adapter.bcsp

import play.api.libs.functional.syntax._
import play.api.libs.json.{Writes, _}

/**
  * BCSPService
  */
trait BCSPService {
  /**
    * 短信调用
    *
    * @param sendSMSParams BCSPSendSMSParams
    * @return 返回是否发送成功 true/false
    */
  def sendSMS(sendSMSParams: BCSPSendSMSParams): BCSPBooleanResult
}

/**
  *
  * @param bsnType   BCSP业务类型
  * @param content   内容
  * @param handsetNo 手机号
  * @param sysId     系统ID
  */
case class BCSPSendSMSParams(bsnType: String, content: String, handsetNo: String, sysId: String)

object BCSPSendSMSParams {
  implicit val bcspSendSMSParamsReads: Reads[BCSPSendSMSParams] = (
    (__ \ "bsnType").read[String] ~ (__ \ "content").read[String] ~ (__ \ "handsetNo").read[String] ~ (__ \ "sysId").read[String]
    ) (BCSPSendSMSParams.apply _)

  implicit val bcspSendSMSParamsWrites: Writes[BCSPSendSMSParams] = (
    (__ \ "bsnType").write[String] ~ (__ \ "content").write[String] ~ (__ \ "handsetNo").write[String] ~ (__ \ "sysId").write[String]
    ) (unlift(BCSPSendSMSParams.unapply))
}

case class BCSPBooleanResult(isSuccess: Boolean)

object BCSPBooleanResult {
  implicit val bcspBooleanResultWrites: Writes[BCSPBooleanResult] = Writes(bcspBooleanResult => Json.toJson(JsObject(Map("isSuccess" -> JsBoolean(bcspBooleanResult.isSuccess)))))
}