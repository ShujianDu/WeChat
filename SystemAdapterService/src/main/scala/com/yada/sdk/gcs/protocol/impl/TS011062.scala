package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.protocol.{GCSCommonResp, GCSProtocol}

//TODO 需要返回码-JFM
class TS011062(tranSessionId: String, reqChannelId: String,accountId:String,currencyCode:String) extends GCSProtocol[GCSCommonResp]{

  setPageProps("accountId",accountId)
  setPageProps("currencyCode",currencyCode)

  override protected def generate(xml: String): GCSCommonResp = new GCSCommonResp(xml)

  override def transactionID: String = "011062"

  override def requestChannelId: String = reqChannelId

  override def transactionSessionId: String = tranSessionId

  override def pageKey: String = "RQ011062"

  override def transactionCode: String = "011062"
}
