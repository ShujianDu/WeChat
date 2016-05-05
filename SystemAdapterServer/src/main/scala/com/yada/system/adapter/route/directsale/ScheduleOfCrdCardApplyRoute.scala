package com.yada.system.adapter.route.directsale

import com.yada.system.adapter.directsale.{DirectSaleImpl, ScheduleOfCrdCardApplyParams}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class ScheduleOfCrdCardApplyRoute extends Route {
  override def execute(json: String): String = {
    val params = Json.parse(json).as[ScheduleOfCrdCardApplyParams]
    val rs = DirectSaleImpl.getScheduleOfCrdCardApply(params)
    Json.toJson(rs).toString()
  }
}
