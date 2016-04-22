package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs._
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class BillingSummaryRoute(gCSService: GCSService = GCSServiceImpl) extends Route{
  override def execute(json: String): String = {
    val billingSummaryParams = Json.toJson(json).as[BillingSummaryParams]
    val rs = gCSService.getBillingSummary(billingSummaryParams)
    Json.toJson(rs).toString()
  }
}
