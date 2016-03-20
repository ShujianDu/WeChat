package com.yada.sdk.point.xml

import com.thoughtworks.xstream.converters.{MarshallingContext, UnmarshallingContext, Converter}
import com.thoughtworks.xstream.io.{HierarchicalStreamWriter, HierarchicalStreamReader}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * 积分报文正文
  */
class BodyConverter extends Converter {
  override def marshal(source: scala.Any, writer: HierarchicalStreamWriter, context: MarshallingContext): Unit = {
    source match {
      case body: Body =>
        body.props.foreach(prop => {
          val (key, value) = prop
          writer.startNode(key)
          writer.setValue(value)
          writer.endNode()
        })
        body.listProps.foreach(detail => {
          writer.startNode("detail")
          detail.foreach(prop => {
            val (key, value) = prop
            writer.startNode(key)
            writer.setValue(value)
            writer.endNode()
          })
          writer.endNode()
        })
    }
  }

  override def unmarshal(reader: HierarchicalStreamReader, context: UnmarshallingContext): AnyRef = {
    val tempListProps = ListBuffer.empty[Map[String, String]]
    val tempProps = mutable.Map.empty[String, String]
    while (reader.hasMoreChildren) {
      reader.moveDown()
      reader.getNodeName match {
        case "detail" =>
          val detailTempProps = mutable.Map.empty[String, String]
          while (reader.hasMoreChildren) {
            reader.moveDown()
            detailTempProps += reader.getNodeName -> reader.getValue
            reader.moveUp()
          }
          tempListProps += detailTempProps.toMap
        case propKey: String =>
          tempProps += propKey -> reader.getValue
      }
      reader.moveUp()
    }
    Body(tempProps.toMap, tempListProps.toList)
  }

  override def canConvert(`type`: Class[_]): Boolean = `type`.isAssignableFrom(classOf[Body])
}

/**
  * 积分消息正文
  */
case class Body(props: Map[String, String], listProps: List[Map[String, String]])
