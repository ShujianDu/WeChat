package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{ConsumptionInstallmentsParams, GCSService, GCSServiceImpl}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class ConsumptionInstallmentsRoute(gCSService: GCSService = GCSServiceImpl)  extends Route{
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[ConsumptionInstallmentsParams]
    val rs = gCSService.getConsumptionInstallments(params)
    Json.toJson(rs).toString()
  }
}
