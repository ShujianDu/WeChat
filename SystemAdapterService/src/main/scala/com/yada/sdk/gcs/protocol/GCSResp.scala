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

  def pageList: Array[Map[String, String]] = {
    if (gcs.page.isEmpty) throw new RuntimeException("GCS resp page is not exist...")
    val list = gcs.page.get.list
    if (gcs.page.isEmpty) throw new RuntimeException("GCS resp page[list] is not exist...")
    list.get.entities.map(x => x.toMap)
  }

  def pageListValues[T](propsToObj: Map[String, String] => T): Array[T] = {
    val list = pageList
    list.map(values => {
      propsToObj(values)
    })
  }

  def pageListValues(keys: Array[String]): Array[Map[String, String]] = {
    pageListValues(map => {
      keys.map(key => key -> map.getOrElse(key, "")).toMap
    })
  }
}
