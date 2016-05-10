package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.GCSReq
import com.yada.system.adapter.gcs.GCSConsumptionInstallmentParams

/**
  * 卡户分期项目消费分期（费用试算）
  *
  * @param tranSessionID       交易会话标识，用来表示客户访问身份
  * @param reqChannelID        交易请求渠道标识
  * @param accountKeyOne       帐户键值1。请使用TS011007查询后的“accountID”域值
  * @param accountKeyTwo       帐户键值2。请使用TS011007查询后的“accountedID”域值
  * @param currencyCode        币种
  * @param billDateNo          帐期号
  * @param transactionNo       交易序号
  * @param transactionAmount   交易金额
  * @param cardNo              卡号
  * @param accountNoID         账号id。请使用TS011007查询后的“accountNoID”域值
  * @param installmentPeriods  分期付款期数。“3”、“6”、“9”、“12”、“18”、“24”、“36”
  * @param isfeeFlag           是否分期收取手续费。1---标识手续费分期收取，0---标识手续费不分
  * @param channelId           渠道标识。目前渠道标识为1位长的字符，存放在channelId的第一位，渠道标识的具体含义待业务提供
  * @param pageTransactionCode 交易代码。“3110”- POS消费还原
  * @param installmentPlan     分期付款计划。“EP01” - 灵活分期计划1
  * @param merchantID          “0000000”特约商户代码
  * @param mcc                 “5311”- 商户类别
  *
  */
class TS011172(tranSessionID: String,
               reqChannelID: String,
               accountKeyOne: String,
               accountKeyTwo: String,
               currencyCode: String,
               billDateNo: String,
               transactionNo: String,
               transactionAmount: String,
               cardNo: String,
               accountNoID: String,
               installmentPeriods: String,
               isfeeFlag: String,
               channelId: String,
               pageTransactionCode: String = "3110",
               installmentPlan: String = "EP01",
               merchantID: String = "0000000",
               mcc: String = "5311")(implicit gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {

  setPageProps("accountKeyOne", accountKeyOne)
  setPageProps("accountKeyTwo", accountKeyTwo)
  setPageProps("currencyCode", currencyCode)
  setPageProps("transactionCode", pageTransactionCode)
  setPageProps("billDateNo", billDateNo)
  setPageProps("transactionNo", transactionNo)
  setPageProps("transactionAmount", transactionAmount)
  setPageProps("cardNo", cardNo)
  setPageProps("accountNoID", accountNoID)
  setPageProps("mcc", mcc)
  setPageProps("installmentPeriods", installmentPeriods)
  setPageProps("installmentPlan", installmentPlan)
  setPageProps("merchantID", merchantID)
  setPageProps("isfeeFlag", isfeeFlag)
  setPageProps("channelId", channelId)

  override def transactionID: String = "011172"

  override def requestChannelId: String = reqChannelID

  override def transactionSessionId: String = tranSessionID

  override def pageKey: String = "RQ011172"

  override def transactionCode: String = "011172"
}
