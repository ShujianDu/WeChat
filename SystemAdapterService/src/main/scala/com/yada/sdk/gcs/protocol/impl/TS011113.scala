package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 海淘信用卡短信通知
  *
  * @param sessionId     交易会话标识，用来表示客户访问身份
  * @param channelId     交易请求渠道标识
  * @param cardNo        海淘信用卡卡号
  * @param institutionId 机构号。默认值：BOCK
  */
class TS011113(sessionId: String,
               channelId: String,
               cardNo: String,
               institutionId: String = "BOCK")(implicit gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {
  setPageProps("institutionId", institutionId)
  setPageProps("cardNo", cardNo)

  override def transactionID: String = "011113"

  override def requestChannelId: String = channelId

  override def transactionSessionId: String = sessionId

  override def pageKey: String = "RQ011113"

  override def transactionCode: String = "011113"
}
