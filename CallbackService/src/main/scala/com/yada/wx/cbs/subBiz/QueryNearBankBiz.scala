package com.yada.wx.cbs.subBiz

import com.yada.wx.cb.data.service.jpa.model.{Command, Customer, MsgCom, NewsCom}
import com.yada.wx.cbs.{CmdRespMessage, ICmdSubBiz}
import play.api.libs.json.Json

import scala.collection.convert.WrapAsScala

/**
  * 查询附近网点
  */
class QueryNearBankBiz() extends ICmdSubBiz {
  override def subHandle(command: Command, customer: Customer): CmdRespMessage = {
    val event = Json.toJson(Json.obj(
      "datetime" -> currentDatetime,
      "openID" -> customer.openid)).toString()
    kafkaClient.send("wcbQuery", "nearBank", event)
    val findMsgCom: () => MsgCom = () => msgComDao.findOne(command.success_msg_id)
    val findNewsCom: String => List[NewsCom] = msgID => WrapAsScala.asScalaBuffer(newsComDao.findByMsgID(msgID)).toList
    val np: String => String = t => t
    val rp: String => List[String] = t => List(t)
    createRespMsg(findMsgCom, findNewsCom, np, rp)
  }
}
