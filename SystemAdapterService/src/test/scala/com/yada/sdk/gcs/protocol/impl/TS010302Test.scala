package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import org.mockito.Mockito
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

import scala.xml.XML

/**
  * 某一期账单信息汇总查询
  */
class TS010302Test extends FlatSpec with Matchers with MockitoSugar {

  "TS010302" should "handle successful" in {
    val sessionID = "93c4399ad67d925fa40d0693adb0a222"
    val channelID = "WX01"
    val accountId = "001A0213064FF77E"
    val statementNo = "18"
    val resp =
      """<?xml version="1.0" encoding="UTF-8"?>
        |<GCS transactionID="010302" isRequest="false" isResponse="true">
        |    <system>
        |        <prop key="transactionCode" value="010302"/>
        |        <prop key="transactionNumber" value="0020160420175124"/>
        |        <prop key="txnTraceNumber" value="0012323533"/>
        |        <prop key="transactionSessionId" value="93c4399ad67d925fa40d0693adb0a222"/>
        |        <prop key="requestChannelId" value="WX01"/>
        |        <prop key="txnBankCode" value="003"/>
        |        <prop key="txnProvinceCode" value=""/>
        |        <prop key="txnBranchCode" value="00003"/>
        |        <prop key="txnCounterCode" value=""/>
        |        <prop key="txnTerminalCode" value=""/>
        |        <prop key="txnUserCode" value="wx0000000000"/>
        |        <prop key="localBankTxnRequestTime" value="17:49:12"/>
        |        <prop key="localBankTxnRequestDate" value="2016-04-20"/>
        |        <prop key="localBankTxnResponseTime" value="17:49:13"/>
        |        <prop key="localBankTxnResponseDate" value="2016-04-20"/>
        |        <prop key="bocBankTxnRequestTime" value="17:49:12"/>
        |        <prop key="bocBankTxnRequestDate" value="2016-04-20"/>
        |        <prop key="bocBankTxnResponseTime" value="17:49:13"/>
        |        <prop key="bocBankTxnResponseDate" value="2016-04-20"/>
        |        <prop key="returnCode" value="+GC00000"/>
        |        <prop key="returnMessage" value="Success"/>
        |    </system>
        |    <page key="RP010302">
        |        <prop key="periodStartDate" value="2022-09-11"/>
        |        <prop key="periodEndDate" value="2022-10-10"/>
        |        <prop key="currencyCode" value="CNY"/>
        |        <prop key="upPeriodBalance" value="+00000000011957330"/>
        |        <prop key="closingBalance" value="+00000000012122780"/>
        |        <prop key="closingMinPayment" value="+00000000003757000"/>
        |        <prop key="closingPastDue" value="+00000000011957330"/>
        |        <prop key="paymentDueDate" value="2022-10-30"/>
        |        <prop key="exchangeRateOnStatementDay" value=""/>
        |        <prop key="upPeriodPaymentAmount" value="+00000000000000000"/>
        |        <prop key="currPeriodDebitsAmount" value="+00000000000165450"/>
        |        <prop key="minPaymentAmount" value="+00000000012122780"/>
        |    </page>
        |</GCS>
      """.stripMargin
    val gcsClient = mock[GCSClient]
    val protocol = new TS010302(sessionID, channelID, statementNo, accountId)(gcsClient)
    val req = protocol.reqXML
    Mockito.when(gcsClient.send(org.mockito.Matchers.any())).thenReturn(resp)
    val reqXML = XML.loadString(req)
    reqXML \@ "transactionID" shouldBe "010302"
    reqXML \@ "isRequest" shouldBe "true"
    reqXML \@ "isResponse" shouldBe "false"
    reqXML \ "page" \@ "key" shouldBe "RQ010302"
    val reqMap = (reqXML \\ "prop" map (node => {
      node \@ "key" -> node \@ "value"
    })).toMap
    reqMap("txnTerminalCode") shouldBe ""
    reqMap("requestChannelId") shouldBe "WX01"
    reqMap("transactionSessionId") shouldBe "93c4399ad67d925fa40d0693adb0a222"
    reqMap("txnBankCode") shouldBe "003"
    reqMap("txnCounterCode") shouldBe ""
    reqMap("transactionCode") shouldBe "010302"
    reqMap("txnBranchCode") shouldBe "00003"
    reqMap("txnProvinceCode") shouldBe ""
    reqMap("txnUserCode") shouldBe "wx0000000000"
    reqMap("transactionNumber") should not be ""
    reqMap("accountId") shouldBe "001A0213064FF77E"
    reqMap("statementNo") shouldBe "18"

    val respObj = protocol.send
    respObj.systemValue("transactionCode") shouldBe "010302"
    respObj.systemValue("transactionNumber") shouldBe "0020160420175124"
    respObj.systemValue("txnTraceNumber") shouldBe "0012323533"
    respObj.systemValue("transactionSessionId") shouldBe "93c4399ad67d925fa40d0693adb0a222"
    respObj.systemValue("requestChannelId") shouldBe "WX01"
    respObj.systemValue("txnBankCode") shouldBe "003"
    respObj.systemValue("txnProvinceCode") shouldBe ""
    respObj.systemValue("txnBranchCode") shouldBe "00003"
    respObj.systemValue("txnCounterCode") shouldBe ""
    respObj.systemValue("txnTerminalCode") shouldBe ""
    respObj.systemValue("txnUserCode") shouldBe "wx0000000000"
    respObj.systemValue("localBankTxnRequestTime") shouldBe "17:49:12"
    respObj.systemValue("localBankTxnRequestDate") shouldBe "2016-04-20"
    respObj.systemValue("localBankTxnResponseTime") shouldBe "17:49:13"
    respObj.systemValue("localBankTxnResponseDate") shouldBe "2016-04-20"
    respObj.systemValue("bocBankTxnRequestTime") shouldBe "17:49:12"
    respObj.systemValue("bocBankTxnRequestDate") shouldBe "2016-04-20"
    respObj.systemValue("bocBankTxnResponseTime") shouldBe "17:49:13"
    respObj.systemValue("bocBankTxnResponseDate") shouldBe "2016-04-20"
    respObj.systemValue("returnCode") shouldBe "+GC00000"
    respObj.systemValue("returnMessage") shouldBe "Success"

    respObj.pageValue("periodStartDate") shouldBe "2022-09-11"
    respObj.pageValue("periodEndDate") shouldBe "2022-10-10"
    respObj.pageValue("currencyCode") shouldBe "CNY"
    respObj.pageValue("upPeriodBalance") shouldBe "+00000000011957330"
    respObj.pageValue("closingBalance") shouldBe "+00000000012122780"
    respObj.pageValue("closingMinPayment") shouldBe "+00000000003757000"
    respObj.pageValue("closingPastDue") shouldBe "+00000000011957330"
    respObj.pageValue("paymentDueDate") shouldBe "2022-10-30"
    respObj.pageValue("exchangeRateOnStatementDay") shouldBe ""
    respObj.pageValue("upPeriodPaymentAmount") shouldBe "+00000000000000000"
    respObj.pageValue("currPeriodDebitsAmount") shouldBe "+00000000000165450"
    respObj.pageValue("minPaymentAmount") shouldBe "+00000000012122780"
  }
}
