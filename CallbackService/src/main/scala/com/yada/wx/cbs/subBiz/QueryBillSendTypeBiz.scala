package com.yada.wx.cbs.subBiz

import com.typesafe.config.ConfigFactory
import com.yada.wx.cb.data.service.SpringContext
import com.yada.wx.cb.data.service.jpa.dao.{MsgComDao, NewsComDao}
import com.yada.wx.cb.data.service.jpa.model.{Command, Customer, MsgCom, NewsCom}
import com.yada.wx.cbs.{CmdRespMessage, HttpClient, ICmdSubBiz}
import play.api.libs.functional.syntax._
import play.api.libs.json.{Reads, _}

import scala.collection.convert.WrapAsScala

/**
  * 查询账单寄送方式
  */
class QueryBillSendTypeBiz(httpClient: HttpClient = HttpClient) extends ICmdSubBiz {
  private val url = "/gcs/BillSendTypeRoute"
  private val (gcsTranSessionID, gcsReqChannelID) = {
    val config = ConfigFactory.load()
    config.getString("systemAdapter.gcsTranSessionID") -> config.getString("systemAdapter.gcsReqChannelID")
  }

  override def subHandle(command: Command, customer: Customer): CmdRespMessage = {
    val req = Json.toJson(BillSendTypeReq(gcsTranSessionID, gcsReqChannelID, customer.defCardNo)).toString
    val resp = httpClient.send(req, url)
    val respJson = Json.parse(resp)
    if ((respJson \ "returnCode").as[String] != "00") throw new RuntimeException(respJson.toString())
    val data = (respJson \ "data").as[String]
    val findMsgCom: () => MsgCom = () => msgComDao.findOne(command.success_msg_id)
    val findNewsCom: String => List[NewsCom] = msgID => WrapAsScala.asScalaBuffer(newsComDao.findByMsgID(msgID)).toList
    // 普通模板替换
    val np: String => String = _.replace("$_{billSendTypeDesc}", data)
      .replace("$_{cardNo}", customer.defCardNo)
    // 重复模板替换
    val rp: String => List[String] = t => List(t)
    createRespMsg(findMsgCom, findNewsCom, np, rp)
  }

  // json 打包
  implicit val writes: Writes[BillSendTypeReq] = (
    (__ \ "tranSessionID").write[String] ~ (__ \ "reqChannelID").write[String] ~ (__ \ "cardNo").write[String]
    ) (unlift(BillSendTypeReq.unapply))

}

/**
  * @param tranSessionID GCStranSessionID
  * @param reqChannelID  GCS渠道号
  * @param cardNo        卡号
  */
case class BillSendTypeReq(tranSessionID: String, reqChannelID: String, cardNo: String)

