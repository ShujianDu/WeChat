package com.yada.sdk.gcs.protocol.impl

import java.text.SimpleDateFormat
import java.util.Calendar

import com.yada.sdk.gcs.protocol.GCSReq
import com.yada.system.adapter.gcs.GCSTemporaryUpCommitParams

/**
  * 个人信用卡额度调整申请交易
  */
class TS220001(sessionId: String, channelId: String, gcsTemporaryUpCommitParams: GCSTemporaryUpCommitParams) extends GCSReq {

  //TODO 8位随机数
  setPageProps("eosId", "0602"+new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance.getTime))
  setPageProps("eosType", "02")
  setPageProps("eosReason", "01")
  setPageProps("eosEmergencyDegree", "2")
  setPageProps("eosCustomerType", "1")
  setPageProps("eosCustomerName", gcsTemporaryUpCommitParams.eosCustomerName)
  setPageProps("eosCustomerIdType", gcsTemporaryUpCommitParams.eosCustomerIdType)
  setPageProps("eosCustomerIdNumber", gcsTemporaryUpCommitParams.certNum)
  setPageProps("eosPhoneNumber", gcsTemporaryUpCommitParams.phoneNumber)
  setPageProps("eosCardNo", gcsTemporaryUpCommitParams.cardNo)
  setPageProps("eosCurrency", gcsTemporaryUpCommitParams.eosCurrency)
  setPageProps("eosPreAddLimit", gcsTemporaryUpCommitParams.eosPreAddLimit)
  setPageProps("eosStarLimitDate", gcsTemporaryUpCommitParams.eosStarLimitDate)
  setPageProps("eosEndlimitdate", gcsTemporaryUpCommitParams.eosEndLimitDate)
  setPageProps("eosRequestPath", "06")
  setPageProps("eosImpTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance.getTime))
  setPageProps("cardStyle", gcsTemporaryUpCommitParams.cardStyle)
  setPageProps("issuingBranchId", gcsTemporaryUpCommitParams.issuingBranchId)
  setPageProps("pmtCreditLimit", gcsTemporaryUpCommitParams.pmtCreditLimit)

  override def transactionID: String = "220001"

  override def requestChannelId: String = channelId

  override def transactionSessionId: String = sessionId

  override def pageKey: String = "RQ220001"

  override def transactionCode: String = "220001"
}
