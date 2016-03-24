package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.protocol.GCSReq

class TS011005(sessionID: String, channelID: String, cardNo: Option[String], idType: Option[String], idNum: Option[String], startNum: String, totalNum: String, isFilterCardStatus: String = "0") extends GCSReq {

  cardNo match {
    case Some(x) => setPageProps("cardNo",x)
    case _ =>
  }

  idType match {
    case Some(x) => setPageProps("idType",x)
    case _ =>
  }

  idNum match {
    case Some(x) => setPageProps("idNum",x)
    case _ =>
  }
  setPageProps("startNum",startNum)
  setPageProps("totalNum",totalNum)
  setPageProps("isFilterCardStatus",isFilterCardStatus)

  override def transactionID: String = "011005"

  override def requestChannelId: String = channelID

  override def transactionSessionId: String = sessionID

  override def pageKey: String = "RQ011005"

  override def transactionCode: String = "011005"
}
