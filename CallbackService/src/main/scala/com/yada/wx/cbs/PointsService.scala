package com.yada.wx.cbs

import com.yada.wx.cb.data.service.CustomerService
import play.api.libs.json._

class PointsService(customerService: CustomerService) {

  protected val POINTSBALANCE_URL = "/pointsBalance"

  def getPoints(openID: String): String = {
    customerService.getCustomerInfo(openID) match {
      case Some(customerInfo) =>
        HttpClient.send(Json.toJson(PointsParams(customerInfo.defCardNo)).toString, POINTSBALANCE_URL)
      case None => ""
    }
  }
}

case class PointsParams(cardNo: String)

object PointsParams {
  implicit val pointsCardNoParamsWrites: Writes[PointsParams] = Writes(params => Json.toJson(JsObject(Seq("cardNo" -> JsString(params.cardNo)))))
}