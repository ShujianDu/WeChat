package com.yada.sdk.gcs.protocol.req

import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 根据卡号查询所有账户概要信息
  */
class TS010102Req extends GCSReq{
  override def transactionID: String = "TS010102"

  override def pageKey: String = "RQ010101"
}
