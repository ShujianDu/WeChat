package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.protocol.{GCSCommonResp, GCSReq}

/**
  * 某一期账单信息汇总查询
  */
class TS010302(tranSessionId: String, reqChannelId: String, statementNo: String, accountId: String) extends GCSReq[GCSCommonResp] {
  setPageProps("statementNo", statementNo)
  setPageProps("accountId", accountId)

  override def transactionID: String = "010302"

  override def requestChannelId: String = reqChannelId

  override def transactionSessionId: String = tranSessionId

  override def pageKey: String = "RQ010302"

  override def transactionCode: String = "010302"

  override protected def generate(xml: String): GCSCommonResp = new GCSCommonResp(xml)
}
