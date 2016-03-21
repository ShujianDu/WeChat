package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.protocol.GCSReq

class TS140028(tranSessionId: String, reqChannelId: String, idType: String, idNo: String) extends GCSReq {

  setPageProps("idType",idType)
  setPageProps("idNo",idNo)

  override def transactionID: String = "140028"

  override def requestChannelId: String = reqChannelId

  override def transactionSessionId: String = tranSessionId

  override def pageKey: String = "RQ140028"

  override def transactionCode: String = "140028"
}
