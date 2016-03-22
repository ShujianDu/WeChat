package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 额度评测交易
  */
class TS190024(tranSessionId: String, reqChannelId: String, certType: String, certNum: String, phoneNumber: String, cardNo: String, currencyNo: String, AT5605: String, AT5602: String) extends GCSReq {
  setPageProps("certType", certType)
  setPageProps("certNum", certNum)
  setPageProps("phoneNumber", phoneNumber)
  setPageProps("cardNo", cardNo)
  setPageProps("currencyNo", currencyNo)
  setPageProps("AT5605", AT5605)
  setPageProps("AT5602", AT5602)

  override def transactionID: String = "190024"

  override def requestChannelId: String = reqChannelId

  override def transactionSessionId: String = tranSessionId

  override def pageKey: String = "RQ190024"

  override def transactionCode: String = "190024"
}
