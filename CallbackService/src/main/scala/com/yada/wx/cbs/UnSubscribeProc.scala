package com.yada.wx.cbs

import com.yada.weixin.api.message.CallbackMessage
import com.yada.weixin.api.message.CallbackMessage.Names.{EVENT_TYPE, MSG_TYPE}
import com.yada.weixin.cb.server.MessageProc
import com.yada.wx.cb.data.service.jpa.dao.CustomerDao
import play.api.libs.json.JsValue

import scala.concurrent.Future

/**
  * 取消关注
  */
class UnSubscribeProc extends MessageProc[JsValue, String] {
  private[cbs] var customerDao: CustomerDao = SpringContext.context.getBean(classOf[CustomerDao])
  override val filter: (JsValue) => Boolean = jv => {
    (jv \ CallbackMessage.Names.MsgType).toString() == MSG_TYPE.Event && (jv \ CallbackMessage.Names.Event).toString() == EVENT_TYPE.UnSubscribe
  }
  override val requestCreator: (JsValue) => JsValue = jv => jv
  override val process: (JsValue) => Future[String] = jv => Future.successful {
    val openID = (jv \ CallbackMessage.Names.FromUserName).toString()
    customerDao.deleteByOpenid(openID)
    "success"
  }
  override val responseCreator: (JsValue, CmdRespMessage) => Option[JsValue] = (req, resp) => None
}
