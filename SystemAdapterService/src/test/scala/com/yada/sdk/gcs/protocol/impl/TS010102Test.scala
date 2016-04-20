package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import org.mockito.Mockito
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

import scala.xml.XML

/**
  * 账单寄送方式修改
  */
class TS010102Test extends FlatSpec with Matchers with MockitoSugar {

  "TS010102" should "handle successful" in {
    val sessionID = "93c4399ad67d925fa40d0693adb0a222"
    val channelID = "WX01"
    val cardNo = "5149580068840943"
    val resp =
      """<?xml version="1.0" encoding="UTF-8"?>
        |<GCS transactionID="010102" isRequest="false" isResponse="true">
        |    <system>
        |        <prop key="transactionCode" value="010102"/>
        |        <prop key="transactionNumber" value="0020160420140537"/>
        |        <prop key="txnTraceNumber" value="0012233735"/>
        |        <prop key="transactionSessionId" value="93c4399ad67d925fa40d0693adb0a222"/>
        |        <prop key="requestChannelId" value="WX01"/>
        |        <prop key="txnBankCode" value="003"/>
        |        <prop key="txnProvinceCode" value=""/>
        |        <prop key="txnBranchCode" value="00003"/>
        |        <prop key="txnCounterCode" value=""/>
        |        <prop key="txnTerminalCode" value=""/>
        |        <prop key="txnUserCode" value="wx0000000000"/>
        |        <prop key="localBankTxnRequestTime" value="14:03:26"/>
        |        <prop key="localBankTxnRequestDate" value="2016-04-20"/>
        |        <prop key="localBankTxnResponseTime" value="14:03:26"/>
        |        <prop key="localBankTxnResponseDate" value="2016-04-20"/>
        |        <prop key="bocBankTxnRequestTime" value="14:03:26"/>
        |        <prop key="bocBankTxnRequestDate" value="2016-04-20"/>
        |        <prop key="bocBankTxnResponseTime" value="14:03:26"/>
        |        <prop key="bocBankTxnResponseDate" value="2016-04-20"/>
        |        <prop key="returnCode" value="+GC00000"/>
        |        <prop key="returnMessage" value="Success"/>
        |    </system>
        |    <page key="RP010102">
        |        <list key="RP010101" entityKey="accountList" size="2">
        |            <entity>
        |                <prop key="accountId" value="001A0213064FF77E"/>
        |                <prop key="accountRefNo" value="2861695039487302"/>
        |                <prop key="automaticStatusCode" value=""/>
        |                <prop key="automaticStatusSetDate" value=""/>
        |                <prop key="currencyCode" value="CNY"/>
        |                <prop key="institutionId" value="BOCK"/>
        |                <prop key="issuingBranchId" value=""/>
        |                <prop key="manualStatusCode" value="NORM"/>
        |                <prop key="manualStatusSetDate" value=""/>
        |                <prop key="productType" value="PRMC"/>
        |            </entity>
        |            <entity>
        |                <prop key="accountId" value="001A0213064FF780"/>
        |                <prop key="accountRefNo" value="7821440695701839"/>
        |                <prop key="automaticStatusCode" value=""/>
        |                <prop key="automaticStatusSetDate" value=""/>
        |                <prop key="currencyCode" value="USD"/>
        |                <prop key="institutionId" value="BOCK"/>
        |                <prop key="issuingBranchId" value=""/>
        |                <prop key="manualStatusCode" value="NORM"/>
        |                <prop key="manualStatusSetDate" value=""/>
        |                <prop key="productType" value="PRMC"/>
        |            </entity>
        |        </list>
        |    </page>
        |</GCS>
      """.stripMargin
    val gcsClient = mock[GCSClient]
    val protocol = new TS010102(sessionID, channelID, cardNo)(gcsClient)
    val req = protocol.reqXML
    Mockito.when(gcsClient.send(org.mockito.Matchers.any())).thenReturn(resp)
    val reqXML = XML.loadString(req)
    reqXML \@ "transactionID" shouldBe "010102"
    reqXML \@ "isRequest" shouldBe "true"
    reqXML \@ "isResponse" shouldBe "false"
    reqXML \ "page" \@ "key" shouldBe "RQ010101"
    val reqMap = (reqXML \\ "prop" map (node => {
      node \@ "key" -> node \@ "value"
    })).toMap
    reqMap("txnTerminalCode") shouldBe ""
    reqMap("requestChannelId") shouldBe "WX01"
    reqMap("transactionSessionId") shouldBe "93c4399ad67d925fa40d0693adb0a222"
    reqMap("txnBankCode") shouldBe "003"
    reqMap("txnCounterCode") shouldBe ""
    reqMap("transactionCode") shouldBe "010102"
    reqMap("txnBranchCode") shouldBe "00003"
    reqMap("txnProvinceCode") shouldBe ""
    reqMap("txnUserCode") shouldBe "wx0000000000"
    reqMap("transactionNumber") should not be ""
    reqMap("cardNo") shouldBe "5149580068840943"
    reqMap("currencyCode") shouldBe ""

    val respObj = protocol.send
    respObj.systemValue("transactionCode") shouldBe "010102"
    respObj.systemValue("transactionNumber") shouldBe "0020160420140537"
    respObj.systemValue("txnTraceNumber") shouldBe "0012233735"
    respObj.systemValue("transactionSessionId") shouldBe "93c4399ad67d925fa40d0693adb0a222"
    respObj.systemValue("requestChannelId") shouldBe "WX01"
    respObj.systemValue("txnBankCode") shouldBe "003"
    respObj.systemValue("txnProvinceCode") shouldBe ""
    respObj.systemValue("txnBranchCode") shouldBe "00003"
    respObj.systemValue("txnCounterCode") shouldBe ""
    respObj.systemValue("txnTerminalCode") shouldBe ""
    respObj.systemValue("txnUserCode") shouldBe "wx0000000000"
    respObj.systemValue("localBankTxnRequestTime") shouldBe "14:03:26"
    respObj.systemValue("localBankTxnRequestDate") shouldBe "2016-04-20"
    respObj.systemValue("localBankTxnResponseTime") shouldBe "14:03:26"
    respObj.systemValue("localBankTxnResponseDate") shouldBe "2016-04-20"
    respObj.systemValue("bocBankTxnRequestTime") shouldBe "14:03:26"
    respObj.systemValue("bocBankTxnRequestDate") shouldBe "2016-04-20"
    respObj.systemValue("bocBankTxnResponseTime") shouldBe "14:03:26"
    respObj.systemValue("bocBankTxnResponseDate") shouldBe "2016-04-20"
    respObj.systemValue("returnCode") shouldBe "+GC00000"
    respObj.systemValue("returnMessage") shouldBe "Success"

    val respList = respObj.pageList
    respList.head("accountId") shouldBe "001A0213064FF77E"
    respList.head("accountRefNo") shouldBe "2861695039487302"
    respList.head("automaticStatusCode") shouldBe ""
    respList.head("automaticStatusSetDate") shouldBe ""
    respList.head("currencyCode") shouldBe "CNY"
    respList.head("institutionId") shouldBe "BOCK"
    respList.head("issuingBranchId") shouldBe ""
    respList.head("manualStatusCode") shouldBe "NORM"
    respList.head("manualStatusSetDate") shouldBe ""
    respList.head("productType") shouldBe "PRMC"

    respList.last("accountId") shouldBe "001A0213064FF780"
    respList.last("accountRefNo") shouldBe "7821440695701839"
    respList.last("automaticStatusCode") shouldBe ""
    respList.last("automaticStatusSetDate") shouldBe ""
    respList.last("currencyCode") shouldBe "USD"
    respList.last("institutionId") shouldBe "BOCK"
    respList.last("issuingBranchId") shouldBe ""
    respList.last("manualStatusCode") shouldBe "NORM"
    respList.last("manualStatusSetDate") shouldBe ""
    respList.last("productType") shouldBe "PRMC"
  }
}
