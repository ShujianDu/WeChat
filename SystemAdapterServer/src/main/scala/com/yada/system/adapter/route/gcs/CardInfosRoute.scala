package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{CardInfosParams, GCSServiceImpl}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json


class CardInfosRoute extends Route {
  override def execute(json: String): String = {
    val params = Json.parse(json).as[CardInfosParams]
    val rs = GCSServiceImpl.getCardInfos(params)
    Json.toJson(rs).toString()
  }
}
