package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{GCSServiceImpl, WbicCardInfoParams}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json


class WbicCardInfoRoute extends Route {
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[WbicCardInfoParams]
    val rs = GCSServiceImpl.getWbicCardInfo(params)
    Json.toJson(rs).toString()
  }
}
