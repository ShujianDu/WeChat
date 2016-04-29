package com.yada.wx.cbs

import com.yada.weixin.cb.server.MessageProc
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.Future

/**
  * 余额处理
  */
class BalanceProc(balanceService: BalanceService) extends MessageProc[JsValue, String] {
  override val filter: (JsValue) => Boolean = {
    //未实现
    ???
  }
  override val requestCreator: (JsValue) => JsValue = jv => jv
  override val process: (JsValue) => Future[String] = req => {
    Future.successful {
      val openID = (req \ "FromUserName").as[String]
      balanceService.getBalance(openID)
    }
  }
  override val responseCreator: (JsValue, String) => Option[JsValue] = (req, respContent) => Option{
    Json.obj(
      "ToUserName" -> (req \ "FromUserName").as[String],
      "FromUserName" -> (req \ "ToUserName").as[String],
      "CreateTime" -> System.currentTimeMillis() / 1000,
      "MsgType" -> "text",
      "Content" -> respContent
    )
  }
}
