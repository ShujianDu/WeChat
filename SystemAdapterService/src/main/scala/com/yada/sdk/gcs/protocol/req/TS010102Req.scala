package com.yada.sdk.gcs.protocol.req

import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 根据卡号查询所有账户概要信息
  */
class TS010102Req(transactionSessionId: String, requestChannelId: String, cardNo: String, currencyCode: String = "") extends GCSReq(
  transactionID = "010102",
  pageKey = "RQ010101",
  transactionSessionId = transactionSessionId,
  requestChannelId = requestChannelId,
  transactionCode = "010102") {

  setPageProps("cardNo", cardNo)
  setPageProps("currencyCode", currencyCode)
}
