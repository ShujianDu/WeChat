package com.yada.sdk.gcs.protocol

import java.net.URL

import com.yada.sdk.gcs.GCSClient

/**
  * Created by locky on 2016/3/17.
  */
object Test extends App {
  //
  //  val ts010102Req = new TS010102Req(transactionSessionId = "93c4399ad67d925fa40d0693adb0a222",requestChannelId = "WX01")
  //  ts010102Req.setSystemProp("transactionCode", "410103")
  //  // 00 + yyyyMMddHHmmss
  //  ts010102Req.setSystemProp("txnTerminalCode", "")
  //  ts010102Req.setPageProps("cardNo", "5149580068840943")
  //  ts010102Req.setPageProps("currencyCode", "")
  //  val client = new GCSClient(new URL("http://22.7.14.91/tps1/ws/gateway?wsdl"))
  //  println(ts010102Req.toXml)
  //  val resp = client.send(ts010102Req.toXml)
  //  println("---------------------------------------")
  //  //  val resp = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<GCS transactionID=\"010102\" isRequest=\"false\" isResponse=\"true\"><system><prop key=\"transactionCode\" value=\"010102\"/><prop key=\"transactionNumber\" value=\"0020160318154242\"/><prop key=\"txnTraceNumber\" value=\"0011503507\"/><prop key=\"transactionSessionId\" value=\"93c4399ad67d925fa40d0693adb0a222\"/><prop key=\"requestChannelId\" value=\"WX01\"/><prop key=\"txnBankCode\" value=\"003\"/><prop key=\"txnProvinceCode\" value=\"\"/><prop key=\"txnBranchCode\" value=\"00003\"/><prop key=\"txnCounterCode\" value=\"\"/><prop key=\"txnTerminalCode\" value=\"\"/><prop key=\"txnUserCode\" value=\"wx00000\"/><prop key=\"localBankTxnRequestTime\" value=\"15:40:37\"/><prop key=\"localBankTxnRequestDate\" value=\"2016-03-18\"/><prop key=\"localBankTxnResponseTime\" value=\"15:40:37\"/><prop key=\"localBankTxnResponseDate\" value=\"2016-03-18\"/><prop key=\"bocBankTxnRequestTime\" value=\"15:40:37\"/><prop key=\"bocBankTxnRequestDate\" value=\"2016-03-18\"/><prop key=\"bocBankTxnResponseTime\" value=\"15:40:37\"/><prop key=\"bocBankTxnResponseDate\" value=\"2016-03-18\"/><prop key=\"returnCode\" value=\"+GC00000\"/><prop key=\"returnMessage\" value=\"Success\"/></system><page key=\"RP010102\"><list key=\"RP010101\" entityKey=\"accountList\" size=\"2\"><entity><prop key=\"accountId\" value=\"001A0213064FF77E\"/><prop key=\"accountRefNo\" value=\"2861695039487302\"/><prop key=\"automaticStatusCode\" value=\"\"/><prop key=\"automaticStatusSetDate\" value=\"\"/><prop key=\"currencyCode\" value=\"CNY\"/><prop key=\"institutionId\" value=\"BOCK\"/><prop key=\"issuingBranchId\" value=\"\"/><prop key=\"manualStatusCode\" value=\"NORM\"/><prop key=\"manualStatusSetDate\" value=\"\"/><prop key=\"productType\" value=\"PRMC\"/></entity><entity><prop key=\"accountId\" value=\"001A0213064FF780\"/><prop key=\"accountRefNo\" value=\"7821440695701839\"/><prop key=\"automaticStatusCode\" value=\"\"/><prop key=\"automaticStatusSetDate\" value=\"\"/><prop key=\"currencyCode\" value=\"USD\"/><prop key=\"institutionId\" value=\"BOCK\"/><prop key=\"issuingBranchId\" value=\"\"/><prop key=\"manualStatusCode\" value=\"NORM\"/><prop key=\"manualStatusSetDate\" value=\"\"/><prop key=\"productType\" value=\"PRMC\"/></entity></list></page></GCS>"
  //  println(resp)
  //  val ts010102Resp = new TS010102Resp(resp)
  //  println("---------------------------------------")
  //  println(ts010102Resp)

  val req = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<GCS transactionID=\"010302\" isRequest=\"true\" isResponse=\"false\">\n    <system>\n        <prop key=\"transactionCode\" value=\"010302\"/>\n        <prop key=\"transactionNumber\" value=\"0020160318183300\"/>\n        <prop key=\"transactionSessionId\" value=\"93c4399ad67d925fa40d0693adb0a222\"/>\n        <prop key=\"requestChannelId\" value=\"WX01\"/>\n        <prop key=\"txnBankCode\" value=\"003\"/>\n        <prop key=\"txnBranchCode\" value=\"00003\"/>\n        <prop key=\"txnProvinceCode\" value=\"\"/>\n        <prop key=\"txnTerminalCode\" value=\"\"/>\n        <prop key=\"txnCounterCode\" value=\"\"/>\n        <prop key=\"txnUserCode\" value=\"wx0103023\"/>\n    </system>\n    <page key=\"RQ010302\">\n        <prop key=\"statementNo\" value=\"3\"/>\n        <prop key=\"accountId\" value=\"001A0213064FF77E\"/>\n    </page>\n</GCS>"
  val client = new GCSClient(new URL("http://22.7.14.91/tps1/ws/gateway?wsdl"))
  client.send(req)
}
