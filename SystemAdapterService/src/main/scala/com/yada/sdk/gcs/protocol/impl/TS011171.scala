package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 卡户分期项目账单分期（授权）
  *
  * @param tranSessionID       交易会话标识，用来表示客户访问身份
  * @param reqChannelID        交易请求渠道标识
  * @param accountID           帐户ID(请使用TS011006查询后的“accountID”域值)
  * @param accountNumber       帐户号码(请使用TS011006查询后的“accountNo”域值)
  * @param currencyCode        币种
  * @param billLowerAmount     账单分期下限金额
  * @param billActualAmount    账单分期实际金额
  * @param installmentsNumber  分期期数（“3”、“6”、“9”、“12”、“18”、“24”）
  * @param feeInstallmentsFlag 手续费分期标识（1---标识手续费分期收取，0---标识手续费不分）
  * @param installmentPlanID   分期计划ID
  * @param channelId           渠道标识（目前渠道标识为1位长的字符，存放在channelId的第一位，渠道标识的具体含义待业务提供）
  */
class TS011171(tranSessionID: String,
               reqChannelID: String,
               accountID: String,
               accountNumber: String,
               currencyCode: String,
               billLowerAmount: String,
               billActualAmount: String,
               installmentsNumber: String,
               feeInstallmentsFlag: String,
               channelId: String,
               installmentPlanID: String = "BI01"
               )(implicit gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {

  setPageProps("accountId", accountID)
  setPageProps("accountNumber", accountNumber)
  setPageProps("currencyCode", currencyCode)
  setPageProps("billLowerAmount", billLowerAmount)
  setPageProps("billActualAmount", billActualAmount)
  setPageProps("installmentsNumber", installmentsNumber)
  setPageProps("feeInstallmentsFlag", feeInstallmentsFlag)
  setPageProps("channelId", channelId)
  setPageProps("installmentPlanID", installmentPlanID)

  override def transactionID: String = "011171"

  override def requestChannelId: String = reqChannelID

  override def transactionSessionId: String = tranSessionID

  override def pageKey: String = "RQ011171"

  override def transactionCode: String = "011171"
}
