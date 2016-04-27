package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{CustMobileParams, GCSServiceImpl}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json


class CustMobileRoute extends Route {
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[CustMobileParams]
    val rs = GCSServiceImpl.getCustMobile(params)
    Json.toJson(rs).toString()
  }
}
