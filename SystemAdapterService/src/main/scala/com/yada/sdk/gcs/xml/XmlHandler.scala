package com.yada.sdk.gcs.xml

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.{XmlFriendlyNameCoder, XppDriver}

/**
  * XML处理者
  */
private[gcs] class XmlHandler {
  val XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + java.lang.System.getProperty("line.separator")
  // 初始化stream
  private val xstream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")))
  // 注册定制的xml转换器
  xstream.registerConverter(new GCSConverter)
  xstream.registerConverter(new SystemConverter)
  xstream.registerConverter(new PageConverter)
  xstream.registerConverter(new ListConverter)
  // 声明短名称
  xstream.alias("GCS", classOf[GCS])

  def toXml(gcs: GCS): String = XmlHandler.XML_HEADER + xstream.toXML(gcs)

  def fromXml(xml: String): GCS = {
    xstream.fromXML(xml) match {
      case gcs: GCS ⇒ gcs
    }
  }
}

private[gcs] object XmlHandler extends XmlHandler {
  val GLOBAL = new XmlHandler
}
