package com.yada.system.adapter.route.tgw

import com.yada.system.adapter.route.Route
import com.yada.system.adapter.tgw.{TGWServiceImpl, TGWVerificationPWDParams}
import play.api.libs.json.Json

class VerificationPWDRoute extends Route {
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[TGWVerificationPWDParams]
    val rs = TGWServiceImpl.verificationPWD(params)
    Json.toJson(rs).toString()
  }
}
