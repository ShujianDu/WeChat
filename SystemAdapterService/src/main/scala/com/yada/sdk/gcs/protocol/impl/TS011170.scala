package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.protocol.GCSReq
import com.yada.system.adapter.gcs.GCSBillInstallmentParams

/**
  * 卡户分期项目账单分期（费用试算）
  */
class TS011170(sessionId: String, channelId: String, gcsBillInstallmentParams: GCSBillInstallmentParams) extends GCSReq{

  setPageProps("accountId",gcsBillInstallmentParams.accountId)
  setPageProps("accountNumber",gcsBillInstallmentParams.accountNumber)
  setPageProps("currencyCode",gcsBillInstallmentParams.currencyCode)
  setPageProps("billLowerAmount",gcsBillInstallmentParams.billLowerAmount)
  setPageProps("billActualAmount",gcsBillInstallmentParams.billActualAmount)
  setPageProps("installmentsNumber",gcsBillInstallmentParams.installmentsNumber)
  setPageProps("feeInstallmentsFlag",gcsBillInstallmentParams.feeInstallmentsFlag)
  setPageProps("installmentPlanID","BI01")
  setPageProps("channelId",channelId.head.toString)

  override def transactionID: String = "011170"

  override def requestChannelId: String = channelId

  override def transactionSessionId: String = sessionId

  override def pageKey: String = "RQ011170"

  override def transactionCode: String = "011170"
}
