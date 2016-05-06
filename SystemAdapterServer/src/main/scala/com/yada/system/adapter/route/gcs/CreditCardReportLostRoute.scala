package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{CreditCardReportLostParams, GCSServiceImpl}
import com.yada.system.adapter.route.Route
import play.api.libs.json.{JsBoolean, Json}


class CreditCardReportLostRoute extends Route {
  override def execute(json: String): String = {
    val params = Json.parse(json).as[CreditCardReportLostParams]
    val rs = GCSServiceImpl.creditCardReportLost(params)
    Json.toJson(JsBoolean(rs)).toString()
  }
}
