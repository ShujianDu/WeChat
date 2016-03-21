package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 账单寄送方式修改
  */
class TS010056(tranSessionId: String, reqChannelId: String, cardNo: String, billSendType: String) extends GCSReq {

  setPageProps("cardNo",cardNo)
  setPageProps("billSendType",billSendType)

  override def transactionID: String = "010056"

  override def requestChannelId: String = reqChannelId

  override def transactionSessionId: String = tranSessionId

  override def pageKey: String = "RP010002"

  override def transactionCode: String = "010056"
}
