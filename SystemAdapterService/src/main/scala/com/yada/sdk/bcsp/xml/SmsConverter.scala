package com.yada.sdk.bcsp.xml

import com.thoughtworks.xstream.converters.{MarshallingContext, UnmarshallingContext, Converter}
import com.thoughtworks.xstream.io.{HierarchicalStreamWriter, HierarchicalStreamReader}

import scala.collection.mutable

/**
  * BCSP通讯报文根节点转换器
  */
class SmsConverter extends Converter {
  override def marshal(source: scala.Any, writer: HierarchicalStreamWriter, context: MarshallingContext): Unit = {
    source match {
      case sms: Sms =>
        sms.props.foreach(prop => {
          val (key, value) = prop
          writer.startNode(key)
          writer.setValue(value)
          writer.endNode()
        })
        sms.entity match {
          case Some(entity) =>
            writer.startNode("entity")
            context.convertAnother(entity)
            writer.endNode()
          case None =>
        }
    }
  }

  override def unmarshal(reader: HierarchicalStreamReader, context: UnmarshallingContext): AnyRef = {
    val props = mutable.Map.empty[String, String]
    var entity: Option[Entity] = None
    while (reader.hasMoreChildren) {
      reader.moveDown()
      reader.getNodeName match {
        case "entity" => context.convertAnother(reader, classOf[Entity]) match {
          case e: Entity => entity = Some(e)
        }
        case key: String => props += key -> reader.getValue
      }
      reader.moveUp()
    }
    Sms(props.toMap, entity)
  }

  override def canConvert(`type`: Class[_]): Boolean = `type`.isAssignableFrom(classOf[Sms])
}


case class Sms(props: Map[String, String], entity: Option[Entity])