package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 未出账单/已出账单某账期交易查询（带存入/支出）
  */
class TS010310(sessionId: String, channelId: String, cardNo: String, currencyCode: String, queryType: String,
               startNum: String, totalNum: String, startDate: String, endDate: String)(gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {


  setPageProps("cardNo", cardNo)
  setPageProps("currencyCode", currencyCode)
  setPageProps("queryType", queryType)
  setPageProps("startNum", startNum)
  setPageProps("totalNum", totalNum)
  setPageProps("startDate", startDate)
  setPageProps("endDate", endDate)
  setPageProps("minAmount", "")
  setPageProps("maxAmount", "")
  setPageProps("flag", "")


  override def transactionID: String = "010310"

  override def requestChannelId: String = channelId

  override def transactionSessionId: String = sessionId

  override def pageKey: String = "RQ010303"

  override def transactionCode: String = "010310"
}
