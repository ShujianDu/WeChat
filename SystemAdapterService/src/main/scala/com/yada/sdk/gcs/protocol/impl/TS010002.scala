package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 账单寄送方式查询
  */
class TS010002(tranSessionId: String, reqChannelId: String, cardNo: String) extends GCSReq {

  setPageProps("cardNo",cardNo)

  override def transactionID: String = "010002"

  override def requestChannelId: String = reqChannelId

  override def transactionSessionId: String = tranSessionId

  override def pageKey: String = "RQ010101"

  override def transactionCode: String = "010002"
}
