package com.yada.sdk.point.protocol

import com.yada.sdk.point.IPointClient
import com.yada.sdk.point.protocol.impl.{P0001_GetBalance, P0004_GetECIF}
import org.mockito.Mockito
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

import scala.xml.XML

/**
  * 根据客户卡号查询客户的ECIF号
  */
class P0001_GetBalance_Test extends FlatSpec with Matchers with MockitoSugar {
  "P0004_GetECIF" should "handle successful" in {
    val client = mock[IPointClient]
    val ecifNo = "TC0008117397"
    Mockito.when(client.send(org.mockito.Matchers.anyString())).thenReturn(
      """<?xml version="1.0" encoding="ISO-8859-1"?>
        |<message>
        |  <head>
        |    <Version>1</Version>
        |    <TranCode>0001</TranCode>
        |    <SerialNo>0428085429</SerialNo>
        |    <ChannelCode>05</ChannelCode>
        |    <TranDate>2016/04/28</TranDate>
        |    <TranTime>08:56:04</TranTime>
        |    <RtnCode>0000</RtnCode>
        |    <RtnMsg>成功</RtnMsg>
        |  </head>
        |  <body>
        |    <TotalPoint>43598.00</TotalPoint>
        |    <AvailPoint>43598.00</AvailPoint>
        |    <CustLevel>10</CustLevel>
        |    <MarketFlag></MarketFlag>
        |    <BranchBank>中国银行总行</BranchBank>
        |    <VipTotalPoint>0.00</VipTotalPoint>
        |    <VipAvailPoint>0.00</VipAvailPoint>
        |    <VipSign>0</VipSign>
        |  </body>
        |</message>
      """.stripMargin)
    val protocol = new P0001_GetBalance(ecifNo)(client)
    val reqXML = XML.loadString(protocol.toXml)
    val resp = protocol.send

    (reqXML \ "head" \ "TranCode").head.text shouldBe "0001"
    (reqXML \ "head" \ "Version").head.text shouldBe "1"
    (reqXML \ "head" \ "TranDate").head.text shouldBe protocol.tranDate
    (reqXML \ "head" \ "ChannelCode").head.text shouldBe "05"
    (reqXML \ "head" \ "SerialNo").head.text shouldBe protocol.serialNo
    (reqXML \ "head" \ "TranTime").head.text shouldBe protocol.tranTime
    (reqXML \ "body" \ "EcifNo").head.text shouldBe "TC0008117397"

    resp.respHeadValue("Version") shouldBe "1"
    resp.respHeadValue("TranCode") shouldBe "0001"
    resp.respHeadValue("SerialNo") shouldBe "0428085429"
    resp.respHeadValue("ChannelCode") shouldBe "05"
    resp.respHeadValue("TranDate") shouldBe "2016/04/28"
    resp.respHeadValue("TranTime") shouldBe "08:56:04"
    resp.respHeadValue("RtnCode") shouldBe "0000"
    resp.respHeadValue("RtnMsg") shouldBe "成功"
    resp.respBodyValue("TotalPoint") shouldBe "43598.00"
    resp.respBodyValue("AvailPoint") shouldBe "43598.00"
    resp.respBodyValue("CustLevel") shouldBe "10"
    resp.respBodyValue("MarketFlag") shouldBe ""
    resp.respBodyValue("BranchBank") shouldBe "中国银行总行"
    resp.respBodyValue("VipTotalPoint") shouldBe "0.00"
    resp.respBodyValue("VipAvailPoint") shouldBe "0.00"
    resp.respBodyValue("VipSign") shouldBe "0"
  }
}
