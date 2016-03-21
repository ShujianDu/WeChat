package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 查询余额
  */
class TS410103(tranSessionId: String, reqChannelId: String, cardNo: String, currencyCode: String) extends GCSReq {
  setPageProps("cardNo", cardNo)
  setPageProps("currencyCode", currencyCode)

  override def transactionID: String = "410103"

  override def requestChannelId: String = reqChannelId

  override def transactionSessionId: String = tranSessionId

  override def pageKey: String = "RQ010101"

  override def transactionCode: String = "410103"

}
