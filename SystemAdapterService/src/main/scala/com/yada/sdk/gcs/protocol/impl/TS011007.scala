package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 消费分期查询 -查询可消费分期的交易
  */
class TS011007(tranSessionId: String, reqChannelId: String, cardNo: String, currencyCode: String,
               startNumber: String, selectNumber: String, transactionType: String = "UNSM")(gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {

  setPageProps("cardNo", cardNo)
  setPageProps("currencyCode", currencyCode)
  setPageProps("transactionType", transactionType)
  setPageProps("startNumber", startNumber)
  setPageProps("selectNumber", selectNumber)

  override def transactionID: String = "011007"

  override def requestChannelId: String = reqChannelId

  override def transactionSessionId: String = tranSessionId

  override def pageKey: String = "RQ011007"

  override def transactionCode: String = "011007"
}
