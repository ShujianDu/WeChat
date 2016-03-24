package com.yada.sdk.bcsp.protocol.impl

import com.yada.sdk.bcsp.xml.XmlHandler
import org.scalatest.{Matchers, FlatSpec}

/**
  * 通用业务类型，即客户端只需要发送组装好的短信，模板不在短信系统维护
  */
class T900000000_Test extends FlatSpec with Matchers {
  "T900000000`s reqXML" should "be formatted..." in {
    val handsetNo = "15910586649"
    val sysId = "HHAPWX"
    val bsnType = "T100000101"
    val content = "阿里路亚"
    val t9 = new T900000000(handsetNo, sysId, bsnType, content)
    val sms = t9.toSMS
    val req = XmlHandler.GLOBAL.toXml(sms)
    println(req)
    req.startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?>") shouldBe true

  }
}
