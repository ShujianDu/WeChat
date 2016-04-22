package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 按卡号查询持卡人客户信息交易--“BOC”客户号
  *
  * @see com.yada.sdk.gcs.protocol.impl.TS011101 客户的信息较少的报文
  */
class TS010201(sessionId: String, channelId: String, cardNo: String)(gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {

  setPageProps("cardNo", cardNo)

  override def transactionID: String = "010201"

  override def requestChannelId: String = channelId

  override def transactionSessionId: String = sessionId

  override def pageKey: String = "RQ010101"

  override def transactionCode: String = "010201"
}
