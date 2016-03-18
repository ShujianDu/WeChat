package com.yada.sdk.gcs.protocol

import java.util.Calendar

import com.yada.sdk.gcs.xml.{XmlHandler, GCS, Page, System}

import scala.collection.mutable

/**
  * GCS请求报文
  */
abstract class GCSReq(transactionSessionId: String, requestChannelId: String) {
  private val systemProps = mutable.Map.empty[String, String]
  private val pageProps = mutable.Map.empty[String, String]
  // 00 + yyyyMMddHHmmss
  setSystemProp("transactionNumber", String.format("00%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance.getTime))
  setSystemProp("transactionSessionId", transactionSessionId)
  setSystemProp("requestChannelId", requestChannelId)
  setSystemProp("txnBranchCode", "00003")
  setSystemProp("txnBankCode", "003")
  setSystemProp("txnProvinceCode", "")
  setSystemProp("txnTerminalCode", "")
  setSystemProp("txnCounterCode", "")
  setSystemProp("txnUserCode", TxnUserCodeGenerate.get)

  def xmlHandler = XmlHandler

  def isRequest = true

  def isResponse = false

  def transactionID: String

  def setSystemProp(key: String, value: String): Unit = systemProps += key → value

  def setPageProps(key: String, value: String): Unit = pageProps += key → value

  def toXml: String = {
    val gcs = GCS(transactionID, isRequest, isResponse, System(systemProps), Some(Page(pageKey, pageProps, None)))
    xmlHandler.toXml(gcs)
  }

  def pageKey: String
}
