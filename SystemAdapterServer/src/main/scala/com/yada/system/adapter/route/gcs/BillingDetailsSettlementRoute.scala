package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{BillingDetailsSettlementParams, GCSService, GCSServiceImpl}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class BillingDetailsSettlementRoute(gCSService: GCSService = GCSServiceImpl) extends Route{
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[BillingDetailsSettlementParams]
    val rs = gCSService.getBillingDetailsSettlement(params)
    Json.toJson(rs).toString()
  }
}
