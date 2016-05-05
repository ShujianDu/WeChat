package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{GCSServiceImpl, TempCreditCardReportLostParams}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class TempReportLostRoute extends Route {
  override def execute(json: String): String = {
    val params = Json.parse(json).as[TempCreditCardReportLostParams]
    val rs = GCSServiceImpl.tempCreditCardReportLost(params)
    Json.toJson(rs).toString()
  }
}