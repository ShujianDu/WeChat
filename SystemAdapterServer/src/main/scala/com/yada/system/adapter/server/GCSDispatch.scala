package com.yada.system.adapter.server

import com.yada.system.adapter.gcs.{GCSService, GCSServiceImpl}
import io.netty.handler.codec.http.FullHttpRequest
import play.api.libs.json._
import play.api.libs.functional.syntax._


class GCSDispatch(gCSService: GCSService) {
  def dispatch(json: String,httpRequest: FullHttpRequest): String = {

    val path = httpRequest.getUri.substring(5)

    path match {
      case "balance" =>
        val balance = Json.parse(json).as[Balance]
        val rs = gCSService.getBalance(balance.sessionID,balance.channelID,balance.cardNo,balance.currencyCodes)
//        Json.toJson(rs).toString()
        ""
      case "BillingPeriods" =>
        //TODO
        ""
      case "" =>
        ""
    }
  }
}

object GCSDispatch{
  private val gCSService: GCSService = new GCSServiceImpl
  def apply(): GCSDispatch = new GCSDispatch(gCSService)
}

case class Balance(sessionID: String, channelID: String, cardNo: String, currencyCodes: List[String])

object Balance{
  implicit val blalanceReads: Reads[Balance] = (
    (__ \ "sessionID").read[String] ~ (__ \ "channelID").read[String] ~ (__ \ "cardNo").read[String] ~ (__ \ "currencyCodes").read[List[String]]
    ) (Balance.apply _)

  implicit val blalanceWrites: Writes[Balance] = (
    (__ \ "sessionID").write[String] ~ (__ \ "channelID").write[String] ~ (__ \ "cardNo").write[String] ~ (__ \ "currencyCodes").write[List[String]]
    ) (unlift(Balance.unapply))
}