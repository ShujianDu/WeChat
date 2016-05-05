package com.yada.system.adapter.route.points

import com.yada.system.adapter.points.{PointsCardNoParams, PointsServiceImpl}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class PointsValidatesRoute extends Route {
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[PointsCardNoParams]
    val rs = PointsServiceImpl.getPointsValidates(params)
    Json.toJson(rs).toString()
  }
}
