package com.yada.sdk.tgw.xml

import com.thoughtworks.xstream.converters.{MarshallingContext, UnmarshallingContext, Converter}
import com.thoughtworks.xstream.io.{HierarchicalStreamWriter, HierarchicalStreamReader}

/**
  * Created by locky on 2016/3/24.
  */
class TxnRspConverter extends Converter {
  override def marshal(source: scala.Any, writer: HierarchicalStreamWriter, context: MarshallingContext): Unit = {
    source match {
      case txnRsp: TxnRsp =>
        writer.startNode("head")
        context.convertAnother(txnRsp.head)
        writer.endNode()
        writer.startNode("data")
        context.convertAnother(txnRsp.data)
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
    TxnRsp(head, data)
  }

  override def canConvert(`type`: Class[_]): Boolean = `type`.isAssignableFrom(classOf[TxnRsp])
}

private[tgw] case class TxnRsp(head: Head, data: Data)