package com.yada.sdk.gcs.protocol

import com.yada.sdk.gcs.xml.{XmlHandler, GCS, Page, System}

/**
  * GCS请求报文
  */
trait GCSReq {

  def xmlHandler = XmlHandler

  def isRequest = true

  def isResponse = false

  def transactionID: String

  private val systemProps = scala.collection.mutable.ListMap.empty[String, String]

  def setSystemProp(key: String, value: String): Unit = systemProps += key → value

  private val pageProps = scala.collection.mutable.ListMap.empty[String, String]

  def setPageProps(key: String, value: String): Unit = pageProps += key → value

  def toXml: String = {
    val gcs = GCS(transactionID, isRequest, isResponse, System(systemProps), Some(Page(pageKey, pageProps)))
    xmlHandler.toXml(gcs)
  }

  def pageKey: String
}
