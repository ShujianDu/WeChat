package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{GCSService, GCSServiceImpl, RelieveCreditCardTempReportLostParams}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class RelieveTempReportLostRoute(gCSService: GCSService = GCSServiceImpl)  extends Route{
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[RelieveCreditCardTempReportLostParams]
    val rs = gCSService.relieveCreditCardTempReportLost(params)
    Json.toJson(rs).toString()
  }
}
