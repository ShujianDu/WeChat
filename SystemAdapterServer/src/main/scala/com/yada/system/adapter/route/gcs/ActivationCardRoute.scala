package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{CardNoParams, GCSServiceImpl}
import com.yada.system.adapter.route.Route
import play.api.libs.json.{JsBoolean, Json}


class ActivationCardRoute extends Route {
  override def execute(json: String): String = {
    val params = Json.parse(json).as[CardNoParams]
    val rs = GCSServiceImpl.activationCard(params)
    Json.toJson(JsBoolean(rs)).toString()
  }
}
