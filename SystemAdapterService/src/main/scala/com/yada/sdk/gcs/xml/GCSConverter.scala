package com.yada.sdk.gcs.xml

import com.thoughtworks.xstream.converters.{MarshallingContext, UnmarshallingContext, Converter}
import com.thoughtworks.xstream.io.{HierarchicalStreamWriter, HierarchicalStreamReader}

/**
  * GCS基本转换器
  */
class GCSConverter extends Converter {
  override def marshal(source: scala.Any, writer: HierarchicalStreamWriter, context: MarshallingContext): Unit = {
    source match {
      case gcs: GCS ⇒
        writer.addAttribute("transactionID", gcs.transactionID)
        writer.addAttribute("isRequest", gcs.isRequest.toString)
        writer.addAttribute("isResponse", gcs.isResponse.toString)
        writer.startNode("system")
        context.convertAnother(gcs.system)
        writer.endNode()
        writer.startNode("page")
        context.convertAnother(gcs.page)
        writer.endNode()
    }
  }

  override def unmarshal(reader: HierarchicalStreamReader, context: UnmarshallingContext): AnyRef = {
    val transactionID = reader.getAttribute("transactionID")
    val isRequest = reader.getAttribute("isRequest").toBoolean
    val isResponse = reader.getAttribute("isResponse").toBoolean
    reader.moveDown()
    val system = context.convertAnother(reader, classOf[System]) match {
      case s: System ⇒ s
    }
    reader.moveUp()
    reader.moveDown()
    val page = context.convertAnother(reader, classOf[Page]) match {
      case p: Page ⇒ p
    }
    reader.moveUp()
    GCS(transactionID, isRequest, isResponse, system, page)
  }


  override def canConvert(`type`: Class[_]): Boolean = `type`.isAssignableFrom(classOf[GCS])
}

/**
  * GCS报文根节点
  *
  * @param transactionID 交易ID
  * @param isRequest     是请求么
  * @param isResponse    是响应么
  * @param system        消息头
  * @param page          消息正文
  */
case class GCS(transactionID: String, isRequest: Boolean, isResponse: Boolean, system: System, page: Page)