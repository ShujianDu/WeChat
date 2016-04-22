package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import org.mockito.Mockito
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

import scala.xml.XML

/**
  * 查询余额
  */
class TS410103Test extends FlatSpec with Matchers with MockitoSugar {

  "TS410103" should "handle successful" in {
    val sessionID = "93c4399ad67d925fa40d0693adb0a222"
    val channelID = "WX01"
    val cardNo = "5149580068840943"
    val currencyCode = "CNY"
    val resp =
      """<?xml version="1.0" encoding="UTF-8"?>
        |<GCS transactionID="410103" isRequest="false" isResponse="true">
        |    <system>
        |        <prop key="transactionCode" value="410103"/>
        |        <prop key="transactionNumber" value="0020160422183449"/>
        |        <prop key="txnTraceNumber" value="0012352140"/>
        |        <prop key="transactionSessionId" value="93c4399ad67d925fa40d0693adb0a222"/>
        |        <prop key="requestChannelId" value="WX01"/>
        |        <prop key="txnBankCode" value="003"/>
        |        <prop key="txnProvinceCode" value=""/>
        |        <prop key="txnBranchCode" value="00003"/>
        |        <prop key="txnCounterCode" value=""/>
        |        <prop key="txnTerminalCode" value=""/>
        |        <prop key="txnUserCode" value="wx0000000000"/>
        |        <prop key="localBankTxnRequestTime" value="18:32:38"/>
        |        <prop key="localBankTxnRequestDate" value="2016-04-22"/>
        |        <prop key="localBankTxnResponseTime" value="18:32:38"/>
        |        <prop key="localBankTxnResponseDate" value="2016-04-22"/>
        |        <prop key="bocBankTxnRequestTime" value="18:32:38"/>
        |        <prop key="bocBankTxnRequestDate" value="2016-04-22"/>
        |        <prop key="bocBankTxnResponseTime" value="18:32:38"/>
        |        <prop key="bocBankTxnResponseDate" value="2016-04-22"/>
        |        <prop key="returnCode" value="+GC00000"/>
        |        <prop key="returnMessage" value="Success"/>
        |    </system>
        |    <page key="RP010054">
        |        <prop key="cardNo" value="5149580068840943"/>
        |        <prop key="currencyCode" value="CNY"/>
        |        <prop key="currAccountAmount" value="-00000000012296250"/>
        |        <prop key="loanBalanceLimit" value="-00000000012296250"/>
        |        <prop key="periodAvailbleCreditLimit" value="+00000000007703750"/>
        |        <prop key="wholeCreditLimit" value="+00000000020000000"/>
        |        <prop key="preCashAdvanceCreditLimit" value="+00000000006000000"/>
        |        <prop key="instalmentPlanAvailableLimit" value="+00000000007703750"/>
        |    </page>
        |</GCS>
      """.stripMargin
    val gcsClient = mock[GCSClient]
    val protocol = new TS410103(sessionID, channelID, cardNo, currencyCode)(gcsClient)
    val req = protocol.reqXML
    Mockito.when(gcsClient.send(org.mockito.Matchers.any())).thenReturn(resp)
    val reqXML = XML.loadString(req)
    reqXML \@ "transactionID" shouldBe "410103"
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
    reqMap("transactionCode") shouldBe "410103"
    reqMap("txnBranchCode") shouldBe "00003"
    reqMap("txnProvinceCode") shouldBe ""
    reqMap("txnUserCode") shouldBe "wx0000000000"
    reqMap("transactionNumber") should not be ""
    reqMap("cardNo") shouldBe "5149580068840943"
    reqMap("currencyCode") shouldBe "CNY"

    val respObj = protocol.send
    respObj.systemValue("transactionCode") shouldBe "410103"
    respObj.systemValue("transactionNumber") shouldBe "0020160422183449"
    respObj.systemValue("txnTraceNumber") shouldBe "0012352140"
    respObj.systemValue("transactionSessionId") shouldBe "93c4399ad67d925fa40d0693adb0a222"
    respObj.systemValue("requestChannelId") shouldBe "WX01"
    respObj.systemValue("txnBankCode") shouldBe "003"
    respObj.systemValue("txnProvinceCode") shouldBe ""
    respObj.systemValue("txnBranchCode") shouldBe "00003"
    respObj.systemValue("txnCounterCode") shouldBe ""
    respObj.systemValue("txnTerminalCode") shouldBe ""
    respObj.systemValue("txnUserCode") shouldBe "wx0000000000"
    respObj.systemValue("localBankTxnRequestTime") shouldBe "18:32:38"
    respObj.systemValue("localBankTxnRequestDate") shouldBe "2016-04-22"
    respObj.systemValue("localBankTxnResponseTime") shouldBe "18:32:38"
    respObj.systemValue("localBankTxnResponseDate") shouldBe "2016-04-22"
    respObj.systemValue("bocBankTxnRequestTime") shouldBe "18:32:38"
    respObj.systemValue("bocBankTxnRequestDate") shouldBe "2016-04-22"
    respObj.systemValue("bocBankTxnResponseTime") shouldBe "18:32:38"
    respObj.systemValue("bocBankTxnResponseDate") shouldBe "2016-04-22"
    respObj.systemValue("returnCode") shouldBe "+GC00000"
    respObj.systemValue("returnMessage") shouldBe "Success"

    respObj.pageValue("cardNo") shouldBe "5149580068840943"
    respObj.pageValue("currencyCode") shouldBe "CNY"
    respObj.pageValue("currAccountAmount") shouldBe "-00000000012296250"
    respObj.pageValue("loanBalanceLimit") shouldBe "-00000000012296250"
    respObj.pageValue("periodAvailbleCreditLimit") shouldBe "+00000000007703750"
    respObj.pageValue("wholeCreditLimit") shouldBe "+00000000020000000"
    respObj.pageValue("preCashAdvanceCreditLimit") shouldBe "+00000000006000000"
    respObj.pageValue("instalmentPlanAvailableLimit") shouldBe "+00000000007703750"

  }
}
