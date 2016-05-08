package com.yada.wx.cbs.subBiz

import java.text.SimpleDateFormat
import java.util.Calendar

import com.yada.wx.cb.data.service.jpa.model.{Command, Customer, MsgCom, NewsCom}
import com.yada.wx.cbs.{CmdRespMessage, ICmdSubBiz}
import play.api.libs.functional.syntax._
import play.api.libs.json._

import scala.collection.convert.WrapAsScala

/**
  * 开启推送
  */
class OpenSpreadBiz() extends ICmdSubBiz {
  protected val dateformat = new SimpleDateFormat("yyyyMMddHHmmss")

  override def subHandle(command: Command, customer: Customer): CmdRespMessage = {
    val msg = Json.toJson(OpenSpreadEvent(dateformat.format(Calendar.getInstance().getTime), customer.openid)).toString()
    kafkaClient.send("wcbDo", "openSpread", msg)
    val findMsgCom: () => MsgCom = () => msgComDao.findOne(command.success_msg_id)
    val findNewsCom: String => List[NewsCom] = msgID => WrapAsScala.asScalaBuffer(newsComDao.findByMsgID(msgID)).toList
    val np: String => String = t => t
    val rp: String => List[String] = t => List(t)
    createRespMsg(findMsgCom, findNewsCom, np, rp)
  }

  // 转换OpenSpreadEvent到json
  implicit val writes: Writes[OpenSpreadEvent] = (
    (__ \ "datetime").write[String] ~ (__ \ "openID").write[String]
    ) (unlift(OpenSpreadEvent.unapply))
}

case class OpenSpreadEvent(datetime: String, openID: String)