package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.protocol.{GCSCommonResp, GCSResp, GCSReq, GCSProtocol}

/**
  * Created by fengm on 2016/3/20.
  */
class TS010102(tranSessionId: String, reqChannelId: String, cardNo: String, currencyCode: String = "") extends GCSProtocol[GCSCommonResp] {
  setPageProps("cardNo", cardNo)
  setPageProps("currencyCode", currencyCode)

  override def transactionID: String = "010102"

  override def requestChannelId: String = reqChannelId

  override def transactionSessionId: String = tranSessionId

  override def pageKey: String = "RQ010101"

  override def transactionCode: String = "010102"

  override protected def generate(xml: String): GCSCommonResp = ???
}
