package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import org.mockito.Mockito
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

import scala.xml.XML

/**
  * 未出账单/已出账单某账期交易查询（带存入/支出）
  */
class TS010310Test extends FlatSpec with Matchers with MockitoSugar {

  "TS010310" should "handle successful" in {
    val sessionID = "93c4399ad67d925fa40d0693adb0a222"
    val channelID = "WX01"
    val cardNo = "5149580068840943"
    val currencyCode = "CNY"
    val queryType = "ALLT"
    val startNum = "1"
    val totalNum = "10"
    val startDate = "2022-09-11"
    val endDate = "2022-10-10"
    val resp =
      """<?xml version="1.0" encoding="UTF-8"?>
        |<GCS transactionID="010310" isRequest="false" isResponse="true">
        |    <system>
        |        <prop key="transactionCode" value="010310"/>
        |        <prop key="transactionNumber" value="0020160421091952"/>
        |        <prop key="txnTraceNumber" value="0012326851"/>
        |        <prop key="transactionSessionId" value="93c4399ad67d925fa40d0693adb0a222"/>
        |        <prop key="requestChannelId" value="WX01"/>
        |        <prop key="txnBankCode" value="003"/>
        |        <prop key="txnProvinceCode" value=""/>
        |        <prop key="txnBranchCode" value="00003"/>
        |        <prop key="txnCounterCode" value=""/>
        |        <prop key="txnTerminalCode" value=""/>
        |        <prop key="txnUserCode" value="wx0000000000"/>
        |        <prop key="localBankTxnRequestTime" value="09:17:40"/>
        |        <prop key="localBankTxnRequestDate" value="2016-04-21"/>
        |        <prop key="localBankTxnResponseTime" value="09:17:41"/>
        |        <prop key="localBankTxnResponseDate" value="2016-04-21"/>
        |        <prop key="bocBankTxnRequestTime" value="09:17:40"/>
        |        <prop key="bocBankTxnRequestDate" value="2016-04-21"/>
        |        <prop key="bocBankTxnResponseTime" value="09:17:41"/>
        |        <prop key="bocBankTxnResponseDate" value="2016-04-21"/>
        |        <prop key="returnCode" value="+GC00000"/>
        |        <prop key="returnMessage" value="Success"/>
        |    </system>
        |    <page key="RP010303">
        |        <list key="RP010052" entityKey="transList" size="1">
        |            <entity>
        |                <prop key="accountId" value="001A0213064FF77E"/>
        |                <prop key="acquirerBranchAndNetworkPointId" value="4110142001"/>
        |                <prop key="additionalSettlementMatching" value="               "/>
        |                <prop key="authoriztionCode" value="      "/>
        |                <prop key="cardAcceptorId" value="               "/>
        |                <prop key="cardNo" value="                            "/>
        |                <prop key="cardNoLastFourNumber" value=""/>
        |                <prop key="cycleNumber" value="31"/>
        |                <prop key="dateReleasedFromDispute" value=""/>
        |                <prop key="debitCreditCode" value="NMON"/>
        |                <prop key="deposit" value="+00000000000000000"/>
        |                <prop key="disputedMachineDate" value="1800-01-01"/>
        |                <prop key="expend" value="+00000000000000000"/>
        |                <prop key="ixsTransactionDetailId" value="1604191646421813"/>
        |                <prop key="mcc" value="    "/>
        |                <prop key="postingAccountId" value="001A0213064FF77E"/>
        |                <prop key="postingDate" value="2022-10-10"/>
        |                <prop key="schemeBillingAmount" value="+00000000000000000"/>
        |                <prop key="schemeBillingCurrencyCode" value="   "/>
        |                <prop key="settlementAmount" value="+00000000000165450"/>
        |                <prop key="settlementCurrencyCode" value="CNY"/>
        |                <prop key="transactionAmount" value="+00000000000165450"/>
        |                <prop key="transactionCurrencyCode" value="CNY"/>
        |                <prop key="transactionDate" value="2022-10-10"/>
        |                <prop key="transactionDescription" value="借记利息"/>
        |                <prop key="transactionId" value="15"/>
        |                <prop key="transactionProfileCode" value="8001"/>
        |                <prop key="transactionSourceCode" value="INTR"/>
        |                <prop key="transactionStatusCode" value="NONE"/>
        |                <prop key="transactionTime" value="16:46:42"/>
        |            </entity>
        |        </list>
        |        <prop key="isHaveNext" value="0"/>
        |    </page>
        |</GCS>
      """.stripMargin
    val gcsClient = mock[GCSClient]
    val protocol = new TS010310(sessionID, channelID, cardNo, currencyCode, queryType, startNum, totalNum, startDate, endDate)(gcsClient)
    val req = protocol.reqXML
    Mockito.when(gcsClient.send(org.mockito.Matchers.any())).thenReturn(resp)
    val reqXML = XML.loadString(req)
    reqXML \@ "transactionID" shouldBe "010310"
    reqXML \@ "isRequest" shouldBe "true"
    reqXML \@ "isResponse" shouldBe "false"
    reqXML \ "page" \@ "key" shouldBe "RQ010303"
    val reqMap = (reqXML \\ "prop" map (node => {
      node \@ "key" -> node \@ "value"
    })).toMap
    reqMap("txnTerminalCode") shouldBe ""
    reqMap("requestChannelId") shouldBe "WX01"
    reqMap("transactionSessionId") shouldBe "93c4399ad67d925fa40d0693adb0a222"
    reqMap("txnBankCode") shouldBe "003"
    reqMap("txnCounterCode") shouldBe ""
    reqMap("transactionCode") shouldBe "010310"
    reqMap("txnBranchCode") shouldBe "00003"
    reqMap("txnProvinceCode") shouldBe ""
    reqMap("txnUserCode") shouldBe "wx0000000000"
    reqMap("transactionNumber") should not be ""
    reqMap("startDate") shouldBe "2022-09-11"
    reqMap("currencyCode") shouldBe "CNY"
    reqMap("cardNo") shouldBe "5149580068840943"
    reqMap("startNum") shouldBe "1"
    reqMap("flag") shouldBe ""
    reqMap("maxAmount") shouldBe ""
    reqMap("minAmount") shouldBe ""
    reqMap("totalNum") shouldBe "10"
    reqMap("endDate") shouldBe "2022-10-10"
    reqMap("queryType") shouldBe "ALLT"

    val respObj = protocol.send
    respObj.systemValue("transactionCode") shouldBe "010310"
    respObj.systemValue("transactionNumber") shouldBe "0020160421091952"
    respObj.systemValue("txnTraceNumber") shouldBe "0012326851"
    respObj.systemValue("transactionSessionId") shouldBe "93c4399ad67d925fa40d0693adb0a222"
    respObj.systemValue("requestChannelId") shouldBe "WX01"
    respObj.systemValue("txnBankCode") shouldBe "003"
    respObj.systemValue("txnProvinceCode") shouldBe ""
    respObj.systemValue("txnBranchCode") shouldBe "00003"
    respObj.systemValue("txnCounterCode") shouldBe ""
    respObj.systemValue("txnTerminalCode") shouldBe ""
    respObj.systemValue("txnUserCode") shouldBe "wx0000000000"
    respObj.systemValue("localBankTxnRequestTime") shouldBe "09:17:40"
    respObj.systemValue("localBankTxnRequestDate") shouldBe "2016-04-21"
    respObj.systemValue("localBankTxnResponseTime") shouldBe "09:17:41"
    respObj.systemValue("localBankTxnResponseDate") shouldBe "2016-04-21"
    respObj.systemValue("bocBankTxnRequestTime") shouldBe "09:17:40"
    respObj.systemValue("bocBankTxnRequestDate") shouldBe "2016-04-21"
    respObj.systemValue("bocBankTxnResponseTime") shouldBe "09:17:41"
    respObj.systemValue("bocBankTxnResponseDate") shouldBe "2016-04-21"
    respObj.systemValue("returnCode") shouldBe "+GC00000"
    respObj.systemValue("returnMessage") shouldBe "Success"

    respObj.pageValue("isHaveNext") shouldBe "0"
    val respList = respObj.pageList
    respList.head("accountId") shouldBe "001A0213064FF77E"
    respList.head("acquirerBranchAndNetworkPointId") shouldBe "4110142001"
    respList.head("additionalSettlementMatching").trim shouldBe ""
    respList.head("authoriztionCode").trim shouldBe ""
    respList.head("cardAcceptorId").trim shouldBe ""
    respList.head("cardNo").trim shouldBe ""
    respList.head("cardNoLastFourNumber") shouldBe ""
    respList.head("cycleNumber") shouldBe "31"
    respList.head("dateReleasedFromDispute") shouldBe ""
    respList.head("debitCreditCode") shouldBe "NMON"
    respList.head("deposit") shouldBe "+00000000000000000"
    respList.head("disputedMachineDate") shouldBe "1800-01-01"
    respList.head("expend") shouldBe "+00000000000000000"
    respList.head("ixsTransactionDetailId") shouldBe "1604191646421813"
    respList.head("mcc").trim shouldBe ""
    respList.head("postingAccountId") shouldBe "001A0213064FF77E"
    respList.head("postingDate") shouldBe "2022-10-10"
    respList.head("schemeBillingAmount") shouldBe "+00000000000000000"
    respList.head("schemeBillingCurrencyCode").trim shouldBe ""
    respList.head("settlementAmount") shouldBe "+00000000000165450"
    respList.head("settlementCurrencyCode") shouldBe "CNY"
    respList.head("transactionAmount") shouldBe "+00000000000165450"
    respList.head("transactionCurrencyCode") shouldBe "CNY"
    respList.head("transactionDate") shouldBe "2022-10-10"
    respList.head("transactionDescription") shouldBe "借记利息"
    respList.head("transactionId") shouldBe "15"
    respList.head("transactionProfileCode") shouldBe "8001"
    respList.head("transactionSourceCode") shouldBe "INTR"
    respList.head("transactionStatusCode") shouldBe "NONE"
    respList.head("transactionTime") shouldBe "16:46:42"

  }
}
