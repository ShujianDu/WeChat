package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 历史分期查询
  *
  * @param sessionId    交易会话标识，用来表示客户访问身份
  * @param channelId    交易请求渠道标识
  * @param cardNo       卡号
  * @param startNumber  开始条数
  * @param selectNumber 选择的条数
  */
class TS011021(sessionId: String,
               channelId: String,
               cardNo: String,
               startNumber: String,
               selectNumber: String)(implicit gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {

  setPageProps("cardNo", cardNo)
  setPageProps("startNumber", startNumber)
  setPageProps("selectNumber", selectNumber)

  override def transactionID: String = "011021"

  override def requestChannelId: String = channelId

  override def transactionSessionId: String = sessionId

  override def pageKey: String = "RQ011021"

  override def transactionCode: String = "011021"
}
