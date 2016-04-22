package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{AmountLimitParams, GCSService, GCSServiceImpl}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class AmountLimitRoute(gCSService: GCSService = GCSServiceImpl)  extends Route{
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[AmountLimitParams]
    val rs = gCSService.getAmountLimit(params)
    Json.toJson(rs).toString()
  }
}
