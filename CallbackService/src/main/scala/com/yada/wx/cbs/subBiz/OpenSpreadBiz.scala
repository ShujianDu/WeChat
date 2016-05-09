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

  override def subHandle(command: Command, customer: Customer): CmdRespMessage = {
    val event = Json.toJson(Json.obj("datetime" -> currentDatetime, "openID" -> customer.openid)).toString()
    kafkaClient.send("wcbDo", "openSpread", event)
    val findMsgCom: () => MsgCom = () => msgComDao.findOne(command.success_msg_id)
    val findNewsCom: String => List[NewsCom] = msgID => WrapAsScala.asScalaBuffer(newsComDao.findByMsgID(msgID)).toList
    val np: String => String = t => t
    val rp: String => List[String] = t => List(t)
    createRespMsg(findMsgCom, findNewsCom, np, rp)
  }
}