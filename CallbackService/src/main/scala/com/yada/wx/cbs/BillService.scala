package com.yada.wx.cbs

import java.text.SimpleDateFormat
import java.util.Calendar

import com.yada.wx.cb.data.service.CustomerService
import play.api.libs.functional.syntax._
import play.api.libs.json.{Json, _}

class BillService(customerService: CustomerService) {

  protected val BILLINGPERIODS_URL = "/gcs/billingPeriods"
  protected val BILLINGSUMMARY_URL = "/gcs/billingSummary"

  def getBillingSummary(openID: String): Option[BillingSummary] = {

    //TODO 写法变成Map
    val billingPeriodsJson = customerService.getCustomerInfo(openID) match {
      case Some(info) =>
        Some(HttpClient.send(Json.toJson(BillingPeriods("sessionID", "channelID", info.defCardNo)).toString(), BILLINGPERIODS_URL))
      case None => None
    }

    val billingSummaryJson =  billingPeriodsJson match {
      case Some(v) =>
        val gcsBillingPeriods = Json.parse(v).as[List[GCSBillingPeriods]]
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

case class GCSBillingPeriods(accountId: String, currencyCode: String, periodStartDate: String, periodEndDate: String, statementNo: String)

object GCSBillingPeriods {
  implicit val GCSBillingPeriodsReads: Reads[GCSBillingPeriods] = (
    (__ \ "accountId").read[String] ~ (__ \ "currencyCode").read[String] ~ (__ \ "periodStartDate").read[String] ~ (__ \ "periodEndDate").read[String] ~ (__ \ "statementNo").read[String]
    ) (GCSBillingPeriods.apply _)

  implicit val GCSBillingPeriodsWrites: Writes[GCSBillingPeriods] = (
    (__ \ "accountId").write[String] ~ (__ \ "currencyCode").write[String] ~ (__ \ "periodStartDate").write[String] ~ (__ \ "periodEndDate").write[String] ~ (__ \ "statementNo").write[String]
    ) (unlift(GCSBillingPeriods.unapply))
}

case class Balance(sessionID: String, channelID: String, cardNo: String, currencyCodes: List[String])

object Balance {
  implicit val balanceReads: Reads[Balance] = (
    (__ \ "sessionID").read[String] ~ (__ \ "channelID").read[String] ~ (__ \ "cardNo").read[String] ~ (__ \ "currencyCodes").read[List[String]]
    ) (Balance.apply _)

  implicit val balanceWrites: Writes[Balance] = (
    (__ \ "sessionID").write[String] ~ (__ \ "channelID").write[String] ~ (__ \ "cardNo").write[String] ~ (__ \ "currencyCodes").write[List[String]]
    ) (unlift(Balance.unapply))
}

case class BillingPeriods(sessionId: String, channelId: String, cardNo: String)

object BillingPeriods {
  implicit val billingPeriodsReads: Reads[BillingPeriods] = (
    (__ \ "sessionID").read[String] ~ (__ \ "channelID").read[String] ~ (__ \ "cardNo").read[String]) (BillingPeriods.apply _)

  implicit val billingPeriodsWrites: Writes[BillingPeriods] = (
    (__ \ "sessionID").write[String] ~ (__ \ "channelID").write[String] ~ (__ \ "cardNo").write[String]) (unlift(BillingPeriods.unapply))
}

case class BillingSummary(sessionId: String, channelId: String, statementNo: String, accountId: String)

object BillingSummary {
  implicit val billingSummaryReads: Reads[BillingSummary] = (
    (__ \ "sessionID").read[String] ~ (__ \ "channelID").read[String] ~ (__ \ "statementNo").read[String] ~ (__ \ "accountId").read[String]) (BillingSummary.apply _)

  implicit val billingSummaryWrites: Writes[BillingSummary] = (
    (__ \ "sessionID").write[String] ~ (__ \ "channelID").write[String] ~ (__ \ "statementNo").write[String] ~ (__ \ "accountId").write[String]) (unlift(BillingSummary.unapply))
}