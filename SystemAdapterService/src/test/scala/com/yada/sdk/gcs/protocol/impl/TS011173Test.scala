package com.yada.sdk.gcs.protocol.impl

class TS011173Test {

  //TODO

  /**
    * "C:\Program Files\Java\jdk1.8.0_66\bin\java" -Didea.launcher.port=7533 "-Didea.launcher.bin.path=C:\Program Files (x86)\JetBrains\IntelliJ IDEA 2016.1\bin" -Dfile.encoding=UTF-8 -classpath "C:\Program Files\Java\jdk1.8.0_66\jre\lib\charsets.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\deploy.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\access-bridge-64.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\cldrdata.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\dnsns.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\jaccess.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\jfxrt.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\localedata.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\nashorn.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\sunec.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\sunjce_provider.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\sunmscapi.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\sunpkcs11.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\zipfs.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\javaws.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\jce.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\jfr.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\jfxswt.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\jsse.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\management-agent.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\plugin.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\resources.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\rt.jar;C:\Users\19016\ideaworkspace\WeChat\SystemAdapterService\target\test-classes;C:\Users\19016\ideaworkspace\WeChat\SystemAdapterService\target\classes;C:\Users\19016\.m2\repository\org\scala-lang\scala-library\2.11.8\scala-library-2.11.8.jar;C:\Users\19016\.m2\repository\org\scala-lang\scala-compiler\2.11.8\scala-compiler-2.11.8.jar;C:\Users\19016\.m2\repository\org\scala-lang\scala-reflect\2.11.8\scala-reflect-2.11.8.jar;C:\Users\19016\.m2\repository\org\scala-lang\modules\scala-parser-combinators_2.11\1.0.4\scala-parser-combinators_2.11-1.0.4.jar;C:\Users\19016\.m2\repository\org\scala-lang\modules\scala-xml_2.11\1.0.5\scala-xml_2.11-1.0.5.jar;C:\Users\19016\.m2\repository\ch\qos\logback\logback-classic\1.1.6\logback-classic-1.1.6.jar;C:\Users\19016\.m2\repository\ch\qos\logback\logback-core\1.1.6\logback-core-1.1.6.jar;C:\Users\19016\.m2\repository\org\slf4j\slf4j-api\1.7.19\slf4j-api-1.7.19.jar;C:\Users\19016\.m2\repository\com\thoughtworks\xstream\xstream\1.4.8\xstream-1.4.8.jar;C:\Users\19016\.m2\repository\xmlpull\xmlpull\1.1.3.1\xmlpull-1.1.3.1.jar;C:\Users\19016\.m2\repository\xpp3\xpp3_min\1.1.4c\xpp3_min-1.1.4c.jar;C:\Users\19016\.m2\repository\commons-codec\commons-codec\1.10\commons-codec-1.10.jar;C:\Users\19016\.m2\repository\org\apache\commons\commons-lang3\3.4\commons-lang3-3.4.jar;C:\Users\19016\.m2\repository\com\typesafe\scala-logging\scala-logging-slf4j_2.11\2.1.2\scala-logging-slf4j_2.11-2.1.2.jar;C:\Users\19016\.m2\repository\com\typesafe\scala-logging\scala-logging-api_2.11\2.1.2\scala-logging-api_2.11-2.1.2.jar;C:\Users\19016\.m2\repository\org\scalatest\scalatest_2.11\2.2.6\scalatest_2.11-2.2.6.jar;C:\Users\19016\.m2\repository\org\mockito\mockito-all\1.10.19\mockito-all-1.10.19.jar;C:\Users\19016\.m2\repository\com\typesafe\config\1.2.1\config-1.2.1.jar;C:\Users\19016\.m2\repository\com\ibm\mq\mq\7.0.0\mq-7.0.0.jar;C:\Users\19016\.m2\repository\com\ibm\mq\jmqi\7.0.0\jmqi-7.0.0.jar;C:\Users\19016\.m2\repository\com\ibm\mq\connector\7.0.0\connector-7.0.0.jar;C:\Users\19016\.m2\repository\com\typesafe\play\play-json_2.11\2.3.10\play-json_2.11-2.3.10.jar;C:\Users\19016\.m2\repository\com\typesafe\play\play-iteratees_2.11\2.3.10\play-iteratees_2.11-2.3.10.jar;C:\Users\19016\.m2\repository\org\scala-stm\scala-stm_2.11\0.7\scala-stm_2.11-0.7.jar;C:\Users\19016\.m2\repository\com\typesafe\play\play-functional_2.11\2.3.10\play-functional_2.11-2.3.10.jar;C:\Users\19016\.m2\repository\com\typesafe\play\play-datacommons_2.11\2.3.10\play-datacommons_2.11-2.3.10.jar;C:\Users\19016\.m2\repository\joda-time\joda-time\2.3\joda-time-2.3.jar;C:\Users\19016\.m2\repository\org\joda\joda-convert\1.6\joda-convert-1.6.jar;C:\Users\19016\.m2\repository\com\fasterxml\jackson\core\jackson-annotations\2.3.2\jackson-annotations-2.3.2.jar;C:\Users\19016\.m2\repository\com\fasterxml\jackson\core\jackson-core\2.3.2\jackson-core-2.3.2.jar;C:\Users\19016\.m2\repository\com\fasterxml\jackson\core\jackson-databind\2.3.2\jackson-databind-2.3.2.jar;C:\Program Files (x86)\JetBrains\IntelliJ IDEA 2016.1\lib\idea_rt.jar" com.intellij.rt.execution.application.AppMain com.yada.sdk.gcs.GCSProtocolTestApp
send to GCS...
<?xml version="1.0" encoding="UTF-8"?>
<GCS transactionID="011173" isRequest="true" isResponse="false">
  <system>
    <prop key="txnTerminalCode" value=""/>
    <prop key="requestChannelId" value="WX01"/>
    <prop key="transactionSessionId" value="93c4399ad67d925fa40d0693adb0a222"/>
    <prop key="txnBankCode" value="003"/>
    <prop key="txnCounterCode" value=""/>
    <prop key="transactionCode" value="011173"/>
    <prop key="txnBranchCode" value="00003"/>
    <prop key="txnProvinceCode" value=""/>
    <prop key="txnUserCode" value="wx0000000000"/>
    <prop key="transactionNumber" value="0020160510141502"/>
  </system>
  <page key="RQ011173">
    <prop key="currencyCode" value="CNY"/>
    <prop key="transactionAmount" value="000000000001300000"/>
    <prop key="accountKeyOne" value="001A021306500928"/>
    <prop key="merchantID" value="0000000"/>
    <prop key="cardNo" value="377677523143733"/>
    <prop key="accountNoID" value="1297812597499142"/>
    <prop key="billDateNo" value="40"/>
    <prop key="isfeeFlag" value="1"/>
    <prop key="mcc" value="5311"/>
    <prop key="transactionCode" value="3110"/>
    <prop key="installmentPeriods" value="6"/>
    <prop key="accountKeyTwo" value="001A021306500928"/>
    <prop key="transactionNo" value="2"/>
    <prop key="installmentPlan" value="EP01"/>
    <prop key="channelId" value="A"/>
  </page>
</GCS>
rece from GCS...
<?xml version="1.0" encoding="UTF-8"?>
<GCS transactionID="011173" isRequest="false" isResponse="true"><system><prop key="transactionCode" value="011173"/><prop key="transactionNumber" value="0020160510141502"/><prop key="txnTraceNumber" value="0013275872"/><prop key="transactionSessionId" value="93c4399ad67d925fa40d0693adb0a222"/><prop key="requestChannelId" value="WX01"/><prop key="txnBankCode" value="003"/><prop key="txnProvinceCode" value=""/><prop key="txnBranchCode" value="00003"/><prop key="txnCounterCode" value=""/><prop key="txnTerminalCode" value=""/><prop key="txnUserCode" value="wx0000000000"/><prop key="localBankTxnRequestTime" value="14:12:45"/><prop key="localBankTxnRequestDate" value="2016-05-10"/><prop key="localBankTxnResponseTime" value="14:12:45"/><prop key="localBankTxnResponseDate" value="2016-05-10"/><prop key="bocBankTxnRequestTime" value="14:12:45"/><prop key="bocBankTxnRequestDate" value="2016-05-10"/><prop key="bocBankTxnResponseTime" value="14:12:45"/><prop key="bocBankTxnResponseDate" value="2016-05-10"/><prop key="returnCode" value="+GC00000"/><prop key="returnMessage" value="Success"/></system><page key="RP011173"><prop key="commno" value="785178"/><prop key="authReturnCode" value="000"/><prop key="handleCode" value="0"/></page></GCS>
GCSResp(GCS(011173,false,true,System(Map(bocBankTxnResponseTime -> 14:12:45, txnTerminalCode -> , requestChannelId -> WX01, transactionSessionId -> 93c4399ad67d925fa40d0693adb0a222, localBankTxnRequestTime -> 14:12:45, bocBankTxnResponseDate -> 2016-05-10, localBankTxnResponseTime -> 14:12:45, returnCode -> +GC00000, txnTraceNumber -> 0013275872, txnBankCode -> 003, bocBankTxnRequestTime -> 14:12:45, localBankTxnRequestDate -> 2016-05-10, transactionCode -> 011173, txnCounterCode -> , txnBranchCode -> 00003, localBankTxnResponseDate -> 2016-05-10, txnProvinceCode -> , bocBankTxnRequestDate -> 2016-05-10, returnMessage -> Success, txnUserCode -> wx0000000000, transactionNumber -> 0020160510141502)),Some(Page(RP011173,Map(authReturnCode -> 000, handleCode -> 0, commno -> 785178),None))))

Process finished with exit code 0

    */

}
