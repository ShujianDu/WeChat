package com.yada.sdk.gcs.protocol

import com.yada.sdk.gcs.xml.{GCS, XmlHandler}

/**
  * Created by locky on 2016/3/17.
  */
trait GCSResp {
  def xmlHandler = new XmlHandler

  private var gcs: Option[GCS] = None

  def fromXml(xml: String): Unit = {
    gcs = Some(xmlHandler.fromXml(xml))
  }

  def systemValue(key: String): String = {
    if (gcs.isEmpty) throw new RuntimeException("not initialization...")
    gcs.get.system.props.getOrElse(key, "")
  }

  def pageValue(key: String): String = {
    if (gcs.isEmpty) throw new RuntimeException("not initialization...")
    gcs.get.page.props.getOrElse(key, "")
  }

  /**
    * 是否已经初始化
    *
    * @return true已经初始化
    */
  def isInitialized = gcs.nonEmpty
}
