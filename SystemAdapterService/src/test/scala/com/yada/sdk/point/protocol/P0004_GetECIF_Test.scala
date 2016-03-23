package com.yada.sdk.point.protocol

import com.yada.sdk.point.protocol.impl.P0004_GetECIF
import com.yada.sdk.point.xml.{XmlHandler, IPointClient}
import org.mockito.Mockito
import org.scalatest.{Matchers, FlatSpec}

/**
  * 根据客户卡号查询客户的ECIF号
  */
class P0004_GetECIF_Test extends FlatSpec with Matchers {
  "P0004_GetECIF response xml" should "be parsed successful" in {
    val respXML = "000417<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<message>\n  <head>\n    <Version>1</Version>\n    <TranCode>0004</TranCode>\n    <SerialNo>0323151747</SerialNo>\n    <ChannelCode>05</ChannelCode>\n    <TranDate>2016/03/23</TranDate>\n    <TranTime>15:17:31</TranTime>\n    <RtnCode>0000</RtnCode>\n    <RtnMsg>成功</RtnMsg>\n  </head>\n  <body>\n    <EcifNo>TC0008106611</EcifNo>\n    <CardType>C0001</CardType>\n  </body>\n</message>".substring(6)
    val respMsg = XmlHandler.GLOBAL.fromXML(respXML)
    val resp = new PointResp(respMsg)
    resp.rtnCode shouldBe "0000"
    resp.rtnMsg shouldBe "成功"
    resp.respHeadValue("Version") shouldBe "1"
    resp.respHeadValue("TranCode") shouldBe "0004"
    resp.respHeadValue("SerialNo") shouldBe "0323151747"
    resp.respHeadValue("ChannelCode") shouldBe "05"
    resp.respHeadValue("TranDate") shouldBe "2016/03/23"
    resp.respHeadValue("TranTime") shouldBe "15:17:31"
    resp.respBodyValue("EcifNo") shouldBe "TC0008106611"
    resp.respBodyValue("CardType") shouldBe "C0001"
  }
}
