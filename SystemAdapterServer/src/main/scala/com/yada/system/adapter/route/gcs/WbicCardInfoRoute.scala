package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{GCSService, GCSServiceImpl, WbicCardInfoParams}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json


class WbicCardInfoRoute(gCSService: GCSService = GCSServiceImpl)  extends Route{
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[WbicCardInfoParams]
    val rs = gCSService.getWbicCardInfo(params)
    Json.toJson(rs).toString()
  }
}
