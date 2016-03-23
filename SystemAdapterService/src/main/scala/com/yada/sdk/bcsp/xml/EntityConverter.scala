package com.yada.sdk.bcsp.xml

import com.thoughtworks.xstream.converters.{MarshallingContext, UnmarshallingContext, Converter}
import com.thoughtworks.xstream.io.{HierarchicalStreamWriter, HierarchicalStreamReader}

import scala.collection.mutable.ListBuffer

/**
  * BCSP报文entity内容转换器
  */
class EntityConverter extends Converter {
  override def marshal(source: scala.Any, writer: HierarchicalStreamWriter, context: MarshallingContext): Unit = {
    source match {
      case entity: Entity =>
        entity.items.foreach(item => {
          writer.startNode("item")
          writer.setValue(item)
          writer.endNode()
        })
    }
  }

  override def unmarshal(reader: HierarchicalStreamReader, context: UnmarshallingContext): AnyRef = {
    val items = ListBuffer.empty[String]
    while (reader.hasMoreChildren) {
      reader.moveDown()
      items += reader.getValue
      reader.moveUp()
    }
    Entity(items.toList)
  }

  override def canConvert(`type`: Class[_]): Boolean = `type`.isAssignableFrom(classOf[Entity])
}

/**
  * BCSP报文内entity标签
  *
  * @param items entity标签下的item标签的值
  */
case class Entity(items: List[String])