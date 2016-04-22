package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{GCSService, GCSServiceImpl, TempCreditCardReportLostParams}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class TempReportLostRoute(gCSService: GCSService = GCSServiceImpl)  extends Route{
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[TempCreditCardReportLostParams]
    val rs = gCSService.tempCreditCardReportLost(params)
    Json.toJson(rs).toString()
  }
}