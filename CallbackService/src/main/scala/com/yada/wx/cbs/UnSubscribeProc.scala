package com.yada.wx.cbs

import java.text.SimpleDateFormat
import java.util.Calendar

import com.yada.weixin.api.message.CallbackMessage.Names.{EVENT_TYPE, MSG_TYPE, _}
import com.yada.weixin.cb.server.MessageProc
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
    (jv \ MsgType).as[String].equalsIgnoreCase(MSG_TYPE.Event) && (jv \ Event).as[String].equalsIgnoreCase(EVENT_TYPE.UnSubscribe)
  }
  override val requestCreator: (JsValue) => JsValue = jv => jv
  override val process: (JsValue) => Future[String] = jv => Future.successful {
    val openID = (jv \ FromUserName).as[String]
    val weiXinID = (jv \ ToUserName).as[String]
    customerDao.deleteByOpenid(openID)
    val event = Json.toJson(Json.obj(
      "datetime" -> String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance.getTime),
      "data" -> Json.obj(
        "openID" -> openID),
      "weiXinID" -> weiXinID).toString()
    ).toString()
    kafkaClient.send("wcbDo", "unBinding", event)
    "success"
  }
  override val responseCreator: (JsValue, String) => Option[JsValue] = (req, resp) => None
}
