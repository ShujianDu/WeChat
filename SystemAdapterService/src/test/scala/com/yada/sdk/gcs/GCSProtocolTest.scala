package com.yada.sdk.gcs

import com.yada.sdk.gcs.protocol.impl.{TS010002, TS010056}

/**
  * GCS协议执行测试
  */
object GCSProtocolTest extends App {
  val sessionID = "93c4399ad67d925fa40d0693adb0a222"
  val channelID = "WX01"
  // 账单寄送方式查询
  //  testTS010002()
  // 账单寄送方式修改
  testTS010056()

  def testTS010002(): Unit = {
    val cardNo = "5149580068840943"
    val req = new TS010002(sessionID, channelID, cardNo)
    val resp = req.send
    println(resp)
  }

  def testTS010056(): Unit = {
    val cardNo = "5149580068840943"
    val req = new TS010056(sessionID, channelID, cardNo, "C")
    val resp = req.send
    println(resp)
  }
}
