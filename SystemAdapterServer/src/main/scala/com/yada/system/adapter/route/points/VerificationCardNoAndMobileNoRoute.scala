package com.yada.system.adapter.route.points

import com.yada.system.adapter.points.{PointsServiceImpl, VerificationCardNoAndMobileNoParams}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class VerificationCardNoAndMobileNoRoute extends Route {
  override def execute(json: String): String = {
    val params = Json.parse(json).as[VerificationCardNoAndMobileNoParams]
    val rs = PointsServiceImpl.verificationCardNoAndMobileNo(params)
    Json.toJson(rs).toString()
  }
}

