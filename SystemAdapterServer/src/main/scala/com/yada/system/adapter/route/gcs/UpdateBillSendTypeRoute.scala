package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{GCSServiceImpl, UpdateBillSendTypeParams}
import com.yada.system.adapter.route.Route
import play.api.libs.json.{JsBoolean, Json}


class UpdateBillSendTypeRoute extends Route {
  override def execute(json: String): String = {
    val params = Json.parse(json).as[UpdateBillSendTypeParams]
    val rs = GCSServiceImpl.updateBillSendType(params)
    Json.toJson(JsBoolean(rs)).toString()
  }
}
