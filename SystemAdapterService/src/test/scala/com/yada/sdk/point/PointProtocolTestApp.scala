package com.yada.sdk.point

import com.yada.sdk.point.protocol.impl.{P0001_GetBalance, P0004_GetECIF, P0118_WeChatUserAuthentication, P0154_WeChatUserAuthenticationForCMG}

/**
  * 积分协议测试应用
  */
object PointProtocolTestApp extends App {

  // 积分余额查询
  //  p0001()

  // 获取ECIF号
    p0004()

  //加密卡号
  p0118()

  // 积分余额查询
  def p0001(): Unit = {
    val ecifNo = "TC0008117397"
    val p = new P0001_GetBalance(ecifNo)
    val resp = p.send
    println(resp)
  }

  def p0002(): Unit = {

  }

  // 获取ECIF号
  def p0004(): Unit = {
    val cardNo = "5149580068840943"
    val p = new P0004_GetECIF(cardNo)
    val resp = p.send
    println(resp)
  }

  def p0013(): Unit = {

  }

  def p0118(): Unit = {
    val cardNo = "5149580068840943"
    val security = PointSecurity.GLOBAL
    println(security.pointDes.decrypt(security.weChatUserAuthenticationEncrypt(cardNo),security.weChatUserAuthenticationDESKey))

    val p = new P0118_WeChatUserAuthentication(cardNo)
    val resp = p.send
    println(resp)
  }

  def p0154(): Unit = {
    val cardNo = "5149580068840943"
    val mobileNo = "13211112222"
    var security = PointSecurity.GLOBAL
    println(security.pointDes.decrypt(security.weChatUserAuthenticationForCMGEncrypt(cardNo),security.weChatUserAuthenticationDESKeyForCMG)
    )
//    val p = new P0154_WeChatUserAuthenticationForCMG(cardNo,mobileNo)
//    val resp = p.send
//    println(resp)
  }
}
