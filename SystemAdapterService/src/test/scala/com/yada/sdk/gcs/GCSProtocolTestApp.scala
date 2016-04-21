package com.yada.sdk.gcs

import com.yada.sdk.gcs.protocol.impl._

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
  //  testTS010102()

  // 按卡号查询持卡人客户信息交易--“BOC”客户号
  //  testTS010201()

  // 账单周期查询
  //  testTS010301()

  // 某一期账单信息汇总查询
  //  testTS010302()

  // 未出账单/已出账单某账期交易查询（带存入/支出）
  //  testTS010310()

  // 根据卡号查询所有客户信息和卡信息
  testTS011005()

  /**
    * 账单寄送方式查询
    */
  def testTS010002(): Unit = {
    val cardNo = "5149580068840943"
    val req = new TS010002(sessionID, channelID, cardNo)()
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
    val req = new TS010056(sessionID, channelID, cardNo, "C")()
    val resp = req.send
    println(resp)
  }

  /**
    * 临时挂失
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
    val req = new TS010102(sessionID, channelID, cardNo)()
    val resp = req.send
    println(resp)
  }

  /**
    * 按卡号查询持卡人客户信息交易--“BOC”客户号
    */
  def testTS010201(): Unit = {
    val cardNo = "5149580068840943"
    val req = new TS010201(sessionID, channelID, cardNo)()
    val resp = req.send
    println(resp)
  }

  /**
    * 账单周期查询
    */
  def testTS010301(): Unit = {
    val cardNo = "5149580068840943"
    val req = new TS010301(sessionID, channelID, cardNo)()
    val resp = req.send
    println(resp)
  }

  /**
    * 某一期账单信息汇总查询
    */
  def testTS010302(): Unit = {
    val accountId = "001A0213064FF77E"
    val statementNo = "18"
    val req = new TS010302(sessionID, channelID, statementNo, accountId)()
    val resp = req.send
    println(resp)
  }

  /**
    * 未出账单/已出账单某账期交易查询（带存入/支出）
    */
  def testTS010310(): Unit = {
    val cardNo = "5149580068840943"
    val currencyCode = "CNY"
    val queryType = "ALLT"
    val startNum = "1"
    val totalNum = "10"
    val startDate = "2022-09-11"
    val endDate = "2022-10-10"
    val req = new TS010310(sessionID, channelID, cardNo, currencyCode, queryType, startNum, totalNum, startDate, endDate)()
    val resp = req.send
    println(resp)
  }

  /**
    * 根据卡号查询所有客户信息和卡信息
    */
  def testTS011005(): Unit = {
    val cardNo = "5149580068840943"
    val req = new TS011005(sessionID, channelID, Some(cardNo), None, None, "1", "10")()
    val resp = req.send
    println(resp)
  }
}
