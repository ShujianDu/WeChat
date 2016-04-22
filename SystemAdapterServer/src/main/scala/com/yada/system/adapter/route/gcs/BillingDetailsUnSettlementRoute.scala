package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{BillingDetailsUnSettlementParams, GCSService, GCSServiceImpl}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json


class BillingDetailsUnSettlementRoute(gCSService: GCSService = GCSServiceImpl) extends Route{
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[BillingDetailsUnSettlementParams]
    val rs = gCSService.getBillingDetailsUnSettlement(params)
    Json.toJson(rs).toString()
  }
}
