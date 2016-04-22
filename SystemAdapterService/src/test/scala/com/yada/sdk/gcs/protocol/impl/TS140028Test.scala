package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import org.mockito.Mockito
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

import scala.xml.XML

/**
  * 根据证件号码或卡号查询客户信息
  */
class TS140028Test extends FlatSpec with Matchers with MockitoSugar {

  "TS140028" should "handle successful" in {
    val sessionID = "93c4399ad67d925fa40d0693adb0a222"
    val channelID = "WX01"
    val idNum = "AAP0191"
    val idType = "SSNO"
    val resp =
      """<?xml version="1.0" encoding="UTF-8"?>
        |<GCS transactionID="140028" isRequest="false" isResponse="true">
        |    <system>
        |        <prop key="transactionCode" value="140028"/>
        |        <prop key="transactionNumber" value="0020160422100336"/>
        |        <prop key="txnTraceNumber" value="0012343929"/>
        |        <prop key="transactionSessionId" value="93c4399ad67d925fa40d0693adb0a222"/>
        |        <prop key="requestChannelId" value="WX01"/>
        |        <prop key="txnBankCode" value="003"/>
        |        <prop key="txnProvinceCode" value=""/>
        |        <prop key="txnBranchCode" value="00003"/>
        |        <prop key="txnCounterCode" value=""/>
        |        <prop key="txnTerminalCode" value=""/>
        |        <prop key="txnUserCode" value="wx0000000000"/>
        |        <prop key="localBankTxnRequestTime" value="10:01:25"/>
        |        <prop key="localBankTxnRequestDate" value="2016-04-22"/>
        |        <prop key="localBankTxnResponseTime" value="10:01:28"/>
        |        <prop key="localBankTxnResponseDate" value="2016-04-22"/>
        |        <prop key="bocBankTxnRequestTime" value="10:01:25"/>
        |        <prop key="bocBankTxnRequestDate" value="2016-04-22"/>
        |        <prop key="bocBankTxnResponseTime" value="10:01:28"/>
        |        <prop key="bocBankTxnResponseDate" value="2016-04-22"/>
        |        <prop key="returnCode" value="+GC00000"/>
        |        <prop key="returnMessage" value="Success"/>
        |    </system>
        |    <page key="RP140028">
        |        <prop key="appiMcMPhone" value="13910150191"/>
        |    </page>
        |</GCS>
      """.stripMargin
    val gcsClient = mock[GCSClient]
    val protocol = new TS140028(sessionID, channelID, idType, idNum)(gcsClient)
    val req = protocol.reqXML
    Mockito.when(gcsClient.send(org.mockito.Matchers.any())).thenReturn(resp)
    val reqXML = XML.loadString(req)
    reqXML \@ "transactionID" shouldBe "140028"
    reqXML \@ "isRequest" shouldBe "true"
    reqXML \@ "isResponse" shouldBe "false"
    reqXML \ "page" \@ "key" shouldBe "RQ140028"
    val reqMap = (reqXML \\ "prop" map (node => {
      node \@ "key" -> node \@ "value"
    })).toMap
    reqMap("txnTerminalCode") shouldBe ""
    reqMap("requestChannelId") shouldBe "WX01"
    reqMap("transactionSessionId") shouldBe "93c4399ad67d925fa40d0693adb0a222"
    reqMap("txnBankCode") shouldBe "003"
    reqMap("txnCounterCode") shouldBe ""
    reqMap("transactionCode") shouldBe "140028"
    reqMap("txnBranchCode") shouldBe "00003"
    reqMap("txnProvinceCode") shouldBe ""
    reqMap("txnUserCode") shouldBe "wx0000000000"
    reqMap("transactionNumber") should not be ""
    reqMap("idNo") shouldBe "AAP0191"
    reqMap("idType") shouldBe "SSNO"

    val respObj = protocol.send
    respObj.systemValue("transactionCode") shouldBe "140028"
    respObj.systemValue("transactionNumber") shouldBe "0020160422100336"
    respObj.systemValue("txnTraceNumber") shouldBe "0012343929"
    respObj.systemValue("transactionSessionId") shouldBe "93c4399ad67d925fa40d0693adb0a222"
    respObj.systemValue("requestChannelId") shouldBe "WX01"
    respObj.systemValue("txnBankCode") shouldBe "003"
    respObj.systemValue("txnProvinceCode") shouldBe ""
    respObj.systemValue("txnBranchCode") shouldBe "00003"
    respObj.systemValue("txnCounterCode") shouldBe ""
    respObj.systemValue("txnTerminalCode") shouldBe ""
    respObj.systemValue("txnUserCode") shouldBe "wx0000000000"
    respObj.systemValue("localBankTxnRequestTime") shouldBe "10:01:25"
    respObj.systemValue("localBankTxnRequestDate") shouldBe "2016-04-22"
    respObj.systemValue("localBankTxnResponseTime") shouldBe "10:01:28"
    respObj.systemValue("localBankTxnResponseDate") shouldBe "2016-04-22"
    respObj.systemValue("bocBankTxnRequestTime") shouldBe "10:01:25"
    respObj.systemValue("bocBankTxnRequestDate") shouldBe "2016-04-22"
    respObj.systemValue("bocBankTxnResponseTime") shouldBe "10:01:28"
    respObj.systemValue("bocBankTxnResponseDate") shouldBe "2016-04-22"
    respObj.systemValue("returnCode") shouldBe "+GC00000"
    respObj.systemValue("returnMessage") shouldBe "Success"

    respObj.pageValue("appiMcMPhone") shouldBe "13910150191"

  }
}
