package com.yada.sdk.gcs

import com.yada.sdk.gcs.protocol.impl._
import com.yada.system.adapter.gcs.{AmountLimitParams, CardNoParams, GCSConsumptionInstallmentParams, GCSServiceImpl}

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
  //    testTS010059()

  // 解除临时挂失--总交易
  //    testTS010060()

  // 海淘卡挂失使用
  //  testTS010063()

  // 根据卡号查询所有账户概要信息
  //  testTS010102()

  // 按卡号查询持卡人客户信息交易--“BOC”客户号
  //  testTS010201()

  // 账单周期查询
  //    testTS010301()

  // 某一期账单信息汇总查询
  //  testTS010302()

  // 未出账单/已出账单某账期交易查询（带存入/支出）
  //  testTS010310()

  // 根据卡号查询所有客户信息和卡信息
  //    testTS011005()

  // 消费分期查询
  //    testTS011007()

  // 历史分期查询
  //  testTS011021()

  // 虚拟卡查询(分页)
  //  testTS011031()

  // 账单分期金额上下限查询
  //  testTS011062()

  // 根据证件号码或卡号查询客户信息
  //  testTS011101()

  // 海淘信用卡查询
  //  testTS011111()

  // 海淘信用卡短信通知
  //  testTS011113()

  // 卡户分期项目账单分期（费用试算）
  //    testTS011170()

  // 卡户分期项目账单分期（授权）
  //    testTS011171()

  // 卡户分期项目消费分期（费用试算）
  //      testTS011172()

  // 卡户分期项目消费分期（授权）
  //    testTS011173()

  // 查询客户手机预留号码
  //  testTS140028()

  // 信用卡额度调整状态查询
  //  testTS140031()

  // 额度评测交易
  //  testTS190024()

  // 个人信用卡额度调整申请交易
  //  testTS220001()

  // 查询余额
  //  testTS410103()


  //查询卡状态
//  testTS011145()

  //卡片激活
//  testTS010062

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
    * 临时挂失
    */
  def testTS010059(): Unit = {
    val cardNo = "377677530138585"
    val req = new TS010059(sessionID, channelID, cardNo, "01", "AAP0345", "03", "AAP0345", "02")
    req.send
  }

  /**
    * 临时挂失
    */
  def testTS010060(): Unit = {
    val cardNo = "377677530138585"
    val req = new TS010060(sessionID, channelID, cardNo, "AAP0345", "AAP0345", "03")
    req.send
  }

  /**
    * 海淘卡挂失使用
    */
  def testTS010063(): Unit = {
    // TODO 海淘卡挂失，不可测试
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

  /**
    * 按卡号查询持卡人客户信息交易--“BOC”客户号
    */
  def testTS010201(): Unit = {
    val cardNo = "5149580068840943"
    val req = new TS010201(sessionID, channelID, cardNo)
    val resp = req.send
    println(resp)
  }

  /**
    * 账单周期查询
    */
  def testTS010301(): Unit = {
    val cardNo = "377677523143733"
    val req = new TS010301(sessionID, channelID, cardNo)
    val resp = req.send
    println(resp)
  }

  /**
    * 某一期账单信息汇总查询
    */
  def testTS010302(): Unit = {
    val accountId = "001A0213064FF77E"
    val statementNo = "18"
    val req = new TS010302(sessionID, channelID, statementNo, accountId)
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
    val req = new TS010310(sessionID, channelID, cardNo, currencyCode, queryType, startNum, totalNum, startDate, endDate)
    val resp = req.send
    println(resp)
  }

  /**
    * 根据卡号查询所有客户信息和卡信息
    */
  def testTS011005(): Unit = {
    val cardNo = "4693805331345849"
    val req = new TS011005(sessionID, channelID, Some(cardNo), None, None, "1", "10")
    val resp = req.send
    println(resp)
  }

  /**
    * 消费分期查询
    */
  def testTS011007(): Unit = {
    // TODO 交易没有返回交易列表
    val cardNo = "5149580068840943"
    val currencyCode = "CNY"
    val startNumber = "1"
    val selectNumber = "10"
    val req = new TS011007(sessionID, channelID, cardNo, currencyCode, startNumber, selectNumber)
    val resp = req.send
    println(resp)
  }

  /**
    * 历史分期查询
    */
  def testTS011021(): Unit = {
    val cardNo = "5149580068840943"
    val startNumber = "1"
    val selectNumber = "10"
    val req = new TS011021(sessionID, channelID, cardNo, startNumber, selectNumber)
    val resp = req.send
    println(resp)
  }


  /**
    * 虚拟卡查询(分页)
    */
  def testTS011031(): Unit = {
    // TODO 当前查询都是无虚拟卡
    val cardNo = "5149580068840943"
    val startNum = "1"
    val totalNum = "10"
    val req = new TS011031(sessionID, channelID, cardNo, startNum, totalNum)
    val resp = req.send
    println(resp)
  }

  /**
    * 账单分期金额上下限查询
    */
  def testTS011062(): Unit = {
    // TODO 当前查询是账户状态异常，不支持账单分期
    val accountId = "001A0213064FF77E"
    val currencyCode = "CNY"
    val req = new TS011062(sessionID, channelID, accountId, currencyCode)
    val resp = req.send
    println(resp)
  }

  /**
    * 根据证件号码或卡号查询客户信息
    */
  def testTS011101(): Unit = {
    val idNum = "AAP0191"
    val idType = "SSNO"
    val req = new TS011101(sessionID, channelID, None, Some(idType), Some(idNum))
    //    val cardNo = "5149580068840943"
    //    val req = new TS011101(sessionID, channelID, Some(cardNo), None, None)
    val resp = req.send
    println(resp)
  }

  /**
    * 海淘信用卡查询
    */
  def testTS011111(): Unit = {
    throw new RuntimeException("海淘卡查询，业务没有上线")
  }

  /**
    * 海淘信用卡短信通知
    */
  def testTS011113(): Unit = {
    throw new RuntimeException("海淘信用卡短信通知，业务没有上线")
  }

  /**
    * 卡户分期项目账单分期（费用试算）
    */
  def testTS011170(): Unit = {
    // TODO 卡户分期项目账单分期（费用试算）无法测试
    val cardNo = "377677523143733"
    val currencyCode = "CNY"
    val temp = new GCSServiceImpl
    val params = CardNoParams(sessionID, channelID, cardNo)
    val billingPeriods = temp.getBillingPeriods(params)
    println(billingPeriods.head.periodStartDate)
    println(billingPeriods.head.periodEndDate)
    val amountLimitParams = AmountLimitParams(sessionID, channelID, cardNo, currencyCode)
    val amountLimit = temp.getAmountLimit(amountLimitParams)
    val req = new TS011170(sessionID, channelID, billingPeriods.head.accountId, billingPeriods.head.statementNo, "CNY", amountLimit.minAmount, amountLimit.maxAmount, "6", "1", "A")
    val resp = req.send
    println(resp)
  }

  /**
    * 卡户分期项目账单分期（授权）
    */
  def testTS011171(): Unit = {
    //TODO 卡户分期项目账单分期（授权） 无法测试
  }

  /**
    * 卡户分期项目消费分期（费用试算）
    */
  def testTS011172(): Unit = {
    val req = new TS011172(sessionID, channelID, "001A021306500928", "001A021306500928", "CNY", "40", "2",
      "000000000001300000", "377677523143733", "1297812597499142", "6", "1", "A")
    val resp = req.send
    println(resp)
  }

  /**
    * 卡户分期项目消费分期（授权）
    */
  def testTS011173(): Unit = {
    // TODO 卡户分期项目消费分期（授权） 无法测试
    val req = new TS011173(sessionID, channelID, "001A021306500928", "001A021306500928", "CNY", "40", "2",
      "000000000001300000", "377677523143733", "1297812597499142", "6", "1", "A")
    val resp = req.send
    println(resp)
  }

  /**
    * 查询客户手机预留号码
    */
  def testTS140028(): Unit = {
    val idNum = "AAP0191"
    val idType = "SSNO"
    val req = new TS140028(sessionID, channelID, idType, idNum)
    val resp = req.send
    println(resp)
  }

  /**
    * 信用卡额度调整状态查询
    */
  def testTS140031(): Unit = {
    // TODO 信用卡额度调整状态查询 无法测试
    //    val cardNo = "5149580068840943"
    //    val req = new TS140031(sessionID, channelID, None, None, Some(cardNo), None)
    //    val resp = req.send
    //    println(resp)
  }

  /**
    * 额度评测交易
    */
  def testTS190024(): Unit = {
    // TODO 额度评测交易。无法测试，功能没上
    val certType = "SSNO"
    val certNum = "AAP0191"
    val phoneNumber = "13910150191"
    val cardNo = "5149580068840943"
    val currencyNo = "CNY"
    val at5605 = "02"
    val at5602 = "06"
    val req = new TS190024(sessionID, channelID, certType, certNum, phoneNumber, cardNo, currencyNo, at5605, at5602)
    val resp = req.send
    println(resp)
  }

  /**
    * 个人信用卡额度调整申请交易
    */
  def testTS220001(): Unit = {
    // TODO 个人信用卡额度调整申请交易。无法测试，功能没上
  }

  /**
    * 查询余额
    */
  def testTS410103(): Unit = {
    val cardNo = "5149580068840943"
    val currencyNo = "CNY"
    val req = new TS410103(sessionID, channelID, cardNo, currencyNo)
    val resp = req.send
    println(resp)
  }

  def testTS011145(): Unit = {
    val cardNo = "4096688277156575"
    val req = new TS011145(sessionID, channelID, cardNo)
    val resp = req.send
    println(resp)
  }

  def testTS010062(): Unit = {
    val cardNo = "4096688277156575"
    val req = new TS010062(sessionID, channelID, cardNo)
    val resp = req.send
    println(resp)
  }

}
