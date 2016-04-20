package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 历史分期查询
  */
class TS011021(sessionId: String, channelId: String,cardNo: String, startNumber: String, selectNumber: String)(gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient)  {

  setPageProps("cardNo",cardNo)
  setPageProps("startNumber",startNumber)
  setPageProps("selectNumber",selectNumber)

  override def transactionID: String = "011021"

  override def requestChannelId: String = channelId

  override def transactionSessionId: String = sessionId

  override def pageKey: String = "RQ011021"

  override def transactionCode: String = "011021"
}
