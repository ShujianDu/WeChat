package com.yada.sdk.gcs

import com.yada.sdk.gcs.protocol.impl.{TS010002, TS010056, TS010102}

/**
  * GCS协议执行测试
  */
object GCSProtocolTestApp extends App {
  val sessionID = "93c4399ad67d925fa40d0693adb0a222"
  val channelID = "WX01"
  // 账单寄送方式查询
  //  testTS010002()

  // 信用卡挂失
  //  testTS010052()

  // 账单寄送方式修改
  //  testTS010056()

  // 临时挂失
  //  testTS010059()

  // 解除临时挂失--总交易
  //  testTS010060()

  // 海淘卡挂失使用
  //  testTS010063()

  // 根据卡号查询所有账户概要信息
  testTS010102()

  /**
    * 账单寄送方式查询
    */
  def testTS010002(): Unit = {
    val cardNo = "5149580068840943"
    val req = new TS010002(sessionID, channelID, cardNo)
    val resp = req.send
    println(resp)
  }

  /**
    * 信用卡挂失
    */
  def testTS010052(): Unit = {
    throw new RuntimeException("信用卡挂失，不可测试")
  }

  /**
    * 账单寄送方式修改
    */
  def testTS010056(): Unit = {
    val cardNo = "5149580068840943"
    val req = new TS010056(sessionID, channelID, cardNo, "C")
    val resp = req.send
    println(resp)
  }

  /**
    * 账单寄送方式修改
    */
  def testTS010059(): Unit = {
    // TODO
  }

  /**
    * 临时挂失
    */
  def testTS010060(): Unit = {
    // TODO
  }

  /**
    * 海淘卡挂失使用
    */
  def testTS010063(): Unit = {
    throw new RuntimeException("海淘卡挂失，不可测试")
  }

  /**
    * 根据卡号查询所有账户概要信息
    */
  def testTS010102(): Unit = {
    val cardNo = "5149580068840943"
    val req = new TS010102(sessionID, channelID, cardNo)
    val resp = req.send
    println(resp)
  }
}
