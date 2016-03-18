package com.yada.sdk.gcs.protocol.req

import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 查询余额
  */
class TS410103Req(transactionSessionId: String, requestChannelId: String) extends GCSReq(transactionSessionId, requestChannelId) {
  override def transactionID: String = "TS410103"

  override def pageKey: String = ???
}
