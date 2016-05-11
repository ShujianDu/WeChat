package com.yada.wx.cbs

import com.yada.weixin.api.message.CallbackMessage.Names.{MSG_TYPE, _}
import com.yada.weixin.cb.server.MessageProc
import com.yada.wx.cb.data.service.jpa.dao.CommandDao
import com.yada.wx.cbs.subBiz.DirectReturnBiz
import play.api.libs.json.JsValue

import scala.concurrent.Future

class UnKnowProc extends MessageProc[JsValue, CmdRespMessage] {
  private[cbs] var biz = new DirectReturnBiz()
  private[cbs] var commandDao: CommandDao = SpringContext.context.getBean(classOf[CommandDao])

  override val filter: (JsValue) => Boolean = jv => (jv \ MsgType).as[String].equalsIgnoreCase(MSG_TYPE.Text)
  override val requestCreator: (JsValue) => JsValue = jv => jv
  override val process: (JsValue) => Future[CmdRespMessage] = jv => Future.successful {
    val cmd = commandDao.findByCommandValue("UNKNOW")
    biz.subHandle(cmd, null)
  }
  override val responseCreator: (JsValue, CmdRespMessage) => Option[JsValue] = (req, resp) => Option {
    CmdRespMessage.toJson(req, resp)
  }
}
