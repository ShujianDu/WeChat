package com.yada.wx.cbs.subBiz

import com.yada.wx.cb.data.service.jpa.model.{Command, Customer, MsgCom, NewsCom}
import com.yada.wx.cbs.{CmdReqMessage, CmdRespMessage, ICmdSubBiz}
import play.api.libs.functional.syntax._
import play.api.libs.json._

import scala.collection.convert.WrapAsScala

/**
  * 关闭推送
  */
class CloseSpreadBiz() extends ICmdSubBiz {

  override def subHandle(command: Command, customer: Customer, cmdReqMessage: CmdReqMessage): CmdRespMessage = {
    kafkaClient.send("wcbDo", "closeSpread", Json.obj(
      "datetime" -> currentDatetime,
      "data" -> Json.obj(
        "openID" -> customer.openid),
      "weiXinID" -> cmdReqMessage.weiXinID,
      "cmd" -> command.commandValue
    ).toString())
    val findMsgCom: () => MsgCom = () => msgComDao.findOne(command.success_msg_id)
    val findNewsCom: String => List[NewsCom] = msgID => WrapAsScala.asScalaBuffer(newsComDao.findByMsgID(msgID)).toList
    val np: String => String = t => t
    val rp: String => List[String] = t => List(t)
    createRespMsg(findMsgCom, findNewsCom, np, rp)
  }

  // 转换OpenSpreadEvent到json
  implicit val writes: Writes[CloseSpreadEvent] = (
    (__ \ "datetime").write[String] ~ (__ \ "openID").write[String]
    ) (unlift(CloseSpreadEvent.unapply))
}

/**
  * 关闭推送事件
  *
  * @param datetime 日期时间
  * @param openID   用户唯一标识
  */
case class CloseSpreadEvent(datetime: String, openID: String)