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
    val np: String => String = _.replace("$_{billSendTypeDesc}", billSendTypeMap(data))
      .replace("$_{cardNo}", customer.defCardNo)
    // 重复模板替换
    val rp: String => List[String] = t => List(t)
    createRespMsg(findMsgCom, findNewsCom, np, rp)
  }

  // json 打包
  implicit val writes: Writes[BillSendTypeReq] = (
    (__ \ "tranSessionID").write[String] ~ (__ \ "reqChannelID").write[String] ~ (__ \ "cardNo").write[String]
    ) (unlift(BillSendTypeReq.unapply))

  val billSendTypeMap = {
    Map("0" -> "仅发纸质",
      "4" -> "公司领取(当前不可用)",
      "5" -> "分行领取(当前不可用)",
      "6" -> "只寄送汇总对账单",
      "7" -> "只寄送明细对账单",
      "8" -> "纸质、email和短信、推入式",
      "9" -> "纸质、彩信、短信、推入式",
      "A" -> "彩信",
      "B" -> "短信",
      "C" -> "推入式",
      "D" -> "短信、EMAIL、彩信、推入式",
      "E" -> "email和彩信",
      "F" -> "email和短信",
      "G" -> "email和推入式",
      "H" -> "彩信、短信",
      "I" -> "彩信、推入式",
      "J" -> "短信、推入式",
      "K" -> "email和彩信、短信",
      "L" -> "email和彩信、推入式",
      "M" -> "email和短信、推入式",
      "N" -> "彩信、短信、推入式",
      "O" -> "纸质、彩信",
      "P" -> "纸质、短信",
      "Q" -> "纸质、推入式",
      "R" -> "纸质和email、短信、彩信、推入式",
      "S" -> "纸质、email和彩信",
      "T" -> "纸质、email和短信",
      "U" -> "纸质、email和推入式",
      "V" -> "纸质、彩信、短信",
      "3" -> "不发送对账单",
      "W" -> "纸质、彩信、推入式",
      "X" -> "纸质、短信、推入式",
      "Y" -> "纸质、EMAIL、短信、彩信",
      "Z" -> "纸质、EMAIL、彩信、推入式",
      "1" -> "纸质和EMAIL",
      "2" -> "EMAIL"
    )
  }
}

/**
  * @param tranSessionID GCStranSessionID
  * @param reqChannelID  GCS渠道号
  * @param cardNo        卡号
  */
case class BillSendTypeReq(tranSessionID: String, reqChannelID: String, cardNo: String)

