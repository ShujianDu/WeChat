package com.yada.wx.cbs

import com.yada.weixin.cb.server.MessageProc
import com.yada.wx.cb.data.service.CMDService
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.Future

/**
  * 命令处理
  */
class CmdProc(cmdService: CMDService) extends MessageProc[JsValue, String] {


  override val filter: (JsValue) => Boolean = jv => {
    cmdService.exist((jv \ "Content").as[String])
  }
  override val requestCreator: (JsValue) => JsValue = jv => jv
  override val process: (JsValue) => Future[String] = jv => Future.successful {
    ""
  }
  override val responseCreator: (JsValue, String) => Option[JsValue] = (req, respContent) => Option {
    Json.obj(
      "ToUserName" -> (req \ "FromUserName").as[String],
      "FromUserName" -> (req \ "ToUserName").as[String],
      "CreateTime" -> System.currentTimeMillis() / 1000,
      "MsgType" -> "text",
      "Content" -> respContent
    )
  }
}
