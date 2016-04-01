package com.yada.sdk.ds

import com.yada.sdk.ds.protocol.impl.P_8WEC060001
import cxf.com.forms.webservice.WechatAppPrjService

/**
  * 用于直销系统的连接执行测试
  */
object Test extends App {
  val name: String = "斐静芳"
  val idType: String = "1"
  val id: String = "320421197303020923"
  val currentPage: String = "1"
  val p = new P_8WEC060001(id, idType, name, currentPage)
//  val d = p.toData
//  println(JsonHandler.GLOBAL.toJSON(d))
  val r = p.send
  println(r.stat)

//  val j = "{\"head\":{\"city\":\"\",\"txnId\":\"8WEC060001\",\"imei\":\"wechat\",\"channelNo\":\"wechat\",\"deviceType\":\"8\",\"lon\":\"0\",\"deviceToken\":\"\",\"lat\":\"0\"},\"body\":{\"idType\":\"1\",\"name\":\"斐静芳\",\"currPage\":\"1\",\"id\":\"320421197303020923\"}}"
//  val s = new WechatAppPrjService
//  val r = s.getWechatAppPrj.getWechatAppPrj(j)
//  println(r)
}
