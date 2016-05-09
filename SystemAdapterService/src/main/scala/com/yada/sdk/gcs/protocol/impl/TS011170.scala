package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.GCSReq
import com.yada.system.adapter.gcs.GCSBillInstallmentParams

/**
  * 卡户分期项目账单分期（费用试算）
  *
  * @param tranSessionId       交易会话标识，用来表示客户访问身份
  * @param reqChannelId        交易请求渠道标识
  * @param accountId           帐户ID
  * @param accountNumber       帐户号码
  * @param currencyCode        币种
  * @param billLowerAmount     账单分期下限金额
  * @param billActualAmount    账单分期实际金额
  * @param installmentPlanID   分期计划ID。“BI01”- 帐单分期计划1
  * @param installmentsNumber  分期期数。“3”、“6”、“9”、“12”、“18”、“24”
  * @param feeInstallmentsFlag 手续费分期标识。1---标识手续费分期收取，0---标识手续费不分
  * @param channelID           渠道标识。目前渠道标识为1位长的字符，存放在channelId 的第一位，渠道标识的具体含义待业务提供
  */
class TS011170(tranSessionId: String,
               reqChannelId: String,
               accountId: String,
               accountNumber: String,
               currencyCode: String,
               billLowerAmount: String,
               billActualAmount: String,
               installmentsNumber: String,
               feeInstallmentsFlag: String,
               channelID: String,
               installmentPlanID: String = "BI01")(implicit gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {

  setPageProps("accountId", accountId)
  setPageProps("accountNumber", accountNumber)
  setPageProps("currencyCode", currencyCode)
  setPageProps("billLowerAmount", billLowerAmount)
  setPageProps("billActualAmount", billActualAmount)
  setPageProps("installmentsNumber", installmentsNumber)
  setPageProps("feeInstallmentsFlag", feeInstallmentsFlag)
  setPageProps("installmentPlanID", installmentPlanID)
  setPageProps("channelId", channelID)

  override def transactionID: String = "011170"

  override def requestChannelId: String = reqChannelId

  override def transactionSessionId: String = tranSessionId

  override def pageKey: String = "RQ011170"

  override def transactionCode: String = "011170"
}
