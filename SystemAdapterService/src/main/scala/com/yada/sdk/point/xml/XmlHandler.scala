package com.yada.sdk.point.xml

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.{XppDriver, XmlFriendlyNameCoder}

/**
  * 积分XML处理
  */
private[point] class XmlHandler {
  // 初始化stream
  private val xstream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")))
  // 注册定制的xml转换器
  xstream.registerConverter(new MessageConverter)
  xstream.registerConverter(new HeadConverter)
  xstream.registerConverter(new BodyConverter)
  // 声明短名称
  xstream.alias("message", classOf[Message])

  val XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + java.lang.System.getProperty("line.separator")

  def toXML(message: Message): String = {
    XML_HEADER + xstream.toXML(message)
  }

  def fromXML(xml: String): Message = {
    xstream.fromXML(xml) match {
      case msg: Message => msg
    }
  }
}

private[point] object XmlHandler {
  var GOLBAL = new XmlHandler
}