package com.yada.sdk.tgw.xml

import com.thoughtworks.xstream.converters.{MarshallingContext, UnmarshallingContext, Converter}
import com.thoughtworks.xstream.io.{HierarchicalStreamWriter, HierarchicalStreamReader}

import scala.collection.mutable

/**
  * TGW报文正文转换器
  */
private[tgw] class DataConverter extends Converter {
  override def marshal(source: scala.Any, writer: HierarchicalStreamWriter, context: MarshallingContext): Unit = {
    source match {
      case data: Data =>
        data.props foreach {
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
    Data(props.toMap)
  }

  override def canConvert(`type`: Class[_]): Boolean = `type`.isAssignableFrom(classOf[Data])
}

private[tgw] case class Data(props: Map[String, String])
