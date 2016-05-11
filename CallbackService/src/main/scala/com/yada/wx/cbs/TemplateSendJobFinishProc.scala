package com.yada.wx.cbs

import com.yada.weixin.api.message.CallbackMessage.Names._
import com.yada.weixin.api.message.CallbackMessage.Names.MSG_TYPE
import com.yada.weixin.cb.server.MessageProc
import play.api.libs.json.JsValue

import scala.concurrent.Future

class TemplateSendJobFinishProc extends MessageProc[JsValue, String] {
  override val filter: (JsValue) => Boolean = jv => {
    (jv \ MsgType).as[String].equalsIgnoreCase(MSG_TYPE.Event) && (jv \ Event).as[String].equalsIgnoreCase(MSG_TYPE.TemplateSendJobFinish)
  }
  override val requestCreator: (JsValue) => JsValue = jv => jv
  override val process: (JsValue) => Future[String] = jv => Future.successful("")
  override val responseCreator: (JsValue, String) => Option[JsValue] = (req, resp) => None
}
