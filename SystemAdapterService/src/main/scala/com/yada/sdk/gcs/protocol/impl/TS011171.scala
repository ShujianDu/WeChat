package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.protocol.GCSReq
import com.yada.system.adapter.gcs.GCSBillInstallmentParams

/**
  * 卡户分期项目账单分期（授权）
  */
class TS011171(gcsBillInstallmentParams: GCSBillInstallmentParams) extends GCSReq {

  setPageProps("accountId", gcsBillInstallmentParams.accountId)
  setPageProps("accountNumber", gcsBillInstallmentParams.accountNumber)
  setPageProps("currencyCode", gcsBillInstallmentParams.currencyCode)
  setPageProps("billLowerAmount", gcsBillInstallmentParams.billLowerAmount)
  setPageProps("billActualAmount", gcsBillInstallmentParams.billActualAmount)
  setPageProps("installmentsNumber", gcsBillInstallmentParams.installmentsNumber)
  setPageProps("feeInstallmentsFlag", gcsBillInstallmentParams.feeInstallmentsFlag)
  setPageProps("channelId", gcsBillInstallmentParams.channelId.head.toString)
  setPageProps("installmentPlanID", "BI01")

  override def transactionID: String = "011171"

  override def requestChannelId: String = gcsBillInstallmentParams.channelId

  override def transactionSessionId: String = gcsBillInstallmentParams.sessionId

  override def pageKey: String = "RQ011171"

  override def transactionCode: String = "011171"
}
