package com.yada.wx.cbs

import java.text.SimpleDateFormat
import java.util.Calendar

import com.yada.wx.cb.data.service.CustomerService
import play.api.libs.functional.syntax._
import play.api.libs.json.{Json, _}

class BillService(customerService: CustomerService) {

  protected val BILLINGPERIODS_URL = "/billingPeriods"
  protected val BILLINGSUMMARY_URL = "/billingSummary"

  def getBillingSummary(openID: String): Option[BillingSummary] = {

    //TODO 写法变成Map
    val billingPeriodsJson = customerService.getCustomerInfo(openID) match {
      case Some(info) =>
        Some(HttpClient.send(Json.toJson(BillingPeriodsParams("sessionID", "channelID", info.defCardNo)).toString(), BILLINGPERIODS_URL))
      case None => None
    }

    val billingSummaryJson =  billingPeriodsJson match {
      case Some(v) =>
        val gcsBillingPeriods = Json.parse(v).as[List[BillingPeriodsResult]]
        val temp = gcsBillingPeriods.filter(g => {
          val calendar = Calendar.getInstance()
          calendar.add(Calendar.MONTH, 1)
          g.periodEndDate > new SimpleDateFormat("yyyyMMdd").format(calendar.getTime)
        }
        )
        temp.headOption match {
          case Some(h) =>
            Some(HttpClient.send(Json.toJson(BillingSummary("sessionID", "channelID", h.accountId,h.statementNo)).toString(), BILLINGPERIODS_URL))
          case None => None
        }
      case None => None
    }

    billingSummaryJson match {
      case Some(json) =>
        Some(Json.parse(json).as[BillingSummary])
      case None => None
    }
  }
}

case class BillingPeriodsResult(accountId: String, currencyCode: String, periodStartDate: String, periodEndDate: String, statementNo: String)

object BillingPeriodsResult {
  implicit val billingPeriodsResultWrites: Writes[BillingPeriodsResult] = (
    (__ \ "accountId").write[String] ~ (__ \ "currencyCode").write[String] ~ (__ \ "periodStartDate").write[String] ~ (__ \ "periodEndDate").write[String] ~ (__ \ "statementNo").write[String]
    ) (unlift(BillingPeriodsResult.unapply))
}

case class BillingPeriodsParams(sessionId: String, channelId: String, cardNo: String)

object BillingPeriodsParams {
  implicit val billingPeriodsReads: Reads[BillingPeriodsParams] = (
    (__ \ "sessionID").read[String] ~ (__ \ "channelID").read[String] ~ (__ \ "cardNo").read[String]) (BillingPeriodsParams.apply _)
}

case class BillingSummary(tranSessionID: String, reqChannelID: String, statementNo: String, accountId: String)

object BillingSummary {
  implicit val billingSummaryReads: Reads[BillingSummary] = (
    (__ \ "tranSessionID").read[String] ~ (__ \ "reqChannelID").read[String] ~ (__ \ "statementNo").read[String]
      ~ (__ \ "accountId").read[String]) (BillingSummary.apply _)
}