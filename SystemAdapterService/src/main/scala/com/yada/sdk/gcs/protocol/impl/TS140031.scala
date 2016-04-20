package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 信用卡额度调整状态查询
  */
class TS140031(sessionId: String, channelId: String, eosIDType: String, idNumber: String,
               cardNo: String, eosId: String)(gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {

  setPageProps("eosIdtype", eosIDType)
  setPageProps("idNumber", idNumber)
  setPageProps("cardNo", cardNo)
  setPageProps("startNum", "1")
  setPageProps("totalNum", "20")
  setPageProps("eosId", eosId)

  override def transactionID: String = "140031"

  override def requestChannelId: String = channelId

  override def transactionSessionId: String = sessionId

  override def pageKey: String = "RQ140031"

  override def transactionCode: String = "140031"
}
