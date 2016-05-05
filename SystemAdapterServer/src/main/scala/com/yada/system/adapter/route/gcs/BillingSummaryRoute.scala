package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs._
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class BillingSummaryRoute extends Route {
  override def execute(json: String): String = {
    val billingSummaryParams = Json.parse(json).as[BillingSummaryParams]
    val rs = GCSServiceImpl.getBillingSummary(billingSummaryParams)
    Json.toJson(rs).toString()
  }
}
