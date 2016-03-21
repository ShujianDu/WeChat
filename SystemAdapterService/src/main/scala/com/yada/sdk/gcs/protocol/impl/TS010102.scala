package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 根据卡号查询所有账户概要信息
  */
class TS010102(tranSessionId: String, reqChannelId: String, cardNo: String, currencyCode: String = "") extends GCSReq {
  setPageProps("cardNo", cardNo)
  setPageProps("currencyCode", currencyCode)

  override def transactionID: String = "010102"

  override def requestChannelId: String = reqChannelId

  override def transactionSessionId: String = tranSessionId

  override def pageKey: String = "RQ010101"

  override def transactionCode: String = "010102"

}
