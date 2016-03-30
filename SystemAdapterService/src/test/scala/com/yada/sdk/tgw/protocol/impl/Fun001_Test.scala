package com.yada.sdk.tgw.protocol.impl

import com.yada.sdk.tgw.protocol.TGWResp
import com.yada.sdk.tgw.xml.XmlHandler
import org.scalatest.{FlatSpec, Matchers}

import scala.xml.XML

class Fun001_Test extends FlatSpec with Matchers {
  "Fun001 reqXML" should "be formatted" in {
    val cardNo = "5149580650447008"
    val password = "111111"
    val fun001 = new Fun001(cardNo, password)
    val reqXML = XmlHandler.GLOBAL.toXml(fun001.toTxnReq)
    reqXML.startsWith("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>") shouldBe true
    val xml = XML.loadString(reqXML)
    (xml \ "head" \ "ACQUID").head.text shouldBe ""
    (xml \ "head" \ "TERMID").head.text shouldBe "0001"
    (xml \ "head" \ "PTXCOD").head.text shouldBe "109004"
    (xml \ "data" \ "CARDNO").head.text shouldBe cardNo
    (xml \ "data" \ "CHKPWD").head.text shouldBe "126198"
  }

  "Fun001 respXML" should "be parsed" in {
    val xml = "<txnrsp><head><PTXCOD>109004</PTXCOD><ACQUID></ACQUID><TERMID>0001</TERMID><ACQSEQ>30153428</ACQSEQ><RSPCOD>000000</RSPCOD></head></txnrsp>"
    val resp = new TGWResp(XmlHandler.GLOBAL.fromXML(xml))
    resp.rspCod shouldBe "000000"
    resp.respHeadValue("PTXCOD") shouldBe "109004"
    resp.respHeadValue("ACQUID") shouldBe ""
    resp.respHeadValue("TERMID") shouldBe "0001"
    resp.respHeadValue("ACQSEQ") shouldBe "30153428"
  }
}
