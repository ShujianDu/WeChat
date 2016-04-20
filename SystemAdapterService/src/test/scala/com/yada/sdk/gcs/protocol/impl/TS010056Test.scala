package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import org.mockito.Mockito
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

import scala.xml.XML

/**
  * 账单寄送方式修改
  */
class TS010056Test extends FlatSpec with Matchers with MockitoSugar {

  "TS010056" should "handle successful" in {
    val sessionID = "93c4399ad67d925fa40d0693adb0a222"
    val channelID = "WX01"
    val cardNo = "5149580068840943"
    val resp =
      """<?xml version="1.0" encoding="UTF-8"?>
        |<GCS transactionID="010056" isRequest="false" isResponse="true">
        |    <system>
        |        <prop key="transactionCode" value="010056"/>
        |        <prop key="transactionNumber" value="0020160420103443"/>
        |        <prop key="txnTraceNumber" value="0012177116"/>
        |        <prop key="transactionSessionId" value="93c4399ad67d925fa40d0693adb0a222"/>
        |        <prop key="requestChannelId" value="WX01"/>
        |        <prop key="txnBankCode" value="003"/>
        |        <prop key="txnProvinceCode" value=""/>
        |        <prop key="txnBranchCode" value="00003"/>
        |        <prop key="txnCounterCode" value=""/>
        |        <prop key="txnTerminalCode" value=""/>
        |        <prop key="txnUserCode" value="wx0000000000"/>
        |        <prop key="localBankTxnRequestTime" value="10:32:32"/>
        |        <prop key="localBankTxnRequestDate" value="2016-04-20"/>
        |        <prop key="localBankTxnResponseTime" value="10:32:33"/>
        |        <prop key="localBankTxnResponseDate" value="2016-04-20"/>
        |        <prop key="bocBankTxnRequestTime" value="10:32:32"/>
        |        <prop key="bocBankTxnRequestDate" value="2016-04-20"/>
        |        <prop key="bocBankTxnResponseTime" value="10:32:33"/>
        |        <prop key="bocBankTxnResponseDate" value="2016-04-20"/>
        |        <prop key="returnCode" value="+GC00000"/>
        |        <prop key="returnMessage" value="Success"/>
        |    </system>
        |    <page key="RP010101">
        |        <prop key="emailStatementOption" value="C"/>
        |    </page>
        |</GCS>
      """.stripMargin

    val protocol = new TS010056(sessionID, channelID, cardNo, "C")
    protocol.gcsClient = mock[GCSClient]
    val req = protocol.reqXML
    Mockito.when(protocol.gcsClient.send(org.mockito.Matchers.any())).thenReturn(resp)
    val reqXML = XML.loadString(req)
    reqXML \@ "transactionID" shouldBe "010056"
    reqXML \@ "isRequest" shouldBe "true"
    reqXML \@ "isResponse" shouldBe "false"
    reqXML \ "page" \@ "key" shouldBe "RP010002"
    val reqMap = (reqXML \\ "prop" map (node => {
      node \@ "key" -> node \@ "value"
    })).toMap
    reqMap("txnTerminalCode") shouldBe ""
    reqMap("requestChannelId") shouldBe "WX01"
    reqMap("transactionSessionId") shouldBe "93c4399ad67d925fa40d0693adb0a222"
    reqMap("txnBankCode") shouldBe "003"
    reqMap("txnCounterCode") shouldBe ""
    reqMap("transactionCode") shouldBe "010056"
    reqMap("txnBranchCode") shouldBe "00003"
    reqMap("txnProvinceCode") shouldBe ""
    reqMap("txnUserCode") shouldBe "wx0000000000"
    reqMap("transactionNumber") should not be ""
    reqMap("cardNo") shouldBe "5149580068840943"
    reqMap("billSendType") shouldBe "C"

    val respObj = protocol.send
    respObj.systemValue("transactionCode") shouldBe "010056"
    respObj.systemValue("transactionNumber") shouldBe "0020160420103443"
    respObj.systemValue("txnTraceNumber") shouldBe "0012177116"
    respObj.systemValue("transactionSessionId") shouldBe "93c4399ad67d925fa40d0693adb0a222"
    respObj.systemValue("requestChannelId") shouldBe "WX01"
    respObj.systemValue("txnBankCode") shouldBe "003"
    respObj.systemValue("txnProvinceCode") shouldBe ""
    respObj.systemValue("txnBranchCode") shouldBe "00003"
    respObj.systemValue("txnCounterCode") shouldBe ""
    respObj.systemValue("txnTerminalCode") shouldBe ""
    respObj.systemValue("txnUserCode") shouldBe "wx0000000000"
    respObj.systemValue("localBankTxnRequestTime") shouldBe "10:32:32"
    respObj.systemValue("localBankTxnRequestDate") shouldBe "2016-04-20"
    respObj.systemValue("localBankTxnResponseTime") shouldBe "10:32:33"
    respObj.systemValue("localBankTxnResponseDate") shouldBe "2016-04-20"
    respObj.systemValue("bocBankTxnRequestTime") shouldBe "10:32:32"
    respObj.systemValue("bocBankTxnRequestDate") shouldBe "2016-04-20"
    respObj.systemValue("bocBankTxnResponseTime") shouldBe "10:32:33"
    respObj.systemValue("bocBankTxnResponseDate") shouldBe "2016-04-20"
    respObj.systemValue("returnCode") shouldBe "+GC00000"
    respObj.systemValue("returnMessage") shouldBe "Success"
    respObj.pageValue("emailStatementOption") shouldBe "C"
  }
}
