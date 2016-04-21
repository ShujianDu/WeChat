package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import org.mockito.Mockito
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

import scala.xml.XML

/**
  * 根据卡号查询所有客户信息和卡信息
  */
class TS011007Test extends FlatSpec with Matchers with MockitoSugar {

  "TS011007" should "handle successful" in {
    val sessionID = "93c4399ad67d925fa40d0693adb0a222"
    val channelID = "WX01"
    val cardNo = "5149580068840943"
    val currencyCode = "CNY"
    val startNumber = "1"
    val selectNumber = "10"
    val resp =
      """<?xml version="1.0" encoding="UTF-8"?>
        |<GCS transactionID="011007" isRequest="false" isResponse="true">
        |    <system>
        |        <prop key="transactionCode" value="011007"/>
        |        <prop key="transactionNumber" value="0020160421142036"/>
        |        <prop key="txnTraceNumber" value="0012335645"/>
        |        <prop key="transactionSessionId" value="93c4399ad67d925fa40d0693adb0a222"/>
        |        <prop key="requestChannelId" value="WX01"/>
        |        <prop key="txnBankCode" value="003"/>
        |        <prop key="txnProvinceCode" value=""/>
        |        <prop key="txnBranchCode" value="00003"/>
        |        <prop key="txnCounterCode" value=""/>
        |        <prop key="txnTerminalCode" value=""/>
        |        <prop key="txnUserCode" value="wx0000000000"/>
        |        <prop key="localBankTxnRequestTime" value="14:18:24"/>
        |        <prop key="localBankTxnRequestDate" value="2016-04-21"/>
        |        <prop key="localBankTxnResponseTime" value="14:18:24"/>
        |        <prop key="localBankTxnResponseDate" value="2016-04-21"/>
        |        <prop key="bocBankTxnRequestTime" value="14:18:24"/>
        |        <prop key="bocBankTxnRequestDate" value="2016-04-21"/>
        |        <prop key="bocBankTxnResponseTime" value="14:18:24"/>
        |        <prop key="bocBankTxnResponseDate" value="2016-04-21"/>
        |        <prop key="returnCode" value="+GC00000"/>
        |        <prop key="returnMessage" value="Success"/>
        |    </system>
        |    <page key="RP011007">
        |        <prop key="transactionNumber" value="0"/>
        |        <prop key="isFollowUp" value="0"/>
        |    </page>
        |</GCS>
      """.stripMargin
    val gcsClient = mock[GCSClient]
    val protocol = new TS011007(sessionID, channelID, cardNo, currencyCode, startNumber, selectNumber)(gcsClient)
    val req = protocol.reqXML
    Mockito.when(gcsClient.send(org.mockito.Matchers.any())).thenReturn(resp)
    val reqXML = XML.loadString(req)
    reqXML \@ "transactionID" shouldBe "011007"
    reqXML \@ "isRequest" shouldBe "true"
    reqXML \@ "isResponse" shouldBe "false"
    reqXML \ "page" \@ "key" shouldBe "RQ011007"
    val reqMap = (reqXML \\ "prop" map (node => {
      node \@ "key" -> node \@ "value"
    })).toMap
    reqMap("txnTerminalCode") shouldBe ""
    reqMap("requestChannelId") shouldBe "WX01"
    reqMap("transactionSessionId") shouldBe "93c4399ad67d925fa40d0693adb0a222"
    reqMap("txnBankCode") shouldBe "003"
    reqMap("txnCounterCode") shouldBe ""
    reqMap("transactionCode") shouldBe "011007"
    reqMap("txnBranchCode") shouldBe "00003"
    reqMap("txnProvinceCode") shouldBe ""
    reqMap("txnUserCode") shouldBe "wx0000000000"
    reqMap("transactionNumber") should not be ""
    reqMap("cardNo") shouldBe "5149580068840943"
    reqMap("currencyCode") shouldBe "CNY"
    reqMap("transactionType") shouldBe "UNSM"
    reqMap("selectNumber") shouldBe "10"
    reqMap("startNumber") shouldBe "1"

    val respObj = protocol.send
    respObj.systemValue("transactionCode") shouldBe "011007"
    respObj.systemValue("transactionNumber") shouldBe "0020160421142036"
    respObj.systemValue("txnTraceNumber") shouldBe "0012335645"
    respObj.systemValue("transactionSessionId") shouldBe "93c4399ad67d925fa40d0693adb0a222"
    respObj.systemValue("requestChannelId") shouldBe "WX01"
    respObj.systemValue("txnBankCode") shouldBe "003"
    respObj.systemValue("txnProvinceCode") shouldBe ""
    respObj.systemValue("txnBranchCode") shouldBe "00003"
    respObj.systemValue("txnCounterCode") shouldBe ""
    respObj.systemValue("txnTerminalCode") shouldBe ""
    respObj.systemValue("txnUserCode") shouldBe "wx0000000000"
    respObj.systemValue("localBankTxnRequestTime") shouldBe "14:18:24"
    respObj.systemValue("localBankTxnRequestDate") shouldBe "2016-04-21"
    respObj.systemValue("localBankTxnResponseTime") shouldBe "14:18:24"
    respObj.systemValue("localBankTxnResponseDate") shouldBe "2016-04-21"
    respObj.systemValue("bocBankTxnRequestTime") shouldBe "14:18:24"
    respObj.systemValue("bocBankTxnRequestDate") shouldBe "2016-04-21"
    respObj.systemValue("bocBankTxnResponseTime") shouldBe "14:18:24"
    respObj.systemValue("bocBankTxnResponseDate") shouldBe "2016-04-21"
    respObj.systemValue("returnCode") shouldBe "+GC00000"
    respObj.systemValue("returnMessage") shouldBe "Success"

    respObj.pageValue("transactionNumber") shouldBe "0"
    respObj.pageValue("isFollowUp") shouldBe "0"
  }
}
