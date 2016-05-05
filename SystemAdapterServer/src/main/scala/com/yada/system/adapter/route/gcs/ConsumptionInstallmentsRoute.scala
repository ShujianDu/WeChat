package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{ConsumptionInstallmentsParams, GCSServiceImpl}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class ConsumptionInstallmentsRoute extends Route {
  override def execute(json: String): String = {
    val params = Json.parse(json).as[ConsumptionInstallmentsParams]
    val rs = GCSServiceImpl.getConsumptionInstallments(params)
    Json.toJson(rs).toString()
  }
}
