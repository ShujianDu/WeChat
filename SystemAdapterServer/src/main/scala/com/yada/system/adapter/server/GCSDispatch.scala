package com.yada.system.adapter.server

import com.yada.system.adapter.gcs.{GCSService, GCSServiceImpl}
import io.netty.handler.codec.http.FullHttpRequest
import play.api.libs.json.{Json, _}
import play.api.libs.functional.syntax._


class GCSDispatch(gCSService: GCSService) {
  def dispatch(json: String,httpRequest: FullHttpRequest): String = {

    val path = httpRequest.getUri.substring(5)

    val rs = path match {
      case "balance" =>
        val balance = Json.parse(json).as[Balance]
        gCSService.getBalance(balance.sessionID,balance.channelID,balance.cardNo,balance.currencyCodes)
      case "billingPeriods" =>
        val billingPeriods = Json.parse(json).as[BillingPeriods]
        gCSService.getBillingPeriods(billingPeriods.sessionId,billingPeriods.channelId,billingPeriods.cardNo)
      case "billingSummary" =>
        val billingSummary = Json.parse(json).as[BillingSummary]
        gCSService.getBillingSummary(billingSummary.sessionId,billingSummary.channelId,billingSummary.statementNo,billingSummary.accountId)
    }

    Json.toJson(rs).toString()
  }
}

object GCSDispatch{
  private val gCSService: GCSService = new GCSServiceImpl
  def apply(): GCSDispatch = new GCSDispatch(gCSService)
}

case class Balance(sessionID: String, channelID: String, cardNo: String, currencyCodes: List[String])

object Balance{
  implicit val balanceReads: Reads[Balance] = (
    (__ \ "sessionID").read[String] ~ (__ \ "channelID").read[String] ~ (__ \ "cardNo").read[String] ~ (__ \ "currencyCodes").read[List[String]]
    ) (Balance.apply _)

  implicit val balanceWrites: Writes[Balance] = (
    (__ \ "sessionID").write[String] ~ (__ \ "channelID").write[String] ~ (__ \ "cardNo").write[String] ~ (__ \ "currencyCodes").write[List[String]]
    ) (unlift(Balance.unapply))
}

case class BillingPeriods(sessionId: String, channelId: String, cardNo: String)

object BillingPeriods{
  implicit val billingPeriodsReads: Reads[BillingPeriods] = (
    (__ \ "sessionID").read[String] ~ (__ \ "channelID").read[String] ~ (__ \ "cardNo").read[String]) (BillingPeriods.apply _)

  implicit val billingPeriodsWrites: Writes[BillingPeriods] = (
    (__ \ "sessionID").write[String] ~ (__ \ "channelID").write[String] ~ (__ \ "cardNo").write[String] ) (unlift(BillingPeriods.unapply))
}

case class BillingSummary(sessionId: String, channelId: String, statementNo: String, accountId: String)

object BillingSummary{
  implicit val billingSummaryReads: Reads[BillingSummary] = (
    (__ \ "sessionID").read[String] ~ (__ \ "channelID").read[String] ~ (__ \ "statementNo").read[String] ~ (__ \ "accountId").read[String]) (BillingSummary.apply _)

  implicit val billingSummaryWrites: Writes[BillingSummary] = (
    (__ \ "sessionID").write[String] ~ (__ \ "channelID").write[String] ~ (__ \ "statementNo").write[String] ~ (__ \ "accountId").write[String] ) (unlift(BillingSummary.unapply))
}