package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 未出账单/已出账单某账期交易查询（带存入/支出）
  *
  * @param cardNo       卡号
  * @param currencyCode 交易货币<br>
  *                     CNY-人民币<br>
  *                     USD-美元<br>
  * @param queryType    查询类型<br>
  *                     未出账单查询:          UNSM<br>
  *                     某历史账期交易查询：   ALLT（并且要配合startDate、endDate使用，其中，startDate上送账期开始日期，endDate上送账期结束日期。）<br>
  *                     全部交易查询：         ALLT<br>
  * @param startNum     开始条数。想从第一条开始取值，该值上送“1”
  * @param totalNum     总共需要的条数
  * @param startDate    交易开始日期。格式：yyyy-MM-dd
  * @param endDate      交易结束日期。格式：yyyy-MM-dd
  */
class TS010310(sessionId: String, channelId: String,
               cardNo: String,
               currencyCode: String,
               queryType: String,
               startNum: String,
               totalNum: String,
               startDate: String,
               endDate: String)(gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {


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
