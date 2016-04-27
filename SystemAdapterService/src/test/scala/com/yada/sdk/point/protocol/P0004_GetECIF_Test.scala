package com.yada.sdk.point.protocol

import com.yada.sdk.point.IPointClient
import com.yada.sdk.point.protocol.impl.P0004_GetECIF
import org.mockito.Mockito
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import scala.xml.XML

/**
  * 根据客户卡号查询客户的ECIF号
  */
class P0004_GetECIF_Test extends FlatSpec with Matchers with MockitoSugar {
  "P0004_GetECIF" should "handle successful" in {
    val client = mock[IPointClient]
    val cardNo = "5149580068840943"
    Mockito.when(client.send(org.mockito.Matchers.anyString())).thenReturn(
      """<?xml version="1.0" encoding="ISO-8859-1"?>
        |<message>
        |  <head>
        |    <Version>1</Version>
        |    <TranCode>0004</TranCode>
        |    <SerialNo>0427152019</SerialNo>
        |    <ChannelCode>05</ChannelCode>
        |    <TranDate>2016/04/27</TranDate>
        |    <TranTime>15:21:54</TranTime>
        |    <RtnCode>0000</RtnCode>
        |    <RtnMsg>成功</RtnMsg>
        |  </head>
        |  <body>
        |    <EcifNo>TC0008117397</EcifNo>
        |    <CardType>C0001</CardType>
        |  </body>
        |</message>
      """.stripMargin)
    val protocol = new P0004_GetECIF(cardNo)(client)
    val reqXML = XML.loadString(protocol.toXml)
    val resp = protocol.send

    (reqXML \ "head" \ "TranCode").head.text shouldBe "0004"
    (reqXML \ "head" \ "Version").head.text shouldBe "1"
    (reqXML \ "head" \ "TranDate").head.text shouldBe protocol.tranDate
    (reqXML \ "head" \ "ChannelCode").head.text shouldBe "05"
    (reqXML \ "head" \ "SerialNo").head.text shouldBe protocol.serialNo
    (reqXML \ "head" \ "TranTime").head.text shouldBe protocol.tranTime
    (reqXML \ "body" \ "CardNo").head.text shouldBe "5149580068840943"

    resp.respHeadValue("Version") shouldBe "1"
    resp.respHeadValue("TranCode") shouldBe "0004"
    resp.respHeadValue("SerialNo") shouldBe "0427152019"
    resp.respHeadValue("ChannelCode") shouldBe "05"
    resp.respHeadValue("TranDate") shouldBe "2016/04/27"
    resp.respHeadValue("TranTime") shouldBe "15:21:54"
    resp.respHeadValue("RtnCode") shouldBe "0000"
    resp.respHeadValue("RtnMsg") shouldBe "成功"
    resp.respBodyValue("EcifNo") shouldBe "TC0008117397"
    resp.respBodyValue("CardType") shouldBe "C0001"
  }
}
