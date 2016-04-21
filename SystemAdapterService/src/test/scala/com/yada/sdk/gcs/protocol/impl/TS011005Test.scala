package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import org.mockito.Mockito
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

import scala.xml.XML

/**
  * 根据卡号查询所有客户信息和卡信息
  */
class TS011005Test extends FlatSpec with Matchers with MockitoSugar {

  "TS011005" should "handle successful" in {
    val sessionID = "93c4399ad67d925fa40d0693adb0a222"
    val channelID = "WX01"
    val cardNo = "5149580068840943"
    val startNum = "1"
    val totalNum = "10"
    val resp =
      """<?xml version="1.0" encoding="UTF-8"?>
        |<GCS transactionID="011005" isRequest="false" isResponse="true">
        |    <system>
        |        <prop key="transactionCode" value="011005"/>
        |        <prop key="transactionNumber" value="0020160421135543"/>
        |        <prop key="txnTraceNumber" value="0012335334"/>
        |        <prop key="transactionSessionId" value="93c4399ad67d925fa40d0693adb0a222"/>
        |        <prop key="requestChannelId" value="WX01"/>
        |        <prop key="txnBankCode" value="003"/>
        |        <prop key="txnProvinceCode" value=""/>
        |        <prop key="txnBranchCode" value="00003"/>
        |        <prop key="txnCounterCode" value=""/>
        |        <prop key="txnTerminalCode" value=""/>
        |        <prop key="txnUserCode" value="wx0000000000"/>
        |        <prop key="localBankTxnRequestTime" value="13:53:31"/>
        |        <prop key="localBankTxnRequestDate" value="2016-04-21"/>
        |        <prop key="localBankTxnResponseTime" value="13:53:33"/>
        |        <prop key="localBankTxnResponseDate" value="2016-04-21"/>
        |        <prop key="bocBankTxnRequestTime" value="13:53:31"/>
        |        <prop key="bocBankTxnRequestDate" value="2016-04-21"/>
        |        <prop key="bocBankTxnResponseTime" value="13:53:33"/>
        |        <prop key="bocBankTxnResponseDate" value="2016-04-21"/>
        |        <prop key="returnCode" value="+GC00000"/>
        |        <prop key="returnMessage" value="Success"/>
        |    </system>
        |    <page key="RP011005">
        |        <prop key="idType" value="03"/>
        |        <prop key="idNum" value="AAP0191"/>
        |        <prop key="customerName" value="AAP0191"/>
        |        <prop key="customerNo" value="356161"/>
        |        <prop key="customerType" value="CONT"/>
        |        <prop key="vipFlag" value="5"/>
        |        <prop key="vipCustomer" value=""/>
        |        <prop key="isHaveNext" value="0"/>
        |        <list key="P0110051" entityKey="p0110051List" size="2">
        |            <entity>
        |                <prop key="baseId" value=""/>
        |                <prop key="cardName" value="中银万事达白金卡精英版"/>
        |                <prop key="cardNo" value="5149580068840943"/>
        |                <prop key="cardProductID" value="MPP2"/>
        |                <prop key="cardStartDate" value="2020-03-10"/>
        |                <prop key="cardStatus" value=""/>
        |                <prop key="issuingBranchId" value="411"/>
        |                <prop key="mainFlag" value="MAIN"/>
        |            </entity>
        |            <entity>
        |                <prop key="baseId" value=""/>
        |                <prop key="cardName" value="万事达PP卡"/>
        |                <prop key="cardNo" value="700013186723779861"/>
        |                <prop key="cardProductID" value="MPP4"/>
        |                <prop key="cardStartDate" value="2020-03-10"/>
        |                <prop key="cardStatus" value="ACTP"/>
        |                <prop key="issuingBranchId" value="411"/>
        |                <prop key="mainFlag" value="MAIN"/>
        |            </entity>
        |        </list>
        |    </page>
        |</GCS>
      """.stripMargin
    val gcsClient = mock[GCSClient]
    val protocol = new TS011005(sessionID, channelID, Some(cardNo), None, None, startNum, totalNum)(gcsClient)
    val req = protocol.reqXML
    Mockito.when(gcsClient.send(org.mockito.Matchers.any())).thenReturn(resp)
    val reqXML = XML.loadString(req)
    reqXML \@ "transactionID" shouldBe "011005"
    reqXML \@ "isRequest" shouldBe "true"
    reqXML \@ "isResponse" shouldBe "false"
    reqXML \ "page" \@ "key" shouldBe "RQ011005"
    val reqMap = (reqXML \\ "prop" map (node => {
      node \@ "key" -> node \@ "value"
    })).toMap
    reqMap("txnTerminalCode") shouldBe ""
    reqMap("requestChannelId") shouldBe "WX01"
    reqMap("transactionSessionId") shouldBe "93c4399ad67d925fa40d0693adb0a222"
    reqMap("txnBankCode") shouldBe "003"
    reqMap("txnCounterCode") shouldBe ""
    reqMap("transactionCode") shouldBe "011005"
    reqMap("txnBranchCode") shouldBe "00003"
    reqMap("txnProvinceCode") shouldBe ""
    reqMap("txnUserCode") shouldBe "wx0000000000"
    reqMap("transactionNumber") should not be ""
    reqMap("cardNo") shouldBe "5149580068840943"
    reqMap("startNum") shouldBe "1"
    reqMap("totalNum") shouldBe "10"
    reqMap("isFilterCardStatus") shouldBe "0"

    val respObj = protocol.send
    respObj.systemValue("transactionCode") shouldBe "011005"
    respObj.systemValue("transactionNumber") shouldBe "0020160421135543"
    respObj.systemValue("txnTraceNumber") shouldBe "0012335334"
    respObj.systemValue("transactionSessionId") shouldBe "93c4399ad67d925fa40d0693adb0a222"
    respObj.systemValue("requestChannelId") shouldBe "WX01"
    respObj.systemValue("txnBankCode") shouldBe "003"
    respObj.systemValue("txnProvinceCode") shouldBe ""
    respObj.systemValue("txnBranchCode") shouldBe "00003"
    respObj.systemValue("txnCounterCode") shouldBe ""
    respObj.systemValue("txnTerminalCode") shouldBe ""
    respObj.systemValue("txnUserCode") shouldBe "wx0000000000"
    respObj.systemValue("localBankTxnRequestTime") shouldBe "13:53:31"
    respObj.systemValue("localBankTxnRequestDate") shouldBe "2016-04-21"
    respObj.systemValue("localBankTxnResponseTime") shouldBe "13:53:33"
    respObj.systemValue("localBankTxnResponseDate") shouldBe "2016-04-21"
    respObj.systemValue("bocBankTxnRequestTime") shouldBe "13:53:31"
    respObj.systemValue("bocBankTxnRequestDate") shouldBe "2016-04-21"
    respObj.systemValue("bocBankTxnResponseTime") shouldBe "13:53:33"
    respObj.systemValue("bocBankTxnResponseDate") shouldBe "2016-04-21"
    respObj.systemValue("returnCode") shouldBe "+GC00000"
    respObj.systemValue("returnMessage") shouldBe "Success"

    respObj.pageValue("idType") shouldBe "03"
    respObj.pageValue("idNum") shouldBe "AAP0191"
    respObj.pageValue("customerName") shouldBe "AAP0191"
    respObj.pageValue("customerNo") shouldBe "356161"
    respObj.pageValue("customerType") shouldBe "CONT"
    respObj.pageValue("vipFlag") shouldBe "5"
    respObj.pageValue("vipCustomer") shouldBe ""
    respObj.pageValue("isHaveNext") shouldBe "0"
    val respList = respObj.pageList
    respList.head("baseId") shouldBe ""
    respList.head("cardName") shouldBe "中银万事达白金卡精英版"
    respList.head("cardNo") shouldBe "5149580068840943"
    respList.head("cardProductID") shouldBe "MPP2"
    respList.head("cardStartDate") shouldBe "2020-03-10"
    respList.head("cardStatus") shouldBe ""
    respList.head("issuingBranchId") shouldBe "411"
    respList.head("mainFlag") shouldBe "MAIN"

    respList.last("baseId") shouldBe ""
    respList.last("cardName") shouldBe "万事达PP卡"
    respList.last("cardNo") shouldBe "700013186723779861"
    respList.last("cardProductID") shouldBe "MPP4"
    respList.last("cardStartDate") shouldBe "2020-03-10"
    respList.last("cardStatus") shouldBe "ACTP"
    respList.last("issuingBranchId") shouldBe "411"
    respList.last("mainFlag") shouldBe "MAIN"
  }
}
