package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{BillingDetailsUnSettlementParams, GCSServiceImpl}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json


class BillingDetailsUnSettlementRoute extends Route {
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[BillingDetailsUnSettlementParams]
    val rs = GCSServiceImpl.getBillingDetailsUnSettlement(params)
    Json.toJson(rs).toString()
  }
}
