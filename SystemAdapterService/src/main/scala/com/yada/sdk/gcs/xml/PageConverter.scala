package com.yada.sdk.gcs.xml

import com.thoughtworks.xstream.converters.{Converter, MarshallingContext, UnmarshallingContext}
import com.thoughtworks.xstream.io.{HierarchicalStreamReader, HierarchicalStreamWriter}

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
    val props = mutable.Map.empty[String, String]
    var list: Option[List] = None
    while (reader.hasMoreChildren) {
      reader.moveDown()
      reader.getNodeName match {
        case "list" ⇒
          list = context.convertAnother(reader, classOf[List]) match {
            case l: List ⇒ Some(l)
          }
        case "prop" ⇒
          props += reader.getAttribute("key") → reader.getAttribute("value")
        case "entity" => //TS010052 永久挂失的特殊处理。TS010052文档没有报文体返回的。GCS反馈不用管报文体。其他交易未发现该域。如有其他需要处理的请联系开发人员-zqd/jfm

      }
      reader.moveUp()
    }
    Page(key, props, list)
  }


  override def canConvert(`type`: Class[_]): Boolean = `type`.isAssignableFrom(classOf[Page])
}

/**
  * 交易正文
  * 用于传输实际的交易请求/响应信息，即实际的交易信息。
  */
case class Page(key: String, props: mutable.Map[String, String], list: Option[List])
