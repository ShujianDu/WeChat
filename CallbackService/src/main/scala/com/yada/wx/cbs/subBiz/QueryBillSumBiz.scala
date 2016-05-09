package com.yada.wx.cbs.subBiz

import java.text.SimpleDateFormat
import java.util.Calendar

import com.typesafe.config.ConfigFactory
import com.yada.wx.cb.data.service.jpa.model.{Command, Customer, MsgCom, NewsCom}
import com.yada.wx.cbs.{CmdRespMessage, HttpClient, ICmdSubBiz}
import play.api.libs.functional.syntax._
import play.api.libs.json._

import scala.collection.convert.WrapAsScala

/**
  * 查询默认卡账单
  */
class QueryBillSumBiz(httpClient: HttpClient = HttpClient) extends ICmdSubBiz {
  private val billingPeriodsURL = "/gcs/BillingPeriodsRoute"
  private val billingSummaryURL = "/gcs/BillingSummaryRoute"
  private val (gcsTranSessionID, gcsReqChannelID) = {
    val config = ConfigFactory.load()
    config.getString("systemAdapter.gcsTranSessionID") -> config.getString("systemAdapter.gcsReqChannelID")
  }

  override def subHandle(command: Command, customer: Customer): CmdRespMessage = {
    val event = Json.obj(
      "datetime" -> currentDatetime,
      "openID" -> customer.openid,
      "cardNo" -> customer.defCardNo).toString()
    kafkaClient.send("wcbQuery", "billingSummary", event)
    val billingPeriods = Json.parse(httpClient.send(Json.toJson(BillingPeriodReq(gcsTranSessionID, gcsReqChannelID, customer.defCardNo)).toString(), billingPeriodsURL))
    if ((billingPeriods \ "returnCode").as[String] != "00") throw new RuntimeException(billingPeriods.toString())
    val bps = (billingPeriods \ "data").as[List[BillingPeriodResp]]
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.MONTH, -1)
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd")
    val bs = bps.filter(bp => {
      val c = Calendar.getInstance()
      c.setTime(dateFormat.parse(bp.periodEndDate))
      calendar.compareTo(c) < 0
    }).map(bp => {
      val billingSummary = Json.parse(httpClient.send(Json.toJson(BillingSummaryReq(gcsTranSessionID, gcsReqChannelID, bp.statementNo, bp.accountId)).toString(), billingSummaryURL))
      if ((billingPeriods \ "returnCode").as[String] != "00") throw new RuntimeException(billingSummary.toString())
      val bs = (billingSummary \ "data").as[BillingSummaryResp]
      BillingSummaryResp(bs.periodStartDate, bs.periodEndDate, bs.paymentDueDate, bs.closingBalance, bs.currencyCode, bs.minPaymentAmount)
    })
    val normalReplace: String => String = _.replace("$_{cardNo}", hideCardNo(customer.defCardNo))
    val repeatReplace: String => List[String] = t => {
      bs.map(b => {
        t.replace("$_{currencyCode}", b.currencyCode)
          .replace("$_{periodEndDate}", b.periodEndDate)
          .replace("$_{paymentDueDate}", b.paymentDueDate)
          .replace("$_{closingBalance}", b.closingBalance)
          .replace("$_{minPaymentAmount}", b.minPaymentAmount)
      })
    }
    val findMsgCom: () => MsgCom = () => msgComDao.findOne(command.success_msg_id)
    val findNewsCom: String => List[NewsCom] = msgID => WrapAsScala.asScalaBuffer(newsComDao.findByMsgID(msgID)).toList
    createRespMsg(findMsgCom, findNewsCom, normalReplace, repeatReplace)
  }

  implicit val billingPeriodReqWrites: Writes[BillingPeriodReq] = (
    (__ \ "tranSessionID").write[String] ~ (__ \ "reqChannelID").write[String] ~ (__ \ "cardNo").write[String]
    ) (unlift(BillingPeriodReq.unapply))

  implicit val billingPeriodRespReads: Reads[BillingPeriodResp] = (
    (__ \ "accountId").read[String] ~
      (__ \ "currencyCode").read[String] ~
      (__ \ "periodStartDate").read[String] ~
      (__ \ "periodEndDate").read[String] ~
      (__ \ "statementNo").read[String]
    ) (BillingPeriodResp.apply _)

  implicit val billingSummaryReqWrites: Writes[BillingSummaryReq] = (
    (__ \ "tranSessionID").write[String] ~
      (__ \ "reqChannelID").write[String] ~
      (__ \ "statementNo").write[String] ~
      (__ \ "accountId").write[String]
    ) (unlift(BillingSummaryReq.unapply))

  implicit val billingSummaryRespReads: Reads[BillingSummaryResp] = (
    (__ \ "periodStartDate").read[String] ~
      (__ \ "periodEndDate").read[String] ~
      (__ \ "paymentDueDate").read[String] ~
      (__ \ "closingBalance").read[String] ~
      (__ \ "currencyCode").read[String] ~
      (__ \ "minPaymentAmount").read[String]
    ) (BillingSummaryResp.apply _)
}

/**
  * 账单周期请求
  *
  * @param tranSessionID GCStranSessionID
  * @param reqChannelID  GCS渠道号
  * @param cardNo        卡号
  */
case class BillingPeriodReq(tranSessionID: String, reqChannelID: String, cardNo: String)

/**
  * 账单周期响应
  *
  * @param accountId       账户ID
  * @param currencyCode    币种
  * @param periodStartDate 账期开始日期
  * @param periodEndDate   账期结束日期
  * @param statementNo     账期号
  */
case class BillingPeriodResp(accountId: String,
                             currencyCode: String,
                             periodStartDate: String,
                             periodEndDate: String,
                             statementNo: String)

/**
  * 账单摘要请求
  *
  * @param tranSessionID gcsSessionId
  * @param reqChannelID  渠道编号
  * @param statementNo   账期号
  * @param accountId     账户ID
  */
case class BillingSummaryReq(tranSessionID: String,
                             reqChannelID: String,
                             statementNo: String,
                             accountId: String)

/**
  * 账单摘要响应
  *
  * @param periodStartDate  账期开始日期
  * @param periodEndDate    账期结束日期/账单日期
  * @param paymentDueDate   到期还款日
  * @param closingBalance   本期结欠金额(应还款额：)
  * @param currencyCode     币种
  * @param minPaymentAmount 最小还款额
  */
case class BillingSummaryResp(periodStartDate: String,
                              periodEndDate: String,
                              paymentDueDate: String,
                              closingBalance: String,
                              currencyCode: String,
                              minPaymentAmount: String)