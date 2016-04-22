package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 根据卡号查询所有账户概要信息
  *
  * @param cardNo       卡号
  * @param currencyCode 币种（可选）
  *                     当此域不上送或上送为""时，则返回该卡号下对应的所有账户信息列表。
  *                     当此域上送不为空值时，则返回上送币种对应的账户信息。<br>
  *                     货币代码、精度、中文名称<br>
  *                     156、2、	人民币
  *
  */
class TS010102(tranSessionId: String, reqChannelId: String, cardNo: String, currencyCode: String = "")(gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {
  setPageProps("cardNo", cardNo)
  setPageProps("currencyCode", currencyCode)

  override def transactionID: String = "010102"

  override def requestChannelId: String = reqChannelId

  override def transactionSessionId: String = tranSessionId

  override def pageKey: String = "RQ010101"

  override def transactionCode: String = "010102"

}
