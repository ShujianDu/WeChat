package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{CardNoParams, GCSServiceImpl}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class WbicCardSendSMSRoute extends Route {
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[CardNoParams]
    val rs = GCSServiceImpl.wbicCardSendSMS(params)
    Json.toJson(rs).toString()
  }
}
