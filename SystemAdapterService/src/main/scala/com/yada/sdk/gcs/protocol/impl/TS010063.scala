package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 信用卡挂失
  * 海淘卡挂失使用
  *
  * @param cardNo 卡号
  */
class TS010063(sessionId: String, channelId: String, cardNo: String)(gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {

  setPageProps("cardNo", cardNo)

  override def transactionID: String = "010063"

  override def requestChannelId: String = channelId

  override def transactionSessionId: String = sessionId

  override def pageKey: String = "RQ010101"

  override def transactionCode: String = "010063"
}
