package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{BillingDetailsParams, GCSService, GCSServiceImpl}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class BillingDetailsRoute(gCSService: GCSService = GCSServiceImpl) extends Route{
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[BillingDetailsParams]
    val rs = gCSService.getBillingDetails(params)
    Json.toJson(rs).toString()
  }
}
