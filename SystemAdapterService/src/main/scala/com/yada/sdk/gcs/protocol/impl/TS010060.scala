package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 解除临时挂失--总交易
  */
class TS010060(sessionId: String, channelId: String, cardNo: String, idNum: String,
               familyName: String, idType: String) extends GCSReq{

  setPageProps("cardNo",cardNo)
  setPageProps("entyMethod","01")
  setPageProps("idNum",idNum)
  setPageProps("idType",idType)
  setPageProps("familyName",familyName)

  override def transactionID: String = "010060"

  override def requestChannelId: String = channelId

  override def transactionSessionId: String = sessionId

  override def pageKey: String = "RQ010060"

  override def transactionCode: String = "010060"
}
