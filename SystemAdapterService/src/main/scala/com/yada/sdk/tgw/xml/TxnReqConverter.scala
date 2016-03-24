package com.yada.sdk.tgw.xml

import com.thoughtworks.xstream.converters.{MarshallingContext, UnmarshallingContext, Converter}
import com.thoughtworks.xstream.io.{HierarchicalStreamWriter, HierarchicalStreamReader}

/**
  * TGW请求报文转换器
  */
private[tgw] class TxnReqConverter extends Converter {
  override def marshal(source: scala.Any, writer: HierarchicalStreamWriter, context: MarshallingContext): Unit = {
    source match {
      case txnReq: TxnReq =>
        writer.startNode("head")
        context.convertAnother(txnReq.head)
        writer.endNode()
        writer.startNode("data")
        context.convertAnother(txnReq.data)
        writer.endNode()
    }
  }

  override def unmarshal(reader: HierarchicalStreamReader, context: UnmarshallingContext): AnyRef = {
    var head: Head = null
    var data: Data = null
    while (reader.hasMoreChildren) {
      reader.moveDown()
      reader.getNodeName match {
        case "head" => context.convertAnother(reader, classOf[Head]) match {
          case h: Head => head = h
        }
        case "data" => context.convertAnother(reader, classOf[Data]) match {
          case d: Data => data = d
        }
      }
    }
    TxnReq(head, data)
  }

  override def canConvert(`type`: Class[_]): Boolean = `type`.isAssignableFrom(classOf[TxnReq])
}

/**
  * TGW请求报文
  *
  * @param head 报文头
  * @param data 报文正文
  */
private[tgw] case class TxnReq(head: Head, data: Data)
