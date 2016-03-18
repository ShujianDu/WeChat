package com.yada.sdk.gcs.protocol.req

import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 查询余额
  */
class TS410103Req extends GCSReq {
  override def transactionID: String = "TS410103"

  override def pageKey: String = ???
}
