package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{GCSService, GCSServiceImpl, MobilePhoneParams}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json


class MobilePhoneRoute(gCSService: GCSService = GCSServiceImpl)  extends Route{
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[MobilePhoneParams]
    val rs = gCSService.getMobilePhone(params)
    Json.toJson(rs).toString()
  }
}
