package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{GCSServiceImpl, MobilePhoneParams}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json


class MobilePhoneRoute extends Route {
  override def execute(json: String): String = {
    val params = Json.parse(json).as[MobilePhoneParams]
    val rs = GCSServiceImpl.getMobilePhone(params)
    Json.toJson(rs).toString()
  }
}
