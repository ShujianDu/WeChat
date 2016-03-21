package com.yada.sdk.gcs.protocol

import java.util.Calendar

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.xml.{Page, System, GCS, XmlHandler}

import scala.collection.mutable

/**
  * GCS协议
  */
trait GCSReq[RespType <: GCSResp] {
  private val systemProps = mutable.Map.empty[String, String]
  private val pageProps = mutable.Map.empty[String, String]
  setSystemProp("transactionCode", transactionCode)
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

  def transactionID: String

  def pageKey: String

  def transactionSessionId: String

  def requestChannelId: String

  def transactionCode: String

  def isRequest = true

  def isResponse = false

  def setSystemProp(key: String, value: String): Unit = systemProps += key → value

  def setPageProps(key: String, value: String): Unit = pageProps += key → value

  def reqXML: String = {
    val gcs = GCS(transactionID, isRequest, isResponse, System(systemProps), Some(Page(pageKey, pageProps, None)))
    xmlHandler.toXml(gcs)
  }

  /**
    * GCS的客户端获取
    *
    * @return
    */
  protected def gcsClient = GCSClient.GLOBAL

  def send: RespType = {
    val resp = gcsClient.send(reqXML)
    generate(resp)
  }

  protected def generate(xml: String): RespType
}
