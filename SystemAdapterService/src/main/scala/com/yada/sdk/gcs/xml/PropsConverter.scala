package com.yada.sdk.gcs.xml

import com.thoughtworks.xstream.converters.{MarshallingContext, UnmarshallingContext, Converter}
import com.thoughtworks.xstream.io.{HierarchicalStreamWriter, HierarchicalStreamReader}


abstract class PropsConverter extends Converter {
  override def marshal(source: scala.Any, writer: HierarchicalStreamWriter, context: MarshallingContext): Unit = {
    source match {
      case system: System ⇒ marshal(system.props, writer)
    }
  }

  def marshal(props: scala.collection.mutable.ListMap[String, String], writer: HierarchicalStreamWriter): Unit = {
    if (props.nonEmpty) {
      val (key, value) = props.head
      writer.startNode("prop")
      writer.addAttribute("key", key)
      writer.addAttribute("value", value)
      writer.endNode()
      marshal(props.tail, writer)
    }
  }

  override def unmarshal(reader: HierarchicalStreamReader, context: UnmarshallingContext): AnyRef = {
    val props = scala.collection.mutable.ListMap.empty[String, String]
    while (reader.hasMoreChildren) {
      reader.moveDown()
      props += reader.getAttribute("key") → reader.getAttribute("value")
      reader.moveUp()
    }
    generate(props)
  }

  /**
    * 生成对象
    *
    * @param props 对象中的属性集合
    * @return
    */
  def generate(props: scala.collection.mutable.ListMap[String, String]): AnyRef

  /**
    * 获取对象的属性
    *
    * @param source 源对象
    * @return
    */
  def getProps(source: scala.Any): scala.collection.mutable.ListMap[String, String]
}
