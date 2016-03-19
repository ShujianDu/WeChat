package com.yada.sdk.gcs.protocol.req

import com.yada.sdk.gcs.protocol.GCSReq
// TODO 注释补全 JFM
/**
  * Created by fengm on 2016/3/20.
  */
class TS010301Req(transactionSessionId: String, requestChannelId: String, cardNo: String) extends GCSReq(
  transactionID = "010301",
  pageKey = "RQ010101",
  transactionSessionId = transactionSessionId,
  requestChannelId = requestChannelId,
  transactionCode = "010301"
) {
  setPageProps("cardNo", cardNo)
}
