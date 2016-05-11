package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.{GCSReq, GCSResp}

/**
  * 信用卡激活--EOM实现
  */
class TS010062(sessionId: String, channelId: String, cardNo: String) (gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {

  setPageProps("cardNo", cardNo)

  override def transactionID: String = "010062"

  override def requestChannelId: String = channelId

  override def transactionSessionId: String = sessionId

  override def pageKey: String = "RQ010101"

  override def transactionCode: String = "010062"

  override def respXMLToObject(xml: String): GCSResp = new GCSResp(xml){
    //不需要验证
    override protected def failedThrowException: Boolean = false
  }
}
