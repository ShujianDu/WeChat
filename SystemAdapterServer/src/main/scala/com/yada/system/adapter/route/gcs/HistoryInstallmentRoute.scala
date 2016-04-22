package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{GCSService, GCSServiceImpl, HistoryInstallmentParams}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class HistoryInstallmentRoute(gCSService: GCSService = GCSServiceImpl)  extends Route{
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[HistoryInstallmentParams]
    val rs = gCSService.getHistoryInstallment(params)
    Json.toJson(rs).toString()
  }
}
