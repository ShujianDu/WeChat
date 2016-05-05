package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{BillingDetailsSettlementParams, GCSServiceImpl}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class BillingDetailsSettlementRoute extends Route {
  override def execute(json: String): String = {
    val params = Json.parse(json).as[BillingDetailsSettlementParams]
    val rs = GCSServiceImpl.getBillingDetailsSettlement(params)
    Json.toJson(rs).toString()
  }
}
