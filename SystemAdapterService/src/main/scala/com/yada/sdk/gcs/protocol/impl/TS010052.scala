package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 信用卡挂失
  */
class TS010052(sessionId: String, channelId: String, cardNo: String, idType: String, idNum: String,
               familyName: String, lostReason: String)(gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {

  setPageProps("cardNo", cardNo)
  setPageProps("idType", idType)
  setPageProps("idNum", idNum)
  setPageProps("familyName", familyName)
  setPageProps("lossReason", lostReason)

  override def transactionID: String = "010052"

  override def requestChannelId: String = channelId

  override def transactionSessionId: String = sessionId

  override def pageKey: String = "RQ01052"

  override def transactionCode: String = "010052"
}
