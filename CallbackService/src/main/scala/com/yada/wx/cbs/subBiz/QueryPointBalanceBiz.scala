package com.yada.wx.cbs.subBiz

import com.yada.wx.cb.data.service.jpa.model.{Command, Customer, MsgCom, NewsCom}
import com.yada.wx.cbs.{CmdRespMessage, HttpClient, ICmdSubBiz}
import play.api.libs.functional.syntax._
import play.api.libs.json.{Reads, _}

import scala.collection.convert.WrapAsScala

/**
  * 查询默认卡积分
  */
class QueryPointBalanceBiz(httpClient: HttpClient = HttpClient) extends ICmdSubBiz {
  private val url = "/points/PointsBalanceRoute"

  override def subHandle(command: Command, customer: Customer): CmdRespMessage = {
    val req = Json.toJson(PointBalanceReq(customer.defCardNo)).toString()
    val resp = httpClient.send(req, url)
    val respJSON = Json.parse(resp)
    if ((respJSON \ "returnCode").as[String] != "00") throw new RuntimeException(respJSON.toString())
    val data = (respJSON \ "data").as[PointBalanceResp]
    val findMsgCom: () => MsgCom = () => msgComDao.findOne(command.success_msg_id)
    val findNewsCom: String => List[NewsCom] = msgID => WrapAsScala.asScalaBuffer(newsComDao.findByMsgID(msgID)).toList
    val normalReplace: String => String = t => t.replace("$_{cardNo}", customer.defCardNo)
    val repeatReplace: String => List[String] = t => {
      List(t.replace("$_{availPoint}", data.availPoint))
    }
    createRespMsg(findMsgCom, findNewsCom, normalReplace, repeatReplace)
  }

  // json 打包
  implicit val writes: Writes[PointBalanceReq] = Writes(req => Json.toJson(JsObject(Seq("cardNo" -> JsString(req.cardNo)))))

  // json 解包
  implicit val reads: Reads[PointBalanceResp] = (
    (__ \ "totalPoint").read[String] ~ (__ \ "availPoint").read[String]
    ) (PointBalanceResp.apply _)
}

/**
  * @param cardNo 卡号
  */
case class PointBalanceReq(cardNo: String)

/**
  * @param totalPoint 积分余额
  * @param availPoint 有效积分余额
  */
case class PointBalanceResp(totalPoint: String, availPoint: String)
