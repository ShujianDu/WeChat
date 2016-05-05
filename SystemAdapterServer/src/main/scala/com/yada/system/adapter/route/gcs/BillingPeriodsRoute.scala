package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{CardNoParams, GCSServiceImpl}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class BillingPeriodsRoute extends Route {
  override def execute(json: String): String = {
    val cardNoParams = Json.parse(json).as[CardNoParams]
    val rs = GCSServiceImpl.getBillingPeriods(cardNoParams)
    Json.toJson(rs).toString()
  }
}
