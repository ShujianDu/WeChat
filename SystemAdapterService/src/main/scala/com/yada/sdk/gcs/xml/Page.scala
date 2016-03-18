package com.yada.sdk.gcs.xml

import com.thoughtworks.xstream.converters.{Converter, MarshallingContext, UnmarshallingContext}
import com.thoughtworks.xstream.io.{HierarchicalStreamWriter, HierarchicalStreamReader}

import scala.collection.mutable


class PageConverter extends Converter with PropsConverter {


  override def marshal(source: Any, writer: HierarchicalStreamWriter, context: MarshallingContext): Unit = {
    source match {
      case page: Page ⇒
        writer.addAttribute("key", page.key)
        marshalProps(page.props, writer)
    }
  }

  override def unmarshal(reader: HierarchicalStreamReader, context: UnmarshallingContext): AnyRef = {
    val key = reader.getAttribute("key")
    val props = unmarshalProps(reader, context)
    Page(key, props)
  }


  override def canConvert(`type`: Class[_]): Boolean = `type`.isAssignableFrom(classOf[Page])
}

/**
  * 交易正文
  * 用于传输实际的交易请求/响应信息，即实际的交易信息。
  */
case class Page(key: String, props: mutable.ListMap[String, String])
