package com.yada.sdk.bcsp.protocol.impl

import com.yada.sdk.bcsp.protocol.{BCSPResp, SeqNoGenerator}
import com.yada.sdk.bcsp.xml.XmlHandler
import org.mockito.Mockito
import org.scalatest.{Matchers, FlatSpec}
import scala.xml._

/**
  * 通用业务类型，即客户端只需要发送组装好的短信，模板不在短信系统维护
  */
class T900000000_Test extends FlatSpec with Matchers {
  "T900000000`s reqXML" should "be formatted..." in {
    val seqNoGenerator = Mockito.mock(classOf[SeqNoGenerator])
    Mockito.when(seqNoGenerator.get).thenReturn("1111")
    SeqNoGenerator.GLOBAL = seqNoGenerator
    val handsetNo = "15910586649"
    val sysId = "HHAPWX"
    val bsnType = "T100000101"
    val content = "阿里路亚"
    val t9 = new T900000000(handsetNo, sysId, bsnType, content)
    val sms = t9.toSMS
    val req = XmlHandler.GLOBAL.toXml(sms)
    req.startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?>") shouldBe true
    val xml = XML.loadString(req)
    (xml \ "handsetNo").head.text shouldBe handsetNo
    (xml \ "sysId").head.text shouldBe sysId
    (xml \ "bsnType").head.text shouldBe bsnType
    (xml \ "seqNo").head.text shouldBe "1111"
    (xml \\ "item").head.text shouldBe content
  }

  "T900000000`s respXML" should "be parsed" in {
    val resp = "000094<?xml version=\"1.0\" encoding=\"UTF-8\"?><sms><retCode>0</retCode><retMsg>Success!</retMsg></sms>"
    val sms = XmlHandler.GLOBAL.fromXml(resp.substring(6))
    val bcspResp = new BCSPResp(sms)
    bcspResp.retCode shouldBe "0"
    bcspResp.retMsg shouldBe "Success!"
  }
}
