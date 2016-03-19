package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.protocol.{GCSCommonResp, GCSProtocol}

/**
  * Created by fengm on 2016/3/20.
  */
class TS010301(tranSessionId: String, reqChannelId: String, cardNo: String) extends GCSProtocol[GCSCommonResp] {
  setPageProps("cardNo", cardNo)

  override def transactionID: String = "010302"

  override def requestChannelId: String = reqChannelId

  override def transactionSessionId: String = tranSessionId

  override def pageKey: String = "RQ010302"

  override def transactionCode: String = "010302"

  override protected def generate(xml: String): GCSCommonResp = new GCSCommonResp(xml)
}
