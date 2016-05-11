package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 账单分期金额上下限查询
  *
  * @param tranSessionId 交易会话标识，用来表示客户访问身份
  * @param reqChannelId  交易请求渠道标识
  * @param accountId     帐号ID
  * @param currencyCode  币种
  *                      CNY-人民币
  *                      USD-美元
  */
class TS011062(tranSessionId: String,
               reqChannelId: String,
               accountId: String,
               currencyCode: String)(implicit gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {

  setPageProps("accountId", accountId)
  setPageProps("currencyCode", currencyCode)

  override def transactionID: String = "011062"

  override def requestChannelId: String = reqChannelId

  override def transactionSessionId: String = tranSessionId

  override def pageKey: String = "RQ011062"

  override def transactionCode: String = "011062"
}
