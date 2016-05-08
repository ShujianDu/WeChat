package com.yada.system.adapter.tgw

import com.yada.system.adapter.points.PointsCardNoParams
import play.api.libs.functional.syntax._
import play.api.libs.json.{Writes, _}

trait TGWService {
  /**
    * 验密
    *
    * @param verificationPWDParams VerificationPWDParams
    * @return 返回是否验证成功    true/false
    */
  def verificationPWD(verificationPWDParams: TGWVerificationPWDParams): Boolean
}

/**
  *
  * @param cardNo 卡号
  * @param pwd    电话银行密码
  */
case class TGWVerificationPWDParams(cardNo: String, pwd: String)

object TGWVerificationPWDParams {
  implicit val tgwVerificationPWDParamsReads: Reads[TGWVerificationPWDParams] = (
    (__ \ "cardNo").read[String] ~ (__ \ "pwd").read[String]
    ) (TGWVerificationPWDParams.apply _)

  implicit val tgwVerificationPWDParamsWrites: Writes[TGWVerificationPWDParams] = (
    (__ \ "cardNo").write[String] ~ (__ \ "pwd").write[String]
    ) (unlift(TGWVerificationPWDParams.unapply))
}

//case class TGWBooleanResult(isSuccess: Boolean)
//
//object TGWBooleanResult {
//  implicit val tgwBooleanResultWrites: Writes[TGWBooleanResult] = Writes(tgwBooleanResult => Json.toJson(JsObject(Map("isSuccess" -> JsBoolean(tgwBooleanResult.isSuccess)).toSeq)))
//}