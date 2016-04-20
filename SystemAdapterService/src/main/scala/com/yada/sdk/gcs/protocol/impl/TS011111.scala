package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 海淘信用卡查询
  */
class TS011111(sessionId: String, channelId: String, idNum: String, idType: String, productCode: String)(gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {

  setPageProps("idNum", idNum)
  setPageProps("idType", idType)
  setPageProps("productCode", productCode)

  override def transactionID: String = "011111"

  override def requestChannelId: String = channelId

  override def transactionSessionId: String = sessionId

  override def pageKey: String = "RQ011111"

  override def transactionCode: String = "011111"
}
