package com.yada.wx.cbs

import com.yada.wx.cb.data.service.CustomerService
import play.api.libs.functional.syntax._
import play.api.libs.json.{Writes, _}

class BalanceService(customerService: CustomerService) {

  protected val BALANCE_URL = "/balance"

  def getBalance(openID:String)={
    customerService.getCustomerInfo(openID) match {
      case Some(customerInfo) =>
        HttpClient.send(Json.toJson(Balance("sessionID","channelID",customerInfo.defCardNo)).toString(),BALANCE_URL)
      case None =>
    }
  }
}

case class Balance(tranSessionID: String, reqChannelID: String, cardNo: String)

object Balance {
  implicit val balanceReads: Reads[Balance] = (
    (__ \ "tranSessionID").read[String] ~ (__ \ "reqChannelID").read[String] ~ (__ \ "cardNo").read[String]
    ) (Balance.apply _)

  implicit val balanceWrites: Writes[Balance] = (
    (__ \ "tranSessionID").write[String] ~ (__ \ "reqChannelID").write[String] ~ (__ \ "cardNo").write[String]
    ) (unlift(Balance.unapply))
}
