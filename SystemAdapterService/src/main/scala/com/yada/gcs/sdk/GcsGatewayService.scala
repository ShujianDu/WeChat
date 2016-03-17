package com.yada.gcs.sdk

import java.net.URL
import javax.xml.namespace.QName


import java.net.URL
import javax.xml.namespace.QName
import javax.xml.ws.{WebEndpoint, WebServiceClient}

class GcsGatewayService(url: URL, serviceQName: QName, portQName: QName) {
  @WebEndpoint(name = "GatewayPort") def getGatewayPort: Gateway = {
    super.getPort(GatewayPort, classOf[Gateway])
  }
}

object Test extends App {
  val msg = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<GCS transactionID=\"TS011172\" isRequest=\"ture\" isResponse=\"false\">\n" + "  <system>\n" + "    <prop key=\"transactionCode\" value=\"011172\" />\n" + "    <prop key=\"transactionNumber\" value=\"201603151648550020160315164855\" />\n" + "    <prop key=\"transactionSessionId\" value=\"93c4399ad67d925fa40d0693adb0a222\" />\n" + "    <prop key=\"requestChannelId\" value=\"WX01\" />\n" + "    <prop key=\"txnBankCode\" value=\"003\" />\n" + "    <prop key=\"txnBranchCode\" value=\"00003\" />\n" + "    <prop key=\"txnProvinceCode\" value=\"\" />\n" + "    <prop key=\"txnTerminalCode\" value=\"\" />\n" + "    <prop key=\"txnCounterCode\" value=\"\" />\n" + "    <prop key=\"txnUserCode\" value=\"wx000000\" />\n" + "  </system>\n" + "  <page key=\"RQ011172\">\n" + "    <prop key=\"accountKeyOne\" value=\"1111111111111111\" />\n" + "    <prop key=\"accountKeyTwo\" value=\"1111111111111111\" />\n" + "    <prop key=\"currencyCode\" value=\"CNY\" />\n" + "    <prop key=\"transactionCode\" value=\"3110\" />\n" + "    <prop key=\"billDateNo\" value=\"111111111\" />\n" + "    <prop key=\"transactionNo\" value=\"111111111\" />\n" + "    <prop key=\"transactionAmount\" value=\"5555\" />\n" + "    <prop key=\"cardNo\" value=\"4693801111405010\" />\n" + "    <prop key=\"accountNoID\" value=\"11111111111111111111\" />\n" + "    <prop key=\"mcc\" value=\"5311\" />\n" + "    <prop key=\"installmentPeriods\" value=\"3\" />\n" + "    <prop key=\"installmentPlan\" value=\"EP01\" />\n" + "    <prop key=\"merchantID\" value=\"0000000\" />\n" + "    <prop key=\"isfeeFlag\" value=\"1\" />\n" + "    <prop key=\"channelId\" value=\"A\" />\n" + "  </page>\n" + "</GCS>"
  val SERVICE: QName = new QName("www.boc.com.cn/gcs", "GcsGatewayService")
  val GatewayPort: QName = new QName("www.boc.com.cn/gcs", "GatewayPort")
  val url = new URL("http://22.7.14.91/tps1/ws/gateway?wsdl")
  val s = javax.xml.ws.Service.create(url, SERVICE)
  val p = s.getPort(GatewayPort, classOf[Gateway])
  val r = p.service(msg)
  println(r)
}