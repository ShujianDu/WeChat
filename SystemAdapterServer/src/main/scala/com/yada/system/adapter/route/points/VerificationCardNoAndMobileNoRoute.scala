package com.yada.system.adapter.route.points

import com.yada.system.adapter.points.{PointsService, PointsServiceImpl, VerificationCardNoAndMobileNoParams}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class VerificationCardNoAndMobileNoRoute(pointsService: PointsService= PointsServiceImpl) extends Route{
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[VerificationCardNoAndMobileNoParams]
    val rs = pointsService.verificationCardNoAndMobileNo(params)
    Json.toJson(rs).toString()
  }
}

