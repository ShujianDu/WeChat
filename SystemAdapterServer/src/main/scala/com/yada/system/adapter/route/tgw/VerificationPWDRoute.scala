package com.yada.system.adapter.route.tgw

import com.yada.system.adapter.route.Route
import com.yada.system.adapter.tgw.{TGWService, TGWServiceImpl, TGWVerificationPWDParams}
import play.api.libs.json.Json

class VerificationPWDRoute(tgwService: TGWService = TGWServiceImpl) extends Route{
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[TGWVerificationPWDParams]
    val rs = tgwService.verificationPWD(params)
    Json.toJson(rs).toString()
  }
}
