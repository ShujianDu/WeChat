package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 查询余额
  *
  * @param tranSessionID 交易会话标识，用来表示客户访问身份
  * @param reqChannelID  交易请求渠道标识
  * @param cardNo        卡号
  * @param currencyCode  货币号
  */
class TS410103(tranSessionID: String,
               reqChannelID: String,
               cardNo: String,
               currencyCode: String)(implicit gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {
  setPageProps("cardNo", cardNo)
  setPageProps("currencyCode", currencyCode)

  override def transactionID: String = "410103"

  override def requestChannelId: String = reqChannelID

  override def transactionSessionId: String = tranSessionID

  override def pageKey: String = "RQ010101"

  override def transactionCode: String = "410103"

}
