package com.yada.sdk.gcs.xml

import com.thoughtworks.xstream.converters.UnmarshallingContext
import com.thoughtworks.xstream.io.{HierarchicalStreamReader, HierarchicalStreamWriter}

/**
  * GCS报文中Prop节点解析
  */
trait PropsConverter {

  protected def marshalProps(props: scala.collection.mutable.Map[String, String], writer: HierarchicalStreamWriter): Unit = {
    if (props.nonEmpty) {
      val (key, value) = props.head
      writer.startNode("prop")
      writer.addAttribute("key", key)
      writer.addAttribute("value", value)
      writer.endNode()
      marshalProps(props.tail, writer)
    }
  }

  protected def unmarshalProps(reader: HierarchicalStreamReader, context: UnmarshallingContext): scala.collection.mutable.Map[String, String] = {
    val props = scala.collection.mutable.Map.empty[String, String]
    while (reader.hasMoreChildren) {
      reader.moveDown()
      props += reader.getAttribute("key") → reader.getAttribute("value")
      reader.moveUp()
    }
    props
  }
}
