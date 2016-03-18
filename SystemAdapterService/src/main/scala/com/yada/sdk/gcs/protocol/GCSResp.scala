package com.yada.sdk.gcs.protocol

import com.yada.sdk.gcs.xml.XmlHandler

/**
  * GCS响应报文
  */
abstract class GCSResp(xml: String) {
  val gcs = xmlHandler.fromXml(xml)

  val isSuccess: Boolean = systemValue("returnCode") == "+GC00000"

  def xmlHandler = XmlHandler

  def systemValue(key: String): String = {
    gcs.system.props.getOrElse(key, "")
  }

  def pageValue(key: String): String = {
    if (gcs.page.isEmpty) throw new RuntimeException("GCS resp page is not exist...")
    gcs.page.get.props.getOrElse(key, "")
  }
}
