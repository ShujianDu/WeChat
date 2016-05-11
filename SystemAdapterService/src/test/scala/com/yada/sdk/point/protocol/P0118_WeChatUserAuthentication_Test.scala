package com.yada.sdk.point.protocol

class P0118_WeChatUserAuthentication_Test {

  //TODO

  /**
    * "C:\Program Files\Java\jdk1.8.0_66\bin\java" -Didea.launcher.port=7534 "-Didea.launcher.bin.path=C:\Program Files (x86)\JetBrains\IntelliJ IDEA 2016.1\bin" -Dfile.encoding=UTF-8 -classpath "C:\Program Files\Java\jdk1.8.0_66\jre\lib\charsets.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\deploy.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\access-bridge-64.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\cldrdata.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\dnsns.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\jaccess.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\jfxrt.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\localedata.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\nashorn.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\sunec.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\sunjce_provider.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\sunmscapi.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\sunpkcs11.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\ext\zipfs.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\javaws.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\jce.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\jfr.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\jfxswt.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\jsse.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\management-agent.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\plugin.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\resources.jar;C:\Program Files\Java\jdk1.8.0_66\jre\lib\rt.jar;C:\Users\19016\ideaworkspace\WeChat\SystemAdapterService\target\test-classes;C:\Users\19016\ideaworkspace\WeChat\SystemAdapterService\target\classes;C:\Users\19016\.m2\repository\org\scala-lang\scala-library\2.11.8\scala-library-2.11.8.jar;C:\Users\19016\.m2\repository\org\scala-lang\scala-compiler\2.11.8\scala-compiler-2.11.8.jar;C:\Users\19016\.m2\repository\org\scala-lang\scala-reflect\2.11.8\scala-reflect-2.11.8.jar;C:\Users\19016\.m2\repository\org\scala-lang\modules\scala-parser-combinators_2.11\1.0.4\scala-parser-combinators_2.11-1.0.4.jar;C:\Users\19016\.m2\repository\org\scala-lang\modules\scala-xml_2.11\1.0.5\scala-xml_2.11-1.0.5.jar;C:\Users\19016\.m2\repository\ch\qos\logback\logback-classic\1.1.6\logback-classic-1.1.6.jar;C:\Users\19016\.m2\repository\ch\qos\logback\logback-core\1.1.6\logback-core-1.1.6.jar;C:\Users\19016\.m2\repository\org\slf4j\slf4j-api\1.7.19\slf4j-api-1.7.19.jar;C:\Users\19016\.m2\repository\com\thoughtworks\xstream\xstream\1.4.8\xstream-1.4.8.jar;C:\Users\19016\.m2\repository\xmlpull\xmlpull\1.1.3.1\xmlpull-1.1.3.1.jar;C:\Users\19016\.m2\repository\xpp3\xpp3_min\1.1.4c\xpp3_min-1.1.4c.jar;C:\Users\19016\.m2\repository\commons-codec\commons-codec\1.10\commons-codec-1.10.jar;C:\Users\19016\.m2\repository\org\apache\commons\commons-lang3\3.4\commons-lang3-3.4.jar;C:\Users\19016\.m2\repository\com\typesafe\scala-logging\scala-logging-slf4j_2.11\2.1.2\scala-logging-slf4j_2.11-2.1.2.jar;C:\Users\19016\.m2\repository\com\typesafe\scala-logging\scala-logging-api_2.11\2.1.2\scala-logging-api_2.11-2.1.2.jar;C:\Users\19016\.m2\repository\org\scalatest\scalatest_2.11\2.2.6\scalatest_2.11-2.2.6.jar;C:\Users\19016\.m2\repository\org\mockito\mockito-all\1.10.19\mockito-all-1.10.19.jar;C:\Users\19016\.m2\repository\com\typesafe\config\1.2.1\config-1.2.1.jar;C:\Users\19016\.m2\repository\com\ibm\mq\mq\7.0.0\mq-7.0.0.jar;C:\Users\19016\.m2\repository\com\ibm\mq\jmqi\7.0.0\jmqi-7.0.0.jar;C:\Users\19016\.m2\repository\com\ibm\mq\connector\7.0.0\connector-7.0.0.jar;C:\Users\19016\.m2\repository\com\typesafe\play\play-json_2.11\2.3.10\play-json_2.11-2.3.10.jar;C:\Users\19016\.m2\repository\com\typesafe\play\play-iteratees_2.11\2.3.10\play-iteratees_2.11-2.3.10.jar;C:\Users\19016\.m2\repository\org\scala-stm\scala-stm_2.11\0.7\scala-stm_2.11-0.7.jar;C:\Users\19016\.m2\repository\com\typesafe\play\play-functional_2.11\2.3.10\play-functional_2.11-2.3.10.jar;C:\Users\19016\.m2\repository\com\typesafe\play\play-datacommons_2.11\2.3.10\play-datacommons_2.11-2.3.10.jar;C:\Users\19016\.m2\repository\joda-time\joda-time\2.3\joda-time-2.3.jar;C:\Users\19016\.m2\repository\org\joda\joda-convert\1.6\joda-convert-1.6.jar;C:\Users\19016\.m2\repository\com\fasterxml\jackson\core\jackson-annotations\2.3.2\jackson-annotations-2.3.2.jar;C:\Users\19016\.m2\repository\com\fasterxml\jackson\core\jackson-core\2.3.2\jackson-core-2.3.2.jar;C:\Users\19016\.m2\repository\com\fasterxml\jackson\core\jackson-databind\2.3.2\jackson-databind-2.3.2.jar;C:\Users\19016\ideaworkspace\WeChat\Encrypt\target\classes;C:\Program Files (x86)\JetBrains\IntelliJ IDEA 2016.1\lib\idea_rt.jar" com.intellij.rt.execution.application.AppMain com.yada.sdk.point.PointProtocolTestApp
[bf166cc4-c9b7-4e27-a957-2113e9cc016e] send to POINT...
[bf166cc4-c9b7-4e27-a957-2113e9cc016e] send to POINT msg :
000422<?xml version="1.0" encoding="ISO-8859-1"?>
<message>
  <head>
    <TranCode>0118</TranCode>
    <Version>1</Version>
    <TranDate>2016/05/11</TranDate>
    <ChannelCode>14</ChannelCode>
    <SerialNo>0511154011</SerialNo>
    <TranTime>15:40:11</TranTime>
  </head>
  <body>
    <EncryptCardNo>6TtDBRrHShem0RDifAu%2BgMG%2FW3djBSCXdX2pBItdRkmNjKWs%2FzAANNnJG0Sd1oDkXm%2FD%2BG3suPM%3D</EncryptCardNo>
  </body>
</message>
[bf166cc4-c9b7-4e27-a957-2113e9cc016e] receive from POINT...
[bf166cc4-c9b7-4e27-a957-2113e9cc016e] receive from POINT msg :
000432<?xml version="1.0" encoding="ISO-8859-1"?>
<message>
  <head>
    <Version>1</Version>
    <TranCode>0118</TranCode>
    <SerialNo>0511154011</SerialNo>
    <ChannelCode>14</ChannelCode>
    <TranDate>2016/05/11</TranDate>
    <TranTime>15:27:16</TranTime>
    <RtnCode>0000</RtnCode>
    <RtnMsg>成功</RtnMsg>
  </head>
  <body>
    <Sign>a63b04edc89b766d</Sign>
    <CardNo>mba_5149580068840943_vqn</CardNo>
  </body>
</message>

PointResp(Message(Head(Map(RtnCode -> 0000, TranCode -> 0118, Version -> 1, TranDate -> 2016/05/11, ChannelCode -> 14, SerialNo -> 0511154011, TranTime -> 15:27:16, RtnMsg -> 成功)),Some(Body(Map(CardNo -> mba_5149580068840943_vqn, Sign -> a63b04edc89b766d),List()))))

Process finished with exit code 0

    */

}
