package com.yada.wx.cbs

import java.text.SimpleDateFormat
import java.util.Calendar

import com.yada.wx.cb.data.service.CustomerService
import play.api.libs.functional.syntax._
import play.api.libs.json.{Json, _}

class BillService(customerService: CustomerService) {

  protected val BILLINGPERIODS_URL = "/billingPeriods"
  protected val BILLINGSUMMARY_URL = "/billingSummary"

  def getBillingSummary(openID: String): Option[BillingSummaryResult] = {

    //TODO 写法变成Map
    val billingPeriodsJson = customerService.getCustomerInfo(openID) match {
      case Some(info) =>
        Some(HttpClient.send(Json.toJson(BillingPeriodsParams("sessionID", "channelID", info.defCardNo)).toString(), BILLINGPERIODS_URL))
      case None => None
    }

    val billingSummaryJson = billingPeriodsJson match {
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
            Some(HttpClient.send(Json.toJson(BillingSummaryParams("sessionID", "channelID", h.accountId, h.statementNo)).toString(), BILLINGPERIODS_URL))
          case None => None
        }
      case None => None
    }

    billingSummaryJson match {
      case Some(json) =>
        Some(Json.parse(json).as[BillingSummaryResult])
      case None => None
    }
  }
}

case class BillingPeriodsResult(accountId: String, currencyCode: String, periodStartDate: String, periodEndDate: String, statementNo: String)

object BillingPeriodsResult {
  implicit val billingPeriodsResultReads: Reads[BillingPeriodsResult] = (
    (__ \ "accountId").read[String] ~ (__ \ "currencyCode").read[String] ~ (__ \ "periodStartDate").read[String] ~ (__ \ "periodEndDate").read[String] ~ (__ \ "statementNo").read[String]
    ) (BillingPeriodsResult.apply _)
}

case class BillingPeriodsParams(sessionId: String, channelId: String, cardNo: String)

object BillingPeriodsParams {
  implicit val billingPeriodsWrites: Writes[BillingPeriodsParams] = (
    (__ \ "sessionID").write[String] ~ (__ \ "channelID").write[String] ~ (__ \ "cardNo").write[String]) (unlift(BillingPeriodsParams.unapply))
}

case class BillingSummaryParams(tranSessionID: String, reqChannelID: String, statementNo: String, accountId: String)

object BillingSummaryParams {
  implicit val billingSummaryWrites: Writes[BillingSummaryParams] = (
    (__ \ "tranSessionID").write[String] ~ (__ \ "reqChannelID").write[String] ~ (__ \ "statementNo").write[String]
      ~ (__ \ "accountId").write[String]) (unlift(BillingSummaryParams.unapply))
}

case class BillingSummaryResult(periodStartDate: String, periodEndDate: String, paymentDueDate: String, closingBalance: String, currencyCode: String, minPaymentAmount: String)

object BillingSummaryResult {
  implicit val billingSummaryResultReads: Reads[BillingSummaryResult] = (
    (__ \ "periodStartDate").read[String] ~ (__ \ "periodEndDate").read[String] ~ (__ \ "paymentDueDate").read[String] ~ (__ \ "closingBalance").read[String] ~ (__ \ "currencyCode").read[String] ~ (__ \ "minPaymentAmount").read[String]
    ) (BillingSummaryResult.apply _)
}
