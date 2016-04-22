package com.yada.system.adapter.route.directsale

import com.yada.system.adapter.directsale.{DirectSale, DirectSaleImpl, ScheduleOfCrdCardApplyParams}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class ScheduleOfCrdCardApplyRoute(directSale: DirectSale = DirectSaleImpl) extends Route{
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[ScheduleOfCrdCardApplyParams]
    val rs=directSale.getScheduleOfCrdCardApply(params)
    Json.toJson(rs).toString()
  }
}
