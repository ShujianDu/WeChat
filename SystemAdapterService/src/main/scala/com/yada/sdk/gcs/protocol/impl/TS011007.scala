package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 消费分期查询 -查询可消费分期的交易
  *
  * @param tranSessionId   交易会话标识，用来表示客户访问身份
  * @param reqChannelId    交易请求渠道标识
  * @param cardNo          卡号
  * @param currencyCode    币种（只支持人民币）
  * @param startNumber     开始条数
  * @param selectNumber    选择条数
  * @param transactionType 交易类型（“UNSM”：查询未出账单的交易）
  */
class TS011007(tranSessionId: String, reqChannelId: String,
               cardNo: String,
               currencyCode: String,
               startNumber: String,
               selectNumber: String,
               transactionType: String = "UNSM")(implicit gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {

  setPageProps("cardNo", cardNo)
  setPageProps("currencyCode", currencyCode)
  setPageProps("transactionType", transactionType)
  setPageProps("startNumber", startNumber)
  setPageProps("selectNumber", selectNumber)

  override def transactionID: String = "011007"

  override def requestChannelId: String = reqChannelId

  override def transactionSessionId: String = tranSessionId

  override def pageKey: String = "RQ011007"

  override def transactionCode: String = "011007"
}
