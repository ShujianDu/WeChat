package com.yada.wx.cbs.subBiz

import com.yada.wx.cb.data.service.SpringContext
import com.yada.wx.cb.data.service.jpa.dao.{MsgComDao, NewsComDao}
import com.yada.wx.cb.data.service.jpa.model.{Command, Customer}
import com.yada.wx.cbs.{NewsCmdRespMessage, _}
import play.api.libs.functional.syntax._
import play.api.libs.json.{Json, Reads, _}

/**
  * 查询余额
  */
class QueryBalanceBiz(msgComDao: MsgComDao = SpringContext.context.getBean(classOf[MsgComDao]),
                      newsComDao: NewsComDao = SpringContext.context.getBean(classOf[NewsComDao]),
                      httpClient: HttpClient = HttpClient) extends ICmdSubBiz {
  private val BALANCE_URL = "/balance"

  override def subHandle(command: Command, customer: Customer): CmdRespMessage = {
    // 去后台请求余额信息
    val resp = httpClient.send(Json.toJson(BalanceReq("sessionID", "channelID", customer.defCardNo)).toString(), BALANCE_URL)
    // 解析响应
    val respJv = Json.parse(resp)
    // 获取余额列表
    val bs = (respJv \ "data").as[List[BalanceResp]]
    // 查询模板信息
    val msgCom = msgComDao.findOne(command.success_msg_id)
    // 普通模板替换
    val normalReplace: String => String = _.replace("$_{cardNo}", customer.defCardNo)
    // 重复模板替换
    val repeatReplace: String => List[String] = t => {
      bs.map(b => {
        t.replace("$_{limitCount}", b.wholeCreditLimit)
          .replace("$_{avaliableCount}", b.periodAvailableCreditLimit)
          .replace("$_{preCashAdvanceCreditLimit}", b.preCashAdvanceCreditLimit)
          .replace("$_{currencyCode}", b.currencyCode)
      })
    }
    msgCom.msg_type match {
      case "1" => // 文本信息
        val content = TemplateUtil.replace(msgCom.content, normalReplace, repeatReplace)
        TextCmdRespMessage(content)
      case "3" => // 图文信息
        val newsList = newsComDao.findByMsg_id(msgCom.msg_id)
        val itemList = newsList.map(newCom => {
          val title = TemplateUtil.replace(newCom.title, normalReplace, repeatReplace)
          val des = TemplateUtil.replace(newCom.description, normalReplace, repeatReplace)
          NewsMessageItem(title, des, newCom.picurl, newCom.pic_link_url)
        })
        NewsCmdRespMessage(itemList)
    }
  }

  // json 打包
  implicit val balanceReqWrites: Writes[BalanceReq] = (
    (__ \ "tranSessionID").write[String] ~ (__ \ "reqChannelID").write[String] ~ (__ \ "cardNo").write[String]
    ) (unlift(BalanceReq.unapply))

  // json 解包
  implicit val respReads: Reads[BalanceResp] = (
    (__ \ "cardNo").read[String] ~ (__ \ "currencyCode").read[String] ~ (__ \ "wholeCreditLimit").read[String] ~ (__ \ "periodAvailableCreditLimit").read[String] ~ (__ \ "preCashAdvanceCreditLimit").read[String]
    ) (BalanceResp.apply _)
}

case class BalanceReq(tranSessionID: String, reqChannelID: String, cardNo: String)

case class BalanceResp(cardNo: String,
                       currencyCode: String,
                       wholeCreditLimit: String,
                       periodAvailableCreditLimit: String,
                       preCashAdvanceCreditLimit: String)

