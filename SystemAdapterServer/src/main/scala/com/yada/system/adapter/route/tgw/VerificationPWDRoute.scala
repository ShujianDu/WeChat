package com.yada.system.adapter.route.tgw

import com.yada.system.adapter.route.Route
import com.yada.system.adapter.tgw.{TGWServiceImpl, TGWVerificationPWDParams}
import play.api.libs.json.{JsBoolean, Json}

class VerificationPWDRoute extends Route {
  override def execute(json: String): String = {
    val params = Json.parse(json).as[TGWVerificationPWDParams]
    val rs = TGWServiceImpl.verificationPWD(params)
    Json.toJson(JsBoolean(rs)).toString()
  }
}
