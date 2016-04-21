package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import org.mockito.Mockito
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

import scala.xml.XML

/**
  * 账单周期查询
  */
class TS010301Test extends FlatSpec with Matchers with MockitoSugar {

  "TS010301" should "handle successful" in {
    val sessionID = "93c4399ad67d925fa40d0693adb0a222"
    val channelID = "WX01"
    val cardNo = "5149580068840943"
    val resp =
      """<?xml version="1.0" encoding="UTF-8"?>
        |<GCS transactionID="010301" isRequest="false" isResponse="true">
        |    <system>
        |        <prop key="transactionCode" value="010301"/>
        |        <prop key="transactionNumber" value="0020160420162752"/>
        |        <prop key="txnTraceNumber" value="0012322856"/>
        |        <prop key="transactionSessionId" value="93c4399ad67d925fa40d0693adb0a222"/>
        |        <prop key="requestChannelId" value="WX01"/>
        |        <prop key="txnBankCode" value="003"/>
        |        <prop key="txnProvinceCode" value=""/>
        |        <prop key="txnBranchCode" value="00003"/>
        |        <prop key="txnCounterCode" value=""/>
        |        <prop key="txnTerminalCode" value=""/>
        |        <prop key="txnUserCode" value="wx0000000000"/>
        |        <prop key="localBankTxnRequestTime" value="16:25:41"/>
        |        <prop key="localBankTxnRequestDate" value="2016-04-20"/>
        |        <prop key="localBankTxnResponseTime" value="16:25:44"/>
        |        <prop key="localBankTxnResponseDate" value="2016-04-20"/>
        |        <prop key="bocBankTxnRequestTime" value="16:25:41"/>
        |        <prop key="bocBankTxnRequestDate" value="2016-04-20"/>
        |        <prop key="bocBankTxnResponseTime" value="16:25:44"/>
        |        <prop key="bocBankTxnResponseDate" value="2016-04-20"/>
        |        <prop key="returnCode" value="+GC00000"/>
        |        <prop key="returnMessage" value="Success"/>
        |    </system>
        |    <page key="RP010301">
        |        <prop key="cardNo" value="5149580068840943"/>
        |        <list key="RP010302" entityKey="statementSummaryList" size="18">
        |            <entity>
        |                <prop key="accountId" value="001A0213064FF77E"/>
        |                <prop key="currencyCode" value="CNY"/>
        |                <prop key="periodEndDate" value="2022-10-10"/>
        |                <prop key="periodStartDate" value="2022-09-11"/>
        |                <prop key="statementNo" value="18"/>
        |            </entity>
        |            <entity>
        |                <prop key="accountId" value="001A0213064FF77E"/>
        |                <prop key="currencyCode" value="CNY"/>
        |                <prop key="periodEndDate" value="2022-09-10"/>
        |                <prop key="periodStartDate" value="2022-08-11"/>
        |                <prop key="statementNo" value="17"/>
        |            </entity>
        |            <entity>
        |                <prop key="accountId" value="001A0213064FF77E"/>
        |                <prop key="currencyCode" value="CNY"/>
        |                <prop key="periodEndDate" value="2022-08-10"/>
        |                <prop key="periodStartDate" value="2022-07-11"/>
        |                <prop key="statementNo" value="16"/>
        |            </entity>
        |            <entity>
        |                <prop key="accountId" value="001A0213064FF77E"/>
        |                <prop key="currencyCode" value="CNY"/>
        |                <prop key="periodEndDate" value="2022-07-10"/>
        |                <prop key="periodStartDate" value="2022-06-11"/>
        |                <prop key="statementNo" value="15"/>
        |            </entity>
        |            <entity>
        |                <prop key="accountId" value="001A0213064FF77E"/>
        |                <prop key="currencyCode" value="CNY"/>
        |                <prop key="periodEndDate" value="2022-06-10"/>
        |                <prop key="periodStartDate" value="2022-05-11"/>
        |                <prop key="statementNo" value="14"/>
        |            </entity>
        |            <entity>
        |                <prop key="accountId" value="001A0213064FF77E"/>
        |                <prop key="currencyCode" value="CNY"/>
        |                <prop key="periodEndDate" value="2022-05-10"/>
        |                <prop key="periodStartDate" value="2022-04-11"/>
        |                <prop key="statementNo" value="13"/>
        |            </entity>
        |            <entity>
        |                <prop key="accountId" value="001A0213064FF77E"/>
        |                <prop key="currencyCode" value="CNY"/>
        |                <prop key="periodEndDate" value="2022-04-10"/>
        |                <prop key="periodStartDate" value="2022-03-11"/>
        |                <prop key="statementNo" value="12"/>
        |            </entity>
        |            <entity>
        |                <prop key="accountId" value="001A0213064FF77E"/>
        |                <prop key="currencyCode" value="CNY"/>
        |                <prop key="periodEndDate" value="2022-03-10"/>
        |                <prop key="periodStartDate" value="2022-02-11"/>
        |                <prop key="statementNo" value="11"/>
        |            </entity>
        |            <entity>
        |                <prop key="accountId" value="001A0213064FF77E"/>
        |                <prop key="currencyCode" value="CNY"/>
        |                <prop key="periodEndDate" value="2022-02-10"/>
        |                <prop key="periodStartDate" value="2022-01-11"/>
        |                <prop key="statementNo" value="10"/>
        |            </entity>
        |            <entity>
        |                <prop key="accountId" value="001A0213064FF77E"/>
        |                <prop key="currencyCode" value="CNY"/>
        |                <prop key="periodEndDate" value="2022-01-10"/>
        |                <prop key="periodStartDate" value="2021-12-11"/>
        |                <prop key="statementNo" value="9"/>
        |            </entity>
        |            <entity>
        |                <prop key="accountId" value="001A0213064FF77E"/>
        |                <prop key="currencyCode" value="CNY"/>
        |                <prop key="periodEndDate" value="2021-12-10"/>
        |                <prop key="periodStartDate" value="2021-11-11"/>
        |                <prop key="statementNo" value="8"/>
        |            </entity>
        |            <entity>
        |                <prop key="accountId" value="001A0213064FF77E"/>
        |                <prop key="currencyCode" value="CNY"/>
        |                <prop key="periodEndDate" value="2021-11-10"/>
        |                <prop key="periodStartDate" value="2021-10-11"/>
        |                <prop key="statementNo" value="7"/>
        |            </entity>
        |            <entity>
        |                <prop key="accountId" value="001A0213064FF77E"/>
        |                <prop key="currencyCode" value="CNY"/>
        |                <prop key="periodEndDate" value="2021-10-10"/>
        |                <prop key="periodStartDate" value="2021-09-11"/>
        |                <prop key="statementNo" value="6"/>
        |            </entity>
        |            <entity>
        |                <prop key="accountId" value="001A0213064FF77E"/>
        |                <prop key="currencyCode" value="CNY"/>
        |                <prop key="periodEndDate" value="2021-09-10"/>
        |                <prop key="periodStartDate" value="2021-08-11"/>
        |                <prop key="statementNo" value="5"/>
        |            </entity>
        |            <entity>
        |                <prop key="accountId" value="001A0213064FF77E"/>
        |                <prop key="currencyCode" value="CNY"/>
        |                <prop key="periodEndDate" value="2021-08-10"/>
        |                <prop key="periodStartDate" value="2021-07-11"/>
        |                <prop key="statementNo" value="4"/>
        |            </entity>
        |            <entity>
        |                <prop key="accountId" value="001A0213064FF77E"/>
        |                <prop key="currencyCode" value="CNY"/>
        |                <prop key="periodEndDate" value="2021-07-10"/>
        |                <prop key="periodStartDate" value="2021-06-11"/>
        |                <prop key="statementNo" value="3"/>
        |            </entity>
        |            <entity>
        |                <prop key="accountId" value="001A0213064FF77E"/>
        |                <prop key="currencyCode" value="CNY"/>
        |                <prop key="periodEndDate" value="2021-06-10"/>
        |                <prop key="periodStartDate" value="2021-05-11"/>
        |                <prop key="statementNo" value="2"/>
        |            </entity>
        |            <entity>
        |                <prop key="accountId" value="001A0213064FF77E"/>
        |                <prop key="currencyCode" value="CNY"/>
        |                <prop key="periodEndDate" value="2021-05-10"/>
        |                <prop key="periodStartDate" value="2020-03-10"/>
        |                <prop key="statementNo" value="1"/>
        |            </entity>
        |        </list>
        |    </page>
        |</GCS>
      """.stripMargin
    val gcsClient = mock[GCSClient]
    val protocol = new TS010301(sessionID, channelID, cardNo)(gcsClient)
    val req = protocol.reqXML
    Mockito.when(gcsClient.send(org.mockito.Matchers.any())).thenReturn(resp)
    val reqXML = XML.loadString(req)
    reqXML \@ "transactionID" shouldBe "010301"
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
    reqMap("transactionCode") shouldBe "010301"
    reqMap("txnBranchCode") shouldBe "00003"
    reqMap("txnProvinceCode") shouldBe ""
    reqMap("txnUserCode") shouldBe "wx0000000000"
    reqMap("transactionNumber") should not be ""
    reqMap("cardNo") shouldBe "5149580068840943"

    val respObj = protocol.send
    respObj.systemValue("transactionCode") shouldBe "010301"
    respObj.systemValue("transactionNumber") shouldBe "0020160420162752"
    respObj.systemValue("txnTraceNumber") shouldBe "0012322856"
    respObj.systemValue("transactionSessionId") shouldBe "93c4399ad67d925fa40d0693adb0a222"
    respObj.systemValue("requestChannelId") shouldBe "WX01"
    respObj.systemValue("txnBankCode") shouldBe "003"
    respObj.systemValue("txnProvinceCode") shouldBe ""
    respObj.systemValue("txnBranchCode") shouldBe "00003"
    respObj.systemValue("txnCounterCode") shouldBe ""
    respObj.systemValue("txnTerminalCode") shouldBe ""
    respObj.systemValue("txnUserCode") shouldBe "wx0000000000"
    respObj.systemValue("localBankTxnRequestTime") shouldBe "16:25:41"
    respObj.systemValue("localBankTxnRequestDate") shouldBe "2016-04-20"
    respObj.systemValue("localBankTxnResponseTime") shouldBe "16:25:44"
    respObj.systemValue("localBankTxnResponseDate") shouldBe "2016-04-20"
    respObj.systemValue("bocBankTxnRequestTime") shouldBe "16:25:41"
    respObj.systemValue("bocBankTxnRequestDate") shouldBe "2016-04-20"
    respObj.systemValue("bocBankTxnResponseTime") shouldBe "16:25:44"
    respObj.systemValue("bocBankTxnResponseDate") shouldBe "2016-04-20"
    respObj.systemValue("returnCode") shouldBe "+GC00000"
    respObj.systemValue("returnMessage") shouldBe "Success"

    respObj.pageValue("cardNo") shouldBe "5149580068840943"
    val respList = respObj.pageList
    respList.head("accountId") shouldBe "001A0213064FF77E"
    respList.head("currencyCode") shouldBe "CNY"
    respList.head("periodEndDate") shouldBe "2022-10-10"
    respList.head("periodStartDate") shouldBe "2022-09-11"
    respList.head("statementNo") shouldBe "18"

    respList(1)("accountId") shouldBe "001A0213064FF77E"
    respList(1)("currencyCode") shouldBe "CNY"
    respList(1)("periodEndDate") shouldBe "2022-09-10"
    respList(1)("periodStartDate") shouldBe "2022-08-11"
    respList(1)("statementNo") shouldBe "17"

    respList(2)("accountId") shouldBe "001A0213064FF77E"
    respList(2)("currencyCode") shouldBe "CNY"
    respList(2)("periodEndDate") shouldBe "2022-08-10"
    respList(2)("periodStartDate") shouldBe "2022-07-11"
    respList(2)("statementNo") shouldBe "16"
    // ...
  }
}
