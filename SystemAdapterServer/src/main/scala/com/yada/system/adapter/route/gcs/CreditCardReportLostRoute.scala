package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{CreditCardReportLostParams, GCSService, GCSServiceImpl}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json


class CreditCardReportLostRoute(gCSService: GCSService = GCSServiceImpl)  extends Route{
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[CreditCardReportLostParams]
    val rs = gCSService.creditCardReportLost(params)
    Json.toJson(rs).toString()
  }
}
