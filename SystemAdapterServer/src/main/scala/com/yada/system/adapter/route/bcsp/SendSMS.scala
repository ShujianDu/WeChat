package com.yada.system.adapter.route.bcsp

import com.yada.system.adapter.bcsp.{BCSPSendSMSParams, BCSPService, BCSPServiceImpl}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class SendSMS extends Route{
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[BCSPSendSMSParams]
    val rs = BCSPServiceImpl.sendSMS(params)
    Json.toJson(rs).toString()
  }
}
