package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import org.mockito.Mockito
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

import scala.xml.XML

/**
  * 历史分期查询
  */
class TS011021Test extends FlatSpec with Matchers with MockitoSugar {

  "TS011021" should "handle successful" in {
    val sessionID = "93c4399ad67d925fa40d0693adb0a222"
    val channelID = "WX01"
    val cardNo = "5149580068840943"
    val startNumber = "1"
    val selectNumber = "10"
    val resp =
      """<?xml version="1.0" encoding="UTF-8"?>
        |<GCS transactionID="011021" isRequest="false" isResponse="true">
        |    <system>
        |        <prop key="transactionCode" value="011021"/>
        |        <prop key="transactionNumber" value="0020160421145002"/>
        |        <prop key="txnTraceNumber" value="0012336131"/>
        |        <prop key="transactionSessionId" value="93c4399ad67d925fa40d0693adb0a222"/>
        |        <prop key="requestChannelId" value="WX01"/>
        |        <prop key="txnBankCode" value="003"/>
        |        <prop key="txnProvinceCode" value=""/>
        |        <prop key="txnBranchCode" value="00003"/>
        |        <prop key="txnCounterCode" value=""/>
        |        <prop key="txnTerminalCode" value=""/>
        |        <prop key="txnUserCode" value="wx0000000000"/>
        |        <prop key="localBankTxnRequestTime" value="14:47:51"/>
        |        <prop key="localBankTxnRequestDate" value="2016-04-21"/>
        |        <prop key="localBankTxnResponseTime" value="14:47:51"/>
        |        <prop key="localBankTxnResponseDate" value="2016-04-21"/>
        |        <prop key="bocBankTxnRequestTime" value="14:47:51"/>
        |        <prop key="bocBankTxnRequestDate" value="2016-04-21"/>
        |        <prop key="bocBankTxnResponseTime" value="14:47:51"/>
        |        <prop key="bocBankTxnResponseDate" value="2016-04-21"/>
        |        <prop key="returnCode" value="+GC00000"/>
        |        <prop key="returnMessage" value="Success"/>
        |    </system>
        |    <page key="RP011021">
        |        <prop key="transactionNumber" value="3"/>
        |        <prop key="isFollowUp" value="0"/>
        |        <list key="P0110211" entityKey="p0110211List" size="3">
        |            <entity>
        |                <prop key="cardNo" value="5149580068840943"/>
        |                <prop key="currencyCode" value="CNY"/>
        |                <prop key="instFeeFlag" value="1"/>
        |                <prop key="instalmentAdjustedNumber" value="0"/>
        |                <prop key="instalmentCompleteDate" value="2021-08-10"/>
        |                <prop key="instalmentFirstPostingAmount" value="+00000000001238000"/>
        |                <prop key="instalmentFirstPostingDate" value="2021-06-10"/>
        |                <prop key="instalmentMerchantName" value="2021年05月账单分期"/>
        |                <prop key="instalmentNextNumber" value="4"/>
        |                <prop key="instalmentNextPostingAmount" value="+00000000000000000"/>
        |                <prop key="instalmentNextPostingDate" value=""/>
        |                <prop key="instalmentOriginalAmount" value="+00000000003710000"/>
        |                <prop key="instalmentOriginalNumber" value="3"/>
        |                <prop key="instalmentOriginalTransactionDate" value="2021-05-11"/>
        |                <prop key="instalmentOriginalTransactionTime" value="10:45:36"/>
        |                <prop key="instalmentOutstandingAmount" value="+00000000000000000"/>
        |                <prop key="instalmentOutstandingNumber" value="0"/>
        |                <prop key="instalmentPlanAdjustDate" value=""/>
        |                <prop key="instalmentPlanId" value="001A0001000131C4"/>
        |                <prop key="instalmentPostedNumber" value="3"/>
        |                <prop key="instalmentReversalAmount" value="+00000000000000000"/>
        |                <prop key="instalmentRuleDescription" value="账单分期                                      2021253"/>
        |                <prop key="instalmentRuleId" value="BI01"/>
        |                <prop key="mcc" value="5311"/>
        |            </entity>
        |            <entity>
        |                <prop key="cardNo" value="5149580068840943"/>
        |                <prop key="currencyCode" value="CNY"/>
        |                <prop key="instFeeFlag" value="0"/>
        |                <prop key="instalmentAdjustedNumber" value="0"/>
        |                <prop key="instalmentCompleteDate" value="2022-02-10"/>
        |                <prop key="instalmentFirstPostingAmount" value="+00000000001333000"/>
        |                <prop key="instalmentFirstPostingDate" value="2021-12-10"/>
        |                <prop key="instalmentMerchantName" value="XIAOFEI11"/>
        |                <prop key="instalmentNextNumber" value="4"/>
        |                <prop key="instalmentNextPostingAmount" value="+00000000000000000"/>
        |                <prop key="instalmentNextPostingDate" value=""/>
        |                <prop key="instalmentOriginalAmount" value="+00000000003999000"/>
        |                <prop key="instalmentOriginalNumber" value="3"/>
        |                <prop key="instalmentOriginalTransactionDate" value="2016-03-28"/>
        |                <prop key="instalmentOriginalTransactionTime" value="14:23:57"/>
        |                <prop key="instalmentOutstandingAmount" value="+00000000000000000"/>
        |                <prop key="instalmentOutstandingNumber" value="0"/>
        |                <prop key="instalmentPlanAdjustDate" value=""/>
        |                <prop key="instalmentPlanId" value="001A0001000132CD"/>
        |                <prop key="instalmentPostedNumber" value="3"/>
        |                <prop key="instalmentReversalAmount" value="+00000000000000000"/>
        |                <prop key="instalmentRuleDescription" value="灵活分期                                      2022069"/>
        |                <prop key="instalmentRuleId" value="EP01"/>
        |                <prop key="mcc" value="5311"/>
        |            </entity>
        |            <entity>
        |                <prop key="cardNo" value="5149580068840943"/>
        |                <prop key="currencyCode" value="CNY"/>
        |                <prop key="instFeeFlag" value="1"/>
        |                <prop key="instalmentAdjustedNumber" value="0"/>
        |                <prop key="instalmentCompleteDate" value="2022-03-10"/>
        |                <prop key="instalmentFirstPostingAmount" value="+00000000000306000"/>
        |                <prop key="instalmentFirstPostingDate" value="2021-12-10"/>
        |                <prop key="instalmentMerchantName" value="XIAOFEICESHI"/>
        |                <prop key="instalmentNextNumber" value="5"/>
        |                <prop key="instalmentNextPostingAmount" value="+00000000000000000"/>
        |                <prop key="instalmentNextPostingDate" value=""/>
        |                <prop key="instalmentOriginalAmount" value="+00000000005219000"/>
        |                <prop key="instalmentOriginalNumber" value="18"/>
        |                <prop key="instalmentOriginalTransactionDate" value="2016-03-28"/>
        |                <prop key="instalmentOriginalTransactionTime" value="14:25:33"/>
        |                <prop key="instalmentOutstandingAmount" value="+00000000000000000"/>
        |                <prop key="instalmentOutstandingNumber" value="0"/>
        |                <prop key="instalmentPlanAdjustDate" value=""/>
        |                <prop key="instalmentPlanId" value="001A0001000132D0"/>
        |                <prop key="instalmentPostedNumber" value="4"/>
        |                <prop key="instalmentReversalAmount" value="+00000000000000000"/>
        |                <prop key="instalmentRuleDescription" value="灵活分期                                      2022100"/>
        |                <prop key="instalmentRuleId" value="EP01"/>
        |                <prop key="mcc" value="5311"/>
        |            </entity>
        |        </list>
        |    </page>
        |</GCS>
      """.stripMargin
    val gcsClient = mock[GCSClient]
    val protocol = new TS011021(sessionID, channelID, cardNo, startNumber, selectNumber)(gcsClient)
    val req = protocol.reqXML
    Mockito.when(gcsClient.send(org.mockito.Matchers.any())).thenReturn(resp)
    val reqXML = XML.loadString(req)
    reqXML \@ "transactionID" shouldBe "011021"
    reqXML \@ "isRequest" shouldBe "true"
    reqXML \@ "isResponse" shouldBe "false"
    reqXML \ "page" \@ "key" shouldBe "RQ011021"
    val reqMap = (reqXML \\ "prop" map (node => {
      node \@ "key" -> node \@ "value"
    })).toMap
    reqMap("txnTerminalCode") shouldBe ""
    reqMap("requestChannelId") shouldBe "WX01"
    reqMap("transactionSessionId") shouldBe "93c4399ad67d925fa40d0693adb0a222"
    reqMap("txnBankCode") shouldBe "003"
    reqMap("txnCounterCode") shouldBe ""
    reqMap("transactionCode") shouldBe "011021"
    reqMap("txnBranchCode") shouldBe "00003"
    reqMap("txnProvinceCode") shouldBe ""
    reqMap("txnUserCode") shouldBe "wx0000000000"
    reqMap("transactionNumber") should not be ""
    reqMap("cardNo") shouldBe "5149580068840943"
    reqMap("selectNumber") shouldBe "10"
    reqMap("startNumber") shouldBe "1"

    val respObj = protocol.send
    respObj.systemValue("transactionCode") shouldBe "011021"
    respObj.systemValue("transactionNumber") shouldBe "0020160421145002"
    respObj.systemValue("txnTraceNumber") shouldBe "0012336131"
    respObj.systemValue("transactionSessionId") shouldBe "93c4399ad67d925fa40d0693adb0a222"
    respObj.systemValue("requestChannelId") shouldBe "WX01"
    respObj.systemValue("txnBankCode") shouldBe "003"
    respObj.systemValue("txnProvinceCode") shouldBe ""
    respObj.systemValue("txnBranchCode") shouldBe "00003"
    respObj.systemValue("txnCounterCode") shouldBe ""
    respObj.systemValue("txnTerminalCode") shouldBe ""
    respObj.systemValue("txnUserCode") shouldBe "wx0000000000"
    respObj.systemValue("localBankTxnRequestTime") shouldBe "14:47:51"
    respObj.systemValue("localBankTxnRequestDate") shouldBe "2016-04-21"
    respObj.systemValue("localBankTxnResponseTime") shouldBe "14:47:51"
    respObj.systemValue("localBankTxnResponseDate") shouldBe "2016-04-21"
    respObj.systemValue("bocBankTxnRequestTime") shouldBe "14:47:51"
    respObj.systemValue("bocBankTxnRequestDate") shouldBe "2016-04-21"
    respObj.systemValue("bocBankTxnResponseTime") shouldBe "14:47:51"
    respObj.systemValue("bocBankTxnResponseDate") shouldBe "2016-04-21"
    respObj.systemValue("returnCode") shouldBe "+GC00000"
    respObj.systemValue("returnMessage") shouldBe "Success"

    respObj.pageValue("transactionNumber") shouldBe "3"
    respObj.pageValue("isFollowUp") shouldBe "0"

    val respList = respObj.pageList

    respList.head("cardNo") shouldBe "5149580068840943"
    respList.head("currencyCode") shouldBe "CNY"
    respList.head("instFeeFlag") shouldBe "1"
    respList.head("instalmentAdjustedNumber") shouldBe "0"
    respList.head("instalmentCompleteDate") shouldBe "2021-08-10"
    respList.head("instalmentFirstPostingAmount") shouldBe "+00000000001238000"
    respList.head("instalmentFirstPostingDate") shouldBe "2021-06-10"
    respList.head("instalmentMerchantName") shouldBe "2021年05月账单分期"
    respList.head("instalmentNextNumber") shouldBe "4"
    respList.head("instalmentNextPostingAmount") shouldBe "+00000000000000000"
    respList.head("instalmentNextPostingDate") shouldBe ""
    respList.head("instalmentOriginalAmount") shouldBe "+00000000003710000"
    respList.head("instalmentOriginalNumber") shouldBe "3"
    respList.head("instalmentOriginalTransactionDate") shouldBe "2021-05-11"
    respList.head("instalmentOriginalTransactionTime") shouldBe "10:45:36"
    respList.head("instalmentOutstandingAmount") shouldBe "+00000000000000000"
    respList.head("instalmentOutstandingNumber") shouldBe "0"
    respList.head("instalmentPlanAdjustDate") shouldBe ""
    respList.head("instalmentPlanId") shouldBe "001A0001000131C4"
    respList.head("instalmentPostedNumber") shouldBe "3"
    respList.head("instalmentReversalAmount") shouldBe "+00000000000000000"
    respList.head("instalmentRuleDescription") shouldBe "账单分期                                      2021253"
    respList.head("instalmentRuleId") shouldBe "BI01"
    respList.head("mcc") shouldBe "5311"

    respList.last("cardNo") shouldBe "5149580068840943"
    respList.last("currencyCode") shouldBe "CNY"
    respList.last("instFeeFlag") shouldBe "1"
    respList.last("instalmentAdjustedNumber") shouldBe "0"
    respList.last("instalmentCompleteDate") shouldBe "2022-03-10"
    respList.last("instalmentFirstPostingAmount") shouldBe "+00000000000306000"
    respList.last("instalmentFirstPostingDate") shouldBe "2021-12-10"
    respList.last("instalmentMerchantName") shouldBe "XIAOFEICESHI"
    respList.last("instalmentNextNumber") shouldBe "5"
    respList.last("instalmentNextPostingAmount") shouldBe "+00000000000000000"
    respList.last("instalmentNextPostingDate") shouldBe ""
    respList.last("instalmentOriginalAmount") shouldBe "+00000000005219000"
    respList.last("instalmentOriginalNumber") shouldBe "18"
    respList.last("instalmentOriginalTransactionDate") shouldBe "2016-03-28"
    respList.last("instalmentOriginalTransactionTime") shouldBe "14:25:33"
    respList.last("instalmentOutstandingAmount") shouldBe "+00000000000000000"
    respList.last("instalmentOutstandingNumber") shouldBe "0"
    respList.last("instalmentPlanAdjustDate") shouldBe ""
    respList.last("instalmentPlanId") shouldBe "001A0001000132D0"
    respList.last("instalmentPostedNumber") shouldBe "4"
    respList.last("instalmentReversalAmount") shouldBe "+00000000000000000"
    respList.last("instalmentRuleDescription") shouldBe "灵活分期                                      2022100"
    respList.last("instalmentRuleId") shouldBe "EP01"
    respList.last("mcc") shouldBe "5311"
  }
}
