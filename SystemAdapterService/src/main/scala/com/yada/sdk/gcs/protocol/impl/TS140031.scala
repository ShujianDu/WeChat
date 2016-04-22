package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 信用卡额度调整状态查询
  *
  * @param tranSessionID 交易会话标识，用来表示客户访问身份
  * @param reqChannelID  交易请求渠道标识
  * @param eosIDType     证件类型。如果卡号为空此域是必填项
  * @param idNumber      证件号码。如果卡号为空此域是必填项
  * @param cardNo        卡号。如果证件类型证件号码为空此域是必填项
  * @param eosId         工单ID。
  *                      字段名称：发起渠道/系统+任务类型+工单发起日期+序号<br>
  *                      编码规则：NN+NN+YYYYMMDD+NNNNN<br>
  *                      示例：02022015011900001<br>
  *                      发起渠道/系统：01总行NUW；02总行CSR；03总行CRM；04分行落地终端；05网银；06微信；07财富OCRM<br>
  *                      任务类型：01申请评分；02临增；03长增；04事件式增额；05批量增额；06风险预警
  * @param startNum      开始条数。该值上送“1”
  * @param totalNum      总共需要的条数。上送20
  */
class TS140031(tranSessionID: String,
               reqChannelID: String,
               eosIDType: Option[String],
               idNumber: Option[String],
               cardNo: Option[String],
               eosId: Option[String],
               startNum: String = "1",
               totalNum: String = "20")(implicit gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {

  eosId.foreach(setPageProps("eosId", _))
  cardNo.foreach(setPageProps("cardNo", _))
  eosIDType.foreach(setPageProps("eosIdtype", _))
  idNumber.foreach(setPageProps("idNumber", _))
  setPageProps("startNum", startNum)
  setPageProps("totalNum", totalNum)

  override def transactionID: String = "140031"

  override def requestChannelId: String = reqChannelID

  override def transactionSessionId: String = tranSessionID

  override def pageKey: String = "RQ140031"

  override def transactionCode: String = "140031"
}
