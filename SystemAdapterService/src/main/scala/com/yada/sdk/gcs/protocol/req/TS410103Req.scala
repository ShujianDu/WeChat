package com.yada.sdk.gcs.protocol.req

import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 查询余额
  */
class TS410103Req(transactionSessionId: String, requestChannelId: String, cardNo: String, currencyCode: String) extends GCSReq(
  transactionID = "410103",
  pageKey = "RQ010101",
  transactionSessionId = transactionSessionId,
  requestChannelId = requestChannelId,
  transactionCode = "410103") {
  setPageProps("cardNo", cardNo)
  setPageProps("currencyCode", currencyCode)
}
