package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import org.mockito.Mockito
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

import scala.xml.XML

/**
  * 按卡号查询持卡人客户信息交易--“BOC”客户号
  */
class TS010201Test extends FlatSpec with Matchers with MockitoSugar {

  "TS010201" should "handle successful" in {
    val sessionID = "93c4399ad67d925fa40d0693adb0a222"
    val channelID = "WX01"
    val cardNo = "5149580068840943"
    val resp =
      """<?xml version="1.0" encoding="UTF-8"?>
        |<GCS transactionID="010201" isRequest="false" isResponse="true">
        |    <system>
        |        <prop key="transactionCode" value="010201"/>
        |        <prop key="transactionNumber" value="0020160420154726"/>
        |        <prop key="txnTraceNumber" value="0012320814"/>
        |        <prop key="transactionSessionId" value="93c4399ad67d925fa40d0693adb0a222"/>
        |        <prop key="requestChannelId" value="WX01"/>
        |        <prop key="txnBankCode" value="003"/>
        |        <prop key="txnProvinceCode" value=""/>
        |        <prop key="txnBranchCode" value="00003"/>
        |        <prop key="txnCounterCode" value=""/>
        |        <prop key="txnTerminalCode" value=""/>
        |        <prop key="txnUserCode" value="wx0000000000"/>
        |        <prop key="localBankTxnRequestTime" value="15:45:15"/>
        |        <prop key="localBankTxnRequestDate" value="2016-04-20"/>
        |        <prop key="localBankTxnResponseTime" value="15:45:17"/>
        |        <prop key="localBankTxnResponseDate" value="2016-04-20"/>
        |        <prop key="bocBankTxnRequestTime" value="15:45:15"/>
        |        <prop key="bocBankTxnRequestDate" value="2016-04-20"/>
        |        <prop key="bocBankTxnResponseTime" value="15:45:17"/>
        |        <prop key="bocBankTxnResponseDate" value="2016-04-20"/>
        |        <prop key="returnCode" value="+GC00000"/>
        |        <prop key="returnMessage" value="Success"/>
        |    </system>
        |    <page key="RP010201">
        |        <prop key="certificateType" value="SSNO"/>
        |        <prop key="certificateNo" value="AAP0191"/>
        |        <prop key="country" value="CHN"/>
        |        <prop key="province" value=""/>
        |        <prop key="county" value=""/>
        |        <prop key="postalCode" value=""/>
        |        <prop key="mailBox" value=""/>
        |        <prop key="mobileNo" value="13910150191"/>
        |        <prop key="workUnitName" value="单位"/>
        |        <prop key="workUnitPhone" value=""/>
        |        <prop key="messageSetting" value="1"/>
        |        <prop key="billAddressLine1" value="北京海淀"/>
        |        <prop key="billAddressLine2" value=""/>
        |        <prop key="billAddressLine3" value=""/>
        |        <prop key="contactName" value=""/>
        |        <prop key="relationshipWithCustom" value="OTHR"/>
        |        <prop key="conatactUnitName" value=""/>
        |        <prop key="contactPostCode" value=""/>
        |        <prop key="contactAddressLine1" value=""/>
        |        <prop key="contactAddressLine2" value=""/>
        |        <prop key="contactAddressLine3" value=""/>
        |        <prop key="contactProvince" value=""/>
        |        <prop key="contactCity" value=""/>
        |        <prop key="contactMobile" value=""/>
        |        <prop key="contactPhone" value=""/>
        |        <prop key="familyName" value="AAP0191"/>
        |        <prop key="firstName" value="                              "/>
        |        <prop key="pfxTle" value="先生"/>
        |        <prop key="sfxTle" value=""/>
        |        <prop key="middleInitial" value=""/>
        |        <prop key="knwAs" value=""/>
        |        <prop key="productSign" value=""/>
        |        <prop key="institutionId" value="BOCK"/>
        |        <prop key="securitySurname" value="王"/>
        |        <prop key="postionGrade" value="801"/>
        |        <prop key="metEmploy" value="NREQ"/>
        |        <prop key="residencyStatus" value="NRES"/>
        |        <prop key="birthDate" value="1976-08-10"/>
        |        <prop key="maritalStatus" value="UNKN"/>
        |        <prop key="billAddressType" value="BUSS"/>
        |        <prop key="homeAddressLine1" value="北京东城"/>
        |        <prop key="homeaddressLine2" value="                              "/>
        |        <prop key="homeAddressLine3" value="                              "/>
        |        <prop key="homeAddressFax" value="                    "/>
        |        <prop key="homeAddressPhone" value="                              "/>
        |        <prop key="homeAddressPostCode" value="100010"/>
        |        <prop key="homeAddressId" value="001A0213064FF779"/>
        |        <prop key="bussAddressLine1" value="北京海淀"/>
        |        <prop key="bussAddressLine2" value="                              "/>
        |        <prop key="bussAddressLine3" value="                              "/>
        |        <prop key="bussAddressFax" value="                    "/>
        |        <prop key="bussAddressPhone" value="                              "/>
        |        <prop key="bussAddressPostCode" value="          "/>
        |        <prop key="bussAddressId" value="001A0213064FF777"/>
        |        <prop key="gender" value="MALE"/>
        |        <prop key="customerNo" value="             "/>
        |        <prop key="customerId" value="001A0213064FF776"/>
        |    </page>
        |</GCS>
      """.stripMargin
    val gcsClient = mock[GCSClient]
    val protocol = new TS010201(sessionID, channelID, cardNo)(gcsClient)
    val req = protocol.reqXML
    Mockito.when(gcsClient.send(org.mockito.Matchers.any())).thenReturn(resp)
    val reqXML = XML.loadString(req)
    reqXML \@ "transactionID" shouldBe "010201"
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
    reqMap("transactionCode") shouldBe "010201"
    reqMap("txnBranchCode") shouldBe "00003"
    reqMap("txnProvinceCode") shouldBe ""
    reqMap("txnUserCode") shouldBe "wx0000000000"
    reqMap("transactionNumber") should not be ""
    reqMap("cardNo") shouldBe "5149580068840943"

    val respObj = protocol.send
    respObj.systemValue("transactionCode") shouldBe "010201"
    respObj.systemValue("transactionNumber") shouldBe "0020160420154726"
    respObj.systemValue("txnTraceNumber") shouldBe "0012320814"
    respObj.systemValue("transactionSessionId") shouldBe "93c4399ad67d925fa40d0693adb0a222"
    respObj.systemValue("requestChannelId") shouldBe "WX01"
    respObj.systemValue("txnBankCode") shouldBe "003"
    respObj.systemValue("txnProvinceCode") shouldBe ""
    respObj.systemValue("txnBranchCode") shouldBe "00003"
    respObj.systemValue("txnCounterCode") shouldBe ""
    respObj.systemValue("txnTerminalCode") shouldBe ""
    respObj.systemValue("txnUserCode") shouldBe "wx0000000000"
    respObj.systemValue("localBankTxnRequestTime") shouldBe "15:45:15"
    respObj.systemValue("localBankTxnRequestDate") shouldBe "2016-04-20"
    respObj.systemValue("localBankTxnResponseTime") shouldBe "15:45:17"
    respObj.systemValue("localBankTxnResponseDate") shouldBe "2016-04-20"
    respObj.systemValue("bocBankTxnRequestTime") shouldBe "15:45:15"
    respObj.systemValue("bocBankTxnRequestDate") shouldBe "2016-04-20"
    respObj.systemValue("bocBankTxnResponseTime") shouldBe "15:45:17"
    respObj.systemValue("bocBankTxnResponseDate") shouldBe "2016-04-20"
    respObj.systemValue("returnCode") shouldBe "+GC00000"
    respObj.systemValue("returnMessage") shouldBe "Success"

    respObj.pageValue("certificateType") shouldBe "SSNO"
    respObj.pageValue("certificateNo") shouldBe "AAP0191"
    respObj.pageValue("country") shouldBe "CHN"
    respObj.pageValue("province") shouldBe ""
    respObj.pageValue("county") shouldBe ""
    respObj.pageValue("postalCode") shouldBe ""
    respObj.pageValue("mailBox") shouldBe ""
    respObj.pageValue("mobileNo") shouldBe "13910150191"
    respObj.pageValue("workUnitName") shouldBe "单位"
    respObj.pageValue("workUnitPhone") shouldBe ""
    respObj.pageValue("messageSetting") shouldBe "1"
    respObj.pageValue("billAddressLine1") shouldBe "北京海淀"
    respObj.pageValue("billAddressLine2") shouldBe ""
    respObj.pageValue("billAddressLine3") shouldBe ""
    respObj.pageValue("contactName") shouldBe ""
    respObj.pageValue("relationshipWithCustom") shouldBe "OTHR"
    respObj.pageValue("conatactUnitName") shouldBe ""
    respObj.pageValue("contactPostCode") shouldBe ""
    respObj.pageValue("contactAddressLine1") shouldBe ""
    respObj.pageValue("contactAddressLine2") shouldBe ""
    respObj.pageValue("contactAddressLine3") shouldBe ""
    respObj.pageValue("contactProvince") shouldBe ""
    respObj.pageValue("contactCity") shouldBe ""
    respObj.pageValue("contactMobile") shouldBe ""
    respObj.pageValue("contactPhone") shouldBe ""
    respObj.pageValue("familyName") shouldBe "AAP0191"
    respObj.pageValue("firstName").trim shouldBe ""
    respObj.pageValue("pfxTle") shouldBe "先生"
    respObj.pageValue("sfxTle") shouldBe ""
    respObj.pageValue("middleInitial") shouldBe ""
    respObj.pageValue("knwAs") shouldBe ""
    respObj.pageValue("productSign") shouldBe ""
    respObj.pageValue("institutionId") shouldBe "BOCK"
    respObj.pageValue("securitySurname") shouldBe "王"
    respObj.pageValue("postionGrade") shouldBe "801"
    respObj.pageValue("metEmploy") shouldBe "NREQ"
    respObj.pageValue("residencyStatus") shouldBe "NRES"
    respObj.pageValue("birthDate") shouldBe "1976-08-10"
    respObj.pageValue("maritalStatus") shouldBe "UNKN"
    respObj.pageValue("billAddressType") shouldBe "BUSS"
    respObj.pageValue("homeAddressLine1") shouldBe "北京东城"
    respObj.pageValue("homeaddressLine2").trim shouldBe ""
    respObj.pageValue("homeAddressLine3").trim shouldBe ""
    respObj.pageValue("homeAddressFax").trim shouldBe ""
    respObj.pageValue("homeAddressPhone").trim shouldBe ""
    respObj.pageValue("homeAddressPostCode") shouldBe "100010"
    respObj.pageValue("homeAddressId") shouldBe "001A0213064FF779"
    respObj.pageValue("bussAddressLine1") shouldBe "北京海淀"
    respObj.pageValue("bussAddressLine2").trim shouldBe ""
    respObj.pageValue("bussAddressLine3").trim shouldBe ""
    respObj.pageValue("bussAddressFax").trim shouldBe ""
    respObj.pageValue("bussAddressPhone").trim shouldBe ""
    respObj.pageValue("bussAddressPostCode").trim shouldBe ""
    respObj.pageValue("bussAddressId") shouldBe "001A0213064FF777"
    respObj.pageValue("gender") shouldBe "MALE"
    respObj.pageValue("customerNo").trim shouldBe ""
    respObj.pageValue("customerId") shouldBe "001A0213064FF776"
  }
}
