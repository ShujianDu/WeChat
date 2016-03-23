package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 根据证件号码或卡号查询客户信息
  */
class TS011101(sessionId: String, channelId: String, idType: String, idNum: String) extends GCSReq {

  //TODO 接口中需要增加类型

  override def transactionID: String = "011101"

  override def requestChannelId: String = channelId

  override def transactionSessionId: String = sessionId

  override def pageKey: String = "RQ011101"

  override def transactionCode: String = "011101"
}
