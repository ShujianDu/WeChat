package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{GCSServiceImpl, RelieveCreditCardTempReportLostParams}
import com.yada.system.adapter.route.Route
import play.api.libs.json.{JsBoolean, Json}

class RelieveTempReportLostRoute extends Route {
  override def execute(json: String): String = {
    val params = Json.parse(json).as[RelieveCreditCardTempReportLostParams]
    val rs = GCSServiceImpl.relieveCreditCardTempReportLost(params)
    Json.toJson(JsBoolean(rs)).toString()
  }
}
