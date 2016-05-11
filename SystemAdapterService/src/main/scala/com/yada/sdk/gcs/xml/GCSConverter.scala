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
        gcs.page match {
          case Some(page) ⇒
            writer.startNode("page")
            context.convertAnother(page)
            writer.endNode()
          case None ⇒ // 什么都不做
        }
    }
  }

  override def unmarshal(reader: HierarchicalStreamReader, context: UnmarshallingContext): AnyRef = {
    val transactionID = reader.getAttribute("transactionID")
    val (isRequest, isResponse) = if (reader.getAttributeCount < 2) {
      // 只有响应才有可能不存在isRequest和isResponse
      false -> true
    } else {
      reader.getAttribute("isRequest").toBoolean -> reader.getAttribute("isResponse").toBoolean
    }

    var system: System = null
    var page: Option[Page] = None
    while (reader.hasMoreChildren) {
      // 进入节点
      reader.moveDown()
      reader.getNodeName match {
        case "system" ⇒
          system = context.convertAnother(reader, classOf[System]) match {
            case s: System ⇒ s
          }
        case "page" ⇒
          page = context.convertAnother(reader, classOf[Page]) match {
            case p: Page ⇒ Some(p)
          }
      }
      // 离开节点
      reader.moveUp()
    }
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
case class GCS(transactionID: String, isRequest: Boolean, isResponse: Boolean, system: System, page: Option[Page])