package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{CardNoParams, GCSService, GCSServiceImpl}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class BillingPeriodsRoute(gCSService: GCSService = GCSServiceImpl) extends Route{
  override def execute(json: String): String = {
    val cardNoParams = Json.toJson(json).as[CardNoParams]
    val rs = gCSService.getBillingPeriods(cardNoParams)
    Json.toJson(rs).toString()
  }
}
