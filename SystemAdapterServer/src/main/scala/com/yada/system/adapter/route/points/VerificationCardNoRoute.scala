package com.yada.system.adapter.route.points

import com.yada.system.adapter.points.{PointsCardNoParams, PointsService, PointsServiceImpl}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json


class VerificationCardNoRoute(pointsService: PointsService= PointsServiceImpl) extends Route{
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[PointsCardNoParams]
    val rs = pointsService.verificationCardNo(params)
    Json.toJson(rs).toString()
  }
}
