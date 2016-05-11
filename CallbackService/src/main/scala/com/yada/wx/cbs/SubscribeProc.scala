package com.yada.wx.cbs

import com.typesafe.scalalogging.LazyLogging
import com.yada.weixin.api.message.CallbackMessage.Names._
import com.yada.weixin.api.message.CallbackMessage.Names.{EVENT_TYPE, MSG_TYPE}
import com.yada.weixin.cb.server.MessageProc
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.Future

/**
  * 关注
  */
class SubscribeProc extends MessageProc[JsValue, CmdRespMessage] with LazyLogging {
  private[cbs] var cmdBiz: CmdBiz = new CmdBiz()
  override val filter: (JsValue) => Boolean = jv => {
    (jv \ MsgType).as[String].equalsIgnoreCase(MSG_TYPE.Event) && (jv \ Event).as[String].equalsIgnoreCase(EVENT_TYPE.Subscribe)
  }
  override val requestCreator: (JsValue) => JsValue = jv => jv
  override val process: (JsValue) => Future[CmdRespMessage] = jv => Future.successful {
    logger.info(s"handle $jv")
    val openID = (jv \ FromUserName).as[String]
    cmdBiz.handle("WELCOME", openID)
  }
  override val responseCreator: (JsValue, CmdRespMessage) => Option[JsValue] = (req, resp) => Option {
    CmdRespMessage.toJson(req, resp)
  }
}
