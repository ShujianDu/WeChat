package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.protocol.GCSReq
import com.yada.system.adapter.gcs.GCSConsumptionInstallmentParams

/**
  * 卡户分期项目消费分期（费用试算）
  */
class TS011172(sessionId: String, channelId: String, params: GCSConsumptionInstallmentParams) extends GCSReq {

  setPageProps("accountKeyOne",params.accountKeyOne)
  setPageProps("accountKeyTwo",params.accountKeyTwo)
  setPageProps("currencyCode",params.currencyCode)
  setPageProps("transactionCode","3110")
  setPageProps("billDateNo",params.billDateNo)
  setPageProps("transactionNo",params.transactionNo)
  setPageProps("transactionAmount",params.transactionAmount)
  setPageProps("cardNo",params.cardNo)
  setPageProps("accountNoID",params.accountNoId)
  setPageProps("mcc","5311")
  setPageProps("installmentPeriods",params.installmentPeriods)
  setPageProps("installmentPlan","EPO1")
  setPageProps("merchantID","0000000")
  setPageProps("isfeeFlag",params.isfeeFlag)
  setPageProps("channelId",channelId.head.toString)

  override def transactionID: String = "011172"

  override def requestChannelId: String = channelId

  override def transactionSessionId: String = sessionId

  override def pageKey: String = "RQ011172"

  override def transactionCode: String = "011172"
}
