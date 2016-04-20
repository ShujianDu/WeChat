package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.{GCSReq, GCSResp}

/**
  * 账单分期金额上下限查询
  */
class TS011062(tranSessionId: String, reqChannelId: String, accountId: String, currencyCode: String)(gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {

  setPageProps("accountId", accountId)
  setPageProps("currencyCode", currencyCode)

  override def transactionID: String = "011062"

  override def requestChannelId: String = reqChannelId

  override def transactionSessionId: String = tranSessionId

  override def pageKey: String = "RQ011062"

  override def transactionCode: String = "011062"

  override protected def respXMLToObject(xml: String): GCSResp = new GCSResp(xml) {
    // 不需要验证
    override protected def failedThrowException: Boolean = false
  }
}
