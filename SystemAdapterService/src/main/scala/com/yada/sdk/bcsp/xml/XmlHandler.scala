package com.yada.sdk.bcsp.xml

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.{XmlFriendlyNameCoder, XppDriver}

/**
  * xml处理器
  */
private[bcsp] class XmlHandler {
  val XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + java.lang.System.getProperty("line.separator")
  // 初始化stream
  private val xstream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")))
  // 注册定制的xml转换器
  xstream.registerConverter(new SmsConverter)
  xstream.registerConverter(new EntityConverter)
  // 声明短名称
  xstream.alias("sms", classOf[Sms])

  /**
    * 转换sms对象到xml字符串
    *
    * @param sms sms对象
    * @return
    */
  def toXml(sms: Sms): String = {
    XML_HEADER + xstream.toXML(sms)
  }

  /**
    * 转换xml到sms对象
    *
    * @param xml 要解析的XML
    * @return
    */
  def fromXml(xml: String): Sms = {
    xstream.fromXML(xml) match {
      case sms: Sms => sms
    }
  }
}
