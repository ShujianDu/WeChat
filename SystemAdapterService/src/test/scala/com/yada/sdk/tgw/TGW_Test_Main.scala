package com.yada.sdk.tgw

import com.yada.sdk.tgw.protocol.impl.Fun001

/**
  * TGW测试
  */
object TGW_Test_Main extends App {

  val cardNo = "5149580650447008"
  val password = "111111"
  val fun001 = new Fun001(cardNo, password)
  val resp = fun001.send
  println(resp.rspCod)
}
