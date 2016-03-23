package com.yada.sdk.point.xml

import com.thoughtworks.xstream.converters.{MarshallingContext, UnmarshallingContext, Converter}
import com.thoughtworks.xstream.io.{HierarchicalStreamWriter, HierarchicalStreamReader}

class MessageConverter extends Converter {
  override def marshal(source: scala.Any, writer: HierarchicalStreamWriter, context: MarshallingContext): Unit = {
    source match {
      case message: Message =>
        writer.startNode("head")
        context.convertAnother(message.head)
        writer.endNode()
        message.body match {
          case None =>
          case Some(body) =>
            writer.startNode("body")
            context.convertAnother(body)
            writer.endNode()
        }

    }
  }

  override def unmarshal(reader: HierarchicalStreamReader, context: UnmarshallingContext): AnyRef = {
    var head: Option[Head] = None
    var body: Option[Body] = None
    while (reader.hasMoreChildren) {
      reader.moveDown()
      reader.getNodeName match {
        case "head" => head = context.convertAnother(reader, classOf[Head]) match {
          case h: Head => Some(h)
        }
        case "body" =>
          body = context.convertAnother(reader, classOf[Body]) match {
            case b: Body => Some(b)
          }
      }
      reader.moveUp()
    }
    Message(head.get, body)
  }

  override def canConvert(`type`: Class[_]): Boolean = `type`.isAssignableFrom(classOf[Message])
}

/**
  * 积分信息根节点
  */
case class Message(head: Head, body: Option[Body])
