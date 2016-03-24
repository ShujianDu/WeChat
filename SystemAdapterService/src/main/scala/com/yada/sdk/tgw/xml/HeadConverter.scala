package com.yada.sdk.tgw.xml

import com.thoughtworks.xstream.converters.{MarshallingContext, UnmarshallingContext, Converter}
import com.thoughtworks.xstream.io.{HierarchicalStreamWriter, HierarchicalStreamReader}

import scala.collection.mutable

/**
  * TGW报文头转换
  */
private[tgw] class HeadConverter extends Converter {
  override def marshal(source: scala.Any, writer: HierarchicalStreamWriter, context: MarshallingContext): Unit = {
    source match {
      case head: Head =>
        head.props foreach {
          prop => {
            val (key, value) = prop
            writer.startNode(key)
            writer.setValue(value)
            writer.endNode()
          }
        }
    }
  }

  override def unmarshal(reader: HierarchicalStreamReader, context: UnmarshallingContext): AnyRef = {
    val props = mutable.Map.empty[String, String]
    while (reader.hasMoreChildren) {
      reader.moveDown()
      props += reader.getNodeName -> reader.getValue
      reader.moveUp()
    }
    Head(props.toMap)
  }

  override def canConvert(`type`: Class[_]): Boolean = `type`.isAssignableFrom(classOf[Head])
}

private[tgw] case class Head(props: Map[String, String])