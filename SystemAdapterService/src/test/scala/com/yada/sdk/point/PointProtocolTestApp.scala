package com.yada.sdk.point

import com.yada.sdk.point.protocol.impl.P0004_GetECIF

/**
  * 积分协议测试应用
  */
object PointProtocolTestApp extends App {

  // 获取ECIF号
  p0004()

  def p0001(): Unit = {

  }

  def p0002(): Unit = {

  }

  def p0004(): Unit = {
    val cardNo = "5149580068840943"
    val p = new P0004_GetECIF(cardNo)
    val resp = p.send
    println(resp)
  }

  def p0013(): Unit = {

  }

  def p0118(): Unit = {

  }

  def p0154(): Unit = {

  }
}
