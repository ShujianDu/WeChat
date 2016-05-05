package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{GCSServiceImpl, HistoryInstallmentParams}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class HistoryInstallmentRoute extends Route {
  override def execute(json: String): String = {
    val params = Json.parse(json).as[HistoryInstallmentParams]
    val rs = GCSServiceImpl.getHistoryInstallment(params)
    Json.toJson(rs).toString()
  }
}
