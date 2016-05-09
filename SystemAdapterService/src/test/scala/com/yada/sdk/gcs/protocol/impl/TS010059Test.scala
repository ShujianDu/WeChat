package com.yada.sdk.gcs.protocol.impl

import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

/**
  * 临时挂失
  */
class TS010059Test extends FlatSpec with Matchers with MockitoSugar {

  "TS010059" should "handle successful" in {
    // TODO 未完成
    //    val sessionID = "93c4399ad67d925fa40d0693adb0a222"
    //    val channelID = "WX01"
    //    val cardNo = "5149580068840943"
    //    val resp =
    //      """<?xml version="1.0" encoding="UTF-8"?>
    //        |<GCS transactionID="010056" isRequest="false" isResponse="true">
    //        |    <system>
    //        |        <prop key="transactionCode" value="010056"/>
    //        |        <prop key="transactionNumber" value="0020160420103443"/>
    //        |        <prop key="txnTraceNumber" value="0012177116"/>
    //        |        <prop key="transactionSessionId" value="93c4399ad67d925fa40d0693adb0a222"/>
    //        |        <prop key="requestChannelId" value="WX01"/>
    //        |        <prop key="txnBankCode" value="003"/>
    //        |        <prop key="txnProvinceCode" value=""/>
    //        |        <prop key="txnBranchCode" value="00003"/>
    //        |        <prop key="txnCounterCode" value=""/>
    //        |        <prop key="txnTerminalCode" value=""/>
    //        |        <prop key="txnUserCode" value="wx0000000000"/>
    //        |        <prop key="localBankTxnRequestTime" value="10:32:32"/>
    //        |        <prop key="localBankTxnRequestDate" value="2016-04-20"/>
    //        |        <prop key="localBankTxnResponseTime" value="10:32:33"/>
    //        |        <prop key="localBankTxnResponseDate" value="2016-04-20"/>
    //        |        <prop key="bocBankTxnRequestTime" value="10:32:32"/>
    //        |        <prop key="bocBankTxnRequestDate" value="2016-04-20"/>
    //        |        <prop key="bocBankTxnResponseTime" value="10:32:33"/>
    //        |        <prop key="bocBankTxnResponseDate" value="2016-04-20"/>
    //        |        <prop key="returnCode" value="+GC00000"/>
    //        |        <prop key="returnMessage" value="Success"/>
    //        |    </system>
    //        |    <page key="RP010101">
    //        |        <prop key="emailStatementOption" value="C"/>
    //        |    </page>
    //        |</GCS>
    //      """.stripMargin
    //
    //    val protocol = new TS010059(sessionID, channelID, cardNo, "C")
    //    protocol.gcsClient = mock[GCSClient]
    //    val req = protocol.reqXML
    //    Mockito.when(protocol.gcsClient.send(org.mockito.Matchers.any())).thenReturn(resp)
    //    val reqXML = XML.loadString(req)
    //    reqXML \@ "transactionID" shouldBe "010056"
    //    reqXML \@ "isRequest" shouldBe "true"
    //    reqXML \@ "isResponse" shouldBe "false"
    //    reqXML \ "page" \@ "key" shouldBe "RP010002"
    //    val reqMap = (reqXML \\ "prop" map (node => {
    //      node \@ "key" -> node \@ "value"
    //    })).toMap
    //    reqMap("txnTerminalCode") shouldBe ""
    //    reqMap("requestChannelId") shouldBe "WX01"
    //    reqMap("transactionSessionId") shouldBe "93c4399ad67d925fa40d0693adb0a222"
    //    reqMap("txnBankCode") shouldBe "003"
    //    reqMap("txnCounterCode") shouldBe ""
    //    reqMap("transactionCode") shouldBe "010056"
    //    reqMap("txnBranchCode") shouldBe "00003"
    //    reqMap("txnProvinceCode") shouldBe ""
    //    reqMap("txnUserCode") shouldBe "wx0000000000"
    //    reqMap("transactionNumber") should not be ""
    //    reqMap("cardNo") shouldBe "5149580068840943"
    //    reqMap("billSendType") shouldBe "C"
    //
    //    val respObj = protocol.send
    //    respObj.systemValue("transactionCode") shouldBe "010056"
    //    respObj.systemValue("transactionNumber") shouldBe "0020160420103443"
    //    respObj.systemValue("txnTraceNumber") shouldBe "0012177116"
    //    respObj.systemValue("transactionSessionId") shouldBe "93c4399ad67d925fa40d0693adb0a222"
    //    respObj.systemValue("requestChannelId") shouldBe "WX01"
    //    respObj.systemValue("txnBankCode") shouldBe "003"
    //    respObj.systemValue("txnProvinceCode") shouldBe ""
    //    respObj.systemValue("txnBranchCode") shouldBe "00003"
    //    respObj.systemValue("txnCounterCode") shouldBe ""
    //    respObj.systemValue("txnTerminalCode") shouldBe ""
    //    respObj.systemValue("txnUserCode") shouldBe "wx0000000000"
    //    respObj.systemValue("localBankTxnRequestTime") shouldBe "10:32:32"
    //    respObj.systemValue("localBankTxnRequestDate") shouldBe "2016-04-20"
    //    respObj.systemValue("localBankTxnResponseTime") shouldBe "10:32:33"
    //    respObj.systemValue("localBankTxnResponseDate") shouldBe "2016-04-20"
    //    respObj.systemValue("bocBankTxnRequestTime") shouldBe "10:32:32"
    //    respObj.systemValue("bocBankTxnRequestDate") shouldBe "2016-04-20"
    //    respObj.systemValue("bocBankTxnResponseTime") shouldBe "10:32:33"
    //    respObj.systemValue("bocBankTxnResponseDate") shouldBe "2016-04-20"
    //    respObj.systemValue("returnCode") shouldBe "+GC00000"
    //    respObj.systemValue("returnMessage") shouldBe "Success"
    //    respObj.pageValue("emailStatementOption") shouldBe "C"

    /**
      * "C:\Program Files\Java\jdk1.8.0_66\bin\java" -Didea.launcher.port=7532 "-Didea.launcher.bin.path=C:\Program Files (x86)\JetBrains\IntelliJ IDEA 2016.1\bin" -Dfile.encoding=UTF-8 -classpath "C:\Program Files\Java\jdk1.8.0_66\jre\lib\charsets.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\deploy.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\access-bridge-64.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\cldrdata.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\dnsns.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\jaccess.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\jfxrt.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\localedata.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\nashorn.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\sunec.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\sunjce_provider.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\sunmscapi.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\sunpkcs11.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\zipfs.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\javaws.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\jce.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\jfr.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\jfxswt.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\jsse.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\management-agent.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\plugin.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\resources.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\rt.jar;C:\Users\19016\ideaworkspace\WeChat\SystemAdapterService\target\test-classes;C:\Users\19016\ideaworkspace\WeChat\SystemAdapterService\target\classes;C:\Users\19016\.m2\repository\org\scala-lang\scala-library\2.11.8\scala-library-2.11.8.jar;C:\Users\19016\.m2\repository\org\scala-lang\scala-compiler\2.11.8\scala-compiler-2.11.8.jar;C:\Users\19016\.m2\repository\org\scala-lang\scala-reflect\2.11.8\scala-reflect-2.11.8.jar;C:\Users\19016\.m2\repository\org\scala-lang\modules\scala-parser-combinators_2.11\1.0.4\scala-parser-combinators_2.11-1.0.4.jar;C:\Users\19016\.m2\repository\org\scala-lang\modules\scala-xml_2.11\1.0.5\scala-xml_2.11-1.0.5.jar;C:\Users\19016\.m2\repository\ch\qos\logback\logback-classic\1.1.6\logback-classic-1.1.6.jar;C:\Users\19016\.m2\repository\ch\qos\logback\logback-core\1.1.6\logback-core-1.1.6.jar;C:\Users\19016\.m2\repository\org\slf4j\slf4j-api\1.7.19\slf4j-api-1.7.19.jar;C:\Users\19016\.m2\repository\com\thoughtworks\xstream\xstream\1.4.8\xstream-1.4.8.jar;C:\Users\19016\.m2\repository\xmlpull\xmlpull\1.1.3.1\xmlpull-1.1.3.1.jar;C:\Users\19016\.m2\repository\xpp3\xpp3_min\1.1.4c\xpp3_min-1.1.4c.jar;C:\Users\19016\.m2\repository\commons-codec\commons-codec\1.10\commons-codec-1.10.jar;C:\Users\19016\.m2\repository\org\apache\commons\commons-lang3\3.4\commons-lang3-3.4.jar;C:\Users\19016\.m2\repository\com\typesafe\scala-logging\scala-logging-slf4j_2.11\2.1.2\scala-logging-slf4j_2.11-2.1.2.jar;C:\Users\19016\.m2\repository\com\typesafe\scala-logging\scala-logging-api_2.11\2.1.2\scala-logging-api_2.11-2.1.2.jar;C:\Users\19016\.m2\repository\org\scalatest\scalatest_2.11\2.2.6\scalatest_2.11-2.2.6.jar;C:\Users\19016\.m2\repository\org\mockito\mockito-all\1.10.19\mockito-all-1.10.19.jar;C:\Users\19016\.m2\repository\com\typesafe\config\1.2.1\config-1.2.1.jar;C:\Users\19016\.m2\repository\com\ibm\mq\mq\7.0.0\mq-7.0.0.jar;C:\Users\19016\.m2\repository\com\ibm\mq\jmqi\7.0.0\jmqi-7.0.0.jar;C:\Users\19016\.m2\repository\com\ibm\mq\connector\7.0.0\connector-7.0.0.jar;C:\Users\19016\.m2\repository\com\typesafe\play\play-json_2.11\2.3.10\play-json_2.11-2.3.10.jar;C:\Users\19016\.m2\repository\com\typesafe\play\play-iteratees_2.11\2.3.10\play-iteratees_2.11-2.3.10.jar;C:\Users\19016\.m2\repository\org\scala-stm\scala-stm_2.11\0.7\scala-stm_2.11-0.7.jar;C:\Users\19016\.m2\repository\com\typesafe\play\play-functional_2.11\2.3.10\play-functional_2.11-2.3.10.jar;C:\Users\19016\.m2\repository\com\typesafe\play\play-datacommons_2.11\2.3.10\play-datacommons_2.11-2.3.10.jar;C:\Users\19016\.m2\repository\joda-time\joda-time\2.3\joda-time-2.3.jar;C:\Users\19016\.m2\repository\org\joda\joda-convert\1.6\joda-convert-1.6.jar;C:\Users\19016\.m2\repository\com\fasterxml\jackson\core\jackson-annotations\2.3.2\jackson-annotations-2.3.2.jar;C:\Users\19016\.m2\repository\com\fasterxml\jackson\core\jackson-core\2.3.2\jackson-core-2.3.2.jar;C:\Users\19016\.m2\repository\com\fasterxml\jackson\core\jackson-databind\2.3.2\jackson-databind-2.3.2.jar;C:\Program Files (x86)\JetBrains\IntelliJ IDEA 2016.1\lib\idea_rt.jar" com.intellij.rt.execution.application.AppMain com.yada.sdk.gcs.GCSProtocolTestApp
send to GCS...
<?xml version="1.0" encoding="UTF-8"?>
<GCS transactionID="010059" isRequest="true" isResponse="false">
  <system>
    <prop key="txnTerminalCode" value=""/>
    <prop key="requestChannelId" value="WX01"/>
    <prop key="transactionSessionId" value="93c4399ad67d925fa40d0693adb0a222"/>
    <prop key="txnBankCode" value="003"/>
    <prop key="txnCounterCode" value=""/>
    <prop key="transactionCode" value="010059"/>
    <prop key="txnBranchCode" value="00003"/>
    <prop key="txnProvinceCode" value=""/>
    <prop key="txnUserCode" value="wx0000000000"/>
    <prop key="transactionNumber" value="0020160509174500"/>
  </system>
  <page key="RQ010059">
    <prop key="idNum" value="AAP0345"/>
    <prop key="cardNo" value="377677530138585"/>
    <prop key="idType" value="03"/>
    <prop key="lossReason" value="02"/>
    <prop key="familyName" value="AAP0345"/>
    <prop key="entyMethod" value="01"/>
  </page>
</GCS>
rece from GCS...
<?xml version="1.0" encoding="UTF-8"?>
<GCS transactionID="010059" isRequest="false" isResponse="true"><system><prop key="transactionCode" value="010059"/><prop key="transactionNumber" value="0020160509174500"/><prop key="txnTraceNumber" value="0013219685"/><prop key="transactionSessionId" value="93c4399ad67d925fa40d0693adb0a222"/><prop key="requestChannelId" value="WX01"/><prop key="txnBankCode" value="003"/><prop key="txnProvinceCode" value=""/><prop key="txnBranchCode" value="00003"/><prop key="txnCounterCode" value=""/><prop key="txnTerminalCode" value=""/><prop key="txnUserCode" value="wx0000000000"/><prop key="localBankTxnRequestTime" value="17:42:45"/><prop key="localBankTxnRequestDate" value="2016-05-09"/><prop key="localBankTxnResponseTime" value="17:42:46"/><prop key="localBankTxnResponseDate" value="2016-05-09"/><prop key="bocBankTxnRequestTime" value="17:42:45"/><prop key="bocBankTxnRequestDate" value="2016-05-09"/><prop key="bocBankTxnResponseTime" value="17:42:46"/><prop key="bocBankTxnResponseDate" value="2016-05-09"/><prop key="returnCode" value="+GC00000"/><prop key="returnMessage" value="Success"/></system><page key="RQ010101"/></GCS>

Process finished with exit code 0

      */
  }
}
