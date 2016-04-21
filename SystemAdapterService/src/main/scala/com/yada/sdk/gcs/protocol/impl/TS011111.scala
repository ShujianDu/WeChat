package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 海淘信用卡查询
  *
  * @param sessionId   交易会话标识，用来表示客户访问身份
  * @param channelId   交易请求渠道标识
  * @param idNum       证件号码
  * @param idType      证件类型
  * @param productCode 海淘信用卡产品代码
  */
class TS011111(sessionId: String,
               channelId: String,
               idNum: String,
               idType: String,
               productCode: String)(implicit gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {

  setPageProps("idNum", idNum)
  setPageProps("idType", idType)
  setPageProps("productCode", productCode)

  override def transactionID: String = "011111"

  override def requestChannelId: String = channelId

  override def transactionSessionId: String = sessionId

  override def pageKey: String = "RQ011111"

  override def transactionCode: String = "011111"
}
