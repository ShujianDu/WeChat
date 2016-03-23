package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 临时挂失
  */
class TS010059(sessionId: String, channelId: String, cardNo: String, entyMethod: String,
               idNum: String, idType: String, familyName: String, lostReason: String) extends GCSReq{

  setPageProps("cardNo",cardNo)
  setPageProps("entyMethod",entyMethod)
  setPageProps("idNum",idNum)
  setPageProps("idType",idType)
  setPageProps("familyName",familyName)
  setPageProps("lossReason",lostReason)

  override def transactionID: String = "010059"

  override def requestChannelId: String = channelId

  override def transactionSessionId: String = sessionId

  override def pageKey: String = "RQ010059"

  override def transactionCode: String = "010059"
}
