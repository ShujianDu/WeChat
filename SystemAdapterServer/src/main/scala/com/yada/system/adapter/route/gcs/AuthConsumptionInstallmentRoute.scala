package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{GCSConsumptionInstallmentParams, GCSServiceImpl}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class AuthConsumptionInstallmentRoute extends Route {
  override def execute(json: String): String = {
    val params = Json.parse(json).as[GCSConsumptionInstallmentParams]
    val rs = GCSServiceImpl.authorizationConsumptionInstallment(params)
    Json.toJson(rs).toString()
  }
}
