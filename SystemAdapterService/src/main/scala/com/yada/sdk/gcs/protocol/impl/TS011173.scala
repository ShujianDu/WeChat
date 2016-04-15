package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.protocol.GCSReq
import com.yada.system.adapter.gcs.GCSConsumptionInstallmentParams

/**
  * 卡户分期项目消费分期（授权）
  */
class TS011173(params: GCSConsumptionInstallmentParams) extends GCSReq {

  setPageProps("accountKeyOne", params.accountKeyOne)
  setPageProps("accountKeyTwo", params.accountKeyTwo)
  setPageProps("currencyCode", params.currencyCode)
  setPageProps("billDateNo", params.billDateNo)
  setPageProps("transactionNo", params.transactionNo)
  setPageProps("transactionAmount", params.transactionAmount)
  setPageProps("cardNo", params.cardNo)
  setPageProps("accountNoID", params.accountNoId)
  setPageProps("installmentPeriods", params.installmentPeriods)
  setPageProps("isfeeFlag", params.isfeeFlag)
  setPageProps("channelId", params.channelId.head.toString)
  setPageProps("transactionCode", "3110")
  setPageProps("mcc", "5311")
  setPageProps("installmentPlan", "EP01")
  setPageProps("merchantID", "0000000")

  override def transactionID: String = "011173"

  override def requestChannelId: String = params.channelId

  override def transactionSessionId: String = params.sessionId

  override def pageKey: String = "RQ011173"

  override def transactionCode: String = "011173"
}
