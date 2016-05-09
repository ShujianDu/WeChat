package com.yada.wx.cbs

import com.yada.weixin.api.message.CallbackMessage
import com.yada.weixin.api.message.CallbackMessage.Names.{EVENT_TYPE, MSG_TYPE}
import com.yada.weixin.cb.server.MessageProc
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.Future

/**
  * 关注
  */
class SubscribeProc extends MessageProc[JsValue, CmdRespMessage] {
  private[cbs] var cmdBiz: CmdBiz = new CmdBiz()
  override val filter: (JsValue) => Boolean = jv => {
    (jv \ CallbackMessage.Names.MsgType).toString() == MSG_TYPE.Event && (jv \ CallbackMessage.Names.Event).toString() == EVENT_TYPE.Subscribe
  }
  override val requestCreator: (JsValue) => JsValue = jv => jv
  override val process: (JsValue) => Future[CmdRespMessage] = jv => Future.successful {
    val openID = (jv \ CallbackMessage.Names.FromUserName).toString()
    cmdBiz.handle("WELCOME", openID)
  }
  override val responseCreator: (JsValue, CmdRespMessage) => Option[JsValue] = (req, resp) => Option {
    CmdRespMessage.toJson(req, resp)
  }
}
