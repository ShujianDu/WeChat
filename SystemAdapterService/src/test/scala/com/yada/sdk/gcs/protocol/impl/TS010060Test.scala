package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import org.mockito.Mockito
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

import scala.xml.XML


class TS010060Test extends FlatSpec with Matchers with MockitoSugar {

  "TS010060" should "success" in {
    val sessionID = "93c4399ad67d925fa40d0693adb0a222"
    val channelID = "WX01"
    val cardNo = "377677530138585"
    val idNum = "AAP0345"
    val idType = "03"
    val familyName = "AAP0345"
    val resp =
      """<?xml version="1.0" encoding="UTF-8"?>
        |<GCS transactionID="010060" isRequest="false" isResponse="true">
        |    <system>
        |        <prop key="transactionCode" value="010060"/>
        |        <prop key="transactionNumber" value="0020160508190554"/>
        |        <prop key="txnTraceNumber" value="0013197004"/>
        |        <prop key="transactionSessionId" value="93c4399ad67d925fa40d0693adb0a222"/>
        |        <prop key="requestChannelId" value="WX01"/>
        |        <prop key="txnBankCode" value="003"/>
        |        <prop key="txnProvinceCode" value=""/>
        |        <prop key="txnBranchCode" value="00003"/>
        |        <prop key="txnCounterCode" value=""/>
        |        <prop key="txnTerminalCode" value=""/>
        |        <prop key="txnUserCode" value="wx0000000000"/>
        |        <prop key="localBankTxnRequestTime" value="19:03:39"/>
        |        <prop key="localBankTxnRequestDate" value="2016-05-08"/>
        |        <prop key="localBankTxnResponseTime" value="19:03:40"/>
        |        <prop key="localBankTxnResponseDate" value="2016-05-08"/>
        |        <prop key="bocBankTxnRequestTime" value="19:03:39"/>
        |        <prop key="bocBankTxnRequestDate" value="2016-05-08"/>
        |        <prop key="bocBankTxnResponseTime" value="19:03:40"/>
        |        <prop key="bocBankTxnResponseDate" value="2016-05-08"/>
        |        <prop key="returnCode" value="+GC00000"/>
        |        <prop key="returnMessage" value="Success"/>
        |    </system>
        |    <page key="RQ010101"/>
        |</GCS>
        """.stripMargin

    val gcsClient = mock[GCSClient]
    val protocol = new TS010060(sessionID, channelID, cardNo,idNum,familyName,idType)(gcsClient)
    val req = protocol.reqXML
    Mockito.when(gcsClient.send(org.mockito.Matchers.any())).thenReturn(resp)
    val reqXML = XML.loadString(req)

    reqXML \@ "transactionID" shouldBe "010060"
    reqXML \@ "isRequest" shouldBe "true"
    reqXML \@ "isResponse" shouldBe "false"
    reqXML \ "page" \@ "key" shouldBe "RQ010060"
    val reqMap = (reqXML \\ "prop" map (node => {
      node \@ "key" -> node \@ "value"
    })).toMap
    reqMap("txnTerminalCode") shouldBe ""
    reqMap("requestChannelId") shouldBe "WX01"
    reqMap("transactionSessionId") shouldBe "93c4399ad67d925fa40d0693adb0a222"
    reqMap("txnBankCode") shouldBe "003"
    reqMap("txnCounterCode") shouldBe ""
    reqMap("transactionCode") shouldBe "010060"
    reqMap("txnBranchCode") shouldBe "00003"
    reqMap("txnProvinceCode") shouldBe ""
    reqMap("txnUserCode") shouldBe "wx0000000000"
    reqMap("transactionNumber") should not be ""
    reqMap("idNum") shouldBe idNum
    reqMap("cardNo") shouldBe cardNo
    reqMap("idType") shouldBe idType
    reqMap("familyName") shouldBe familyName
    reqMap("entyMethod") shouldBe "01"

    val respObj = protocol.send

    respObj.systemValue("transactionCode") shouldBe "010060"
    respObj.systemValue("transactionNumber") shouldBe "0020160508190554"
    respObj.systemValue("txnTraceNumber") shouldBe "0013197004"
    respObj.systemValue("transactionSessionId") shouldBe "93c4399ad67d925fa40d0693adb0a222"
    respObj.systemValue("requestChannelId") shouldBe "WX01"
    respObj.systemValue("txnBankCode") shouldBe "003"
    respObj.systemValue("txnProvinceCode") shouldBe ""
    respObj.systemValue("txnBranchCode") shouldBe "00003"
    respObj.systemValue("txnCounterCode") shouldBe ""
    respObj.systemValue("txnTerminalCode") shouldBe ""
    respObj.systemValue("txnUserCode") shouldBe "wx0000000000"
    respObj.systemValue("localBankTxnRequestTime") shouldBe "19:03:39"
    respObj.systemValue("localBankTxnRequestDate") shouldBe "2016-05-08"
    respObj.systemValue("localBankTxnResponseTime") shouldBe "19:03:40"
    respObj.systemValue("localBankTxnResponseDate") shouldBe "2016-05-08"
    respObj.systemValue("bocBankTxnRequestTime") shouldBe "19:03:39"
    respObj.systemValue("bocBankTxnRequestDate") shouldBe "2016-05-08"
    respObj.systemValue("bocBankTxnResponseTime") shouldBe "19:03:40"
    respObj.systemValue("bocBankTxnResponseDate") shouldBe "2016-05-08"
    respObj.systemValue("returnCode") shouldBe "+GC00000"
    respObj.systemValue("returnMessage") shouldBe "Success"
  }
}
