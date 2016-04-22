package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{CardInfosParams, GCSService, GCSServiceImpl}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json


class CardInfosRoute(gCSService: GCSService = GCSServiceImpl)  extends Route{
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[CardInfosParams]
    val rs = gCSService.getCardInfos(params)
    Json.toJson(rs).toString()
  }
}
