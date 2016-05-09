package com.yada.wx.cbs.subBiz

import com.typesafe.config.ConfigFactory
import com.yada.wx.cb.data.service.jpa.dao.{MsgComDao, NewsComDao}
import com.yada.wx.cb.data.service.jpa.model.{Command, Customer, MsgCom, NewsCom}
import com.yada.wx.cbs.{SpringContext, _}
import play.api.libs.functional.syntax._
import play.api.libs.json.{Json, Reads, _}

import scala.collection.convert.WrapAsScala

/**
  * 查询余额
  */
class QueryBalanceBiz(httpClient: HttpClient = HttpClient) extends ICmdSubBiz {
  private val BALANCE_URL = "/gcs/BalanceRoute"
  private val (gcsTranSessionID, gcsReqChannelID) = {
    val config = ConfigFactory.load()
    config.getString("systemAdapter.gcsTranSessionID") -> config.getString("systemAdapter.gcsReqChannelID")
  }

  override def subHandle(command: Command, customer: Customer): CmdRespMessage = {
    val event = Json.obj(
      "datetime" -> currentDatetime,
      "openID" -> customer.openid,
      "cardNo" -> customer.defCardNo).toString()
    kafkaClient.send("wcbQuery", "balance", event)
    // 去后台请求余额信息
    val resp = httpClient.send(Json.toJson(BalanceReq(gcsTranSessionID, gcsReqChannelID, customer.defCardNo)).toString(), BALANCE_URL)
    // 解析响应
    val respJv = Json.parse(resp)
    if ((respJv \ "returnCode").as[String] != "00") throw new RuntimeException(s"error resp: $respJv")
    // 获取余额列表
    val bs = (respJv \ "data").as[List[BalanceResp]]
    val findMsgCom: () => MsgCom = () => msgComDao.findOne(command.success_msg_id)
    val findNewsCom: String => List[NewsCom] = msgID => WrapAsScala.asScalaBuffer(newsComDao.findByMsgID(msgID)).toList
    // 普通模板替换
    val normalReplace: String => String = _.replace("$_{cardNo}", hideCardNo(customer.defCardNo))
    // 重复模板替换
    val repeatReplace: String => List[String] = t => {
      bs.map(b => {
        t.replace("$_{limitCount}", b.wholeCreditLimit)
          .replace("$_{avaliableCount}", b.periodAvailableCreditLimit)
          .replace("$_{preCashAdvanceCreditLimit}", b.preCashAdvanceCreditLimit)
          .replace("$_{currencyCode}", b.currencyCode)
      })
    }
    createRespMsg(findMsgCom, findNewsCom, normalReplace, repeatReplace)
  }

  // json 打包
  implicit val writes: Writes[BalanceReq] = (
    (__ \ "tranSessionID").write[String] ~ (__ \ "reqChannelID").write[String] ~ (__ \ "cardNo").write[String]
    ) (unlift(BalanceReq.unapply))

  // json 解包
  implicit val reads: Reads[BalanceResp] = (
    (__ \ "cardNo").read[String] ~ (__ \ "currencyCode").read[String] ~ (__ \ "wholeCreditLimit").read[String] ~ (__ \ "periodAvailableCreditLimit").read[String] ~ (__ \ "preCashAdvanceCreditLimit").read[String]
    ) (BalanceResp.apply _)
}

case class BalanceReq(tranSessionID: String, reqChannelID: String, cardNo: String)

case class BalanceResp(cardNo: String,
                       currencyCode: String,
                       wholeCreditLimit: String,
                       periodAvailableCreditLimit: String,
                       preCashAdvanceCreditLimit: String)

