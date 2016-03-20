package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.protocol.{GCSCommonResp, GCSProtocol}

/**
  * 账单周期查询
  */
class TS010301(tranSessionId: String, reqChannelId: String, cardNo: String) extends GCSProtocol[GCSCommonResp] {
  setPageProps("cardNo", cardNo)

  override def transactionID: String = "010301"

  override def requestChannelId: String = reqChannelId

  override def transactionSessionId: String = tranSessionId

  override def pageKey: String = "RQ010101"

  override def transactionCode: String = "010301"

  override protected def generate(xml: String): GCSCommonResp = new GCSCommonResp(xml)
}
