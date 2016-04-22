package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{GCSBillInstallmentParams, GCSService, GCSServiceImpl}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class BillCostRoute(gCSService: GCSService = GCSServiceImpl)  extends Route{
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[GCSBillInstallmentParams]
    val rs = gCSService.getBillCost(params)
    Json.toJson(rs).toString()
  }
}
