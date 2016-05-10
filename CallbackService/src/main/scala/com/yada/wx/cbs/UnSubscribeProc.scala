package com.yada.wx.cbs

import java.text.SimpleDateFormat
import java.util.Calendar

import com.yada.weixin.api.message.CallbackMessage
import com.yada.weixin.api.message.CallbackMessage.Names.{EVENT_TYPE, MSG_TYPE}
import com.yada.weixin.cb.server.MessageProc
import com.yada.wx.cb.data.service.jpa.SimpleDataSource
import com.yada.wx.cb.data.service.jpa.dao.CustomerDao
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.Future

/**
  * 取消关注
  */
class UnSubscribeProc extends MessageProc[JsValue, String] {
  private[cbs] var customerDao: CustomerDao = SpringContext.context.getBean(classOf[CustomerDao])
  private[cbs] var kafkaClient: KafkaClient = KafkaClient
  override val filter: (JsValue) => Boolean = jv => {
    (jv \ CallbackMessage.Names.MsgType).as[String] == MSG_TYPE.Event && (jv \ CallbackMessage.Names.Event).as[String] == EVENT_TYPE.UnSubscribe
  }
  override val requestCreator: (JsValue) => JsValue = jv => jv
  override val process: (JsValue) => Future[String] = jv => Future.successful {
    val openID = (jv \ CallbackMessage.Names.FromUserName).as[String]
    customerDao.deleteByOpenid(openID)
    val sdf = new SimpleDateFormat("")
    val event = Json.toJson(Json.obj(
      "datetime" -> sdf.format(Calendar.getInstance.getTime),
      "openID" -> openID)).toString()
    kafkaClient.send("wcbDo", "unBinding", event)
    "success"
  }
  override val responseCreator: (JsValue, String) => Option[JsValue] = (req, resp) => None
}
