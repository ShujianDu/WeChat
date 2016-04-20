package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 虚拟卡查询(分页)
  */
class TS011031(tranSessionId: String, reqChannelId: String, cardNo: String, startNum: String, totalNum: String)(gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {

  setPageProps("cardNo", cardNo)
  setPageProps("institutionId", "BOCK")
  setPageProps("startNum", startNum)
  setPageProps("totalNum", totalNum)


  override def transactionID: String = "011031"

  override def requestChannelId: String = reqChannelId

  override def transactionSessionId: String = tranSessionId

  override def pageKey: String = "RQ011031"

  override def transactionCode: String = "011031"
}
