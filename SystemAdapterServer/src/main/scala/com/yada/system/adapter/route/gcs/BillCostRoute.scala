package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{GCSBillInstallmentParams, GCSServiceImpl}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class BillCostRoute extends Route {
  override def execute(json: String): String = {
    val params = Json.parse(json).as[GCSBillInstallmentParams]
    val rs = GCSServiceImpl.getBillCost(params)
    Json.toJson(rs).toString()
  }
}
