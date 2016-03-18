package com.yada.sdk.gcs.xml

import com.thoughtworks.xstream.converters.{MarshallingContext, UnmarshallingContext, Converter}
import com.thoughtworks.xstream.io.{HierarchicalStreamWriter, HierarchicalStreamReader}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
  * Created by locky on 2016/3/18.
  */
class ListConverter extends Converter {
  override def marshal(source: scala.Any, writer: HierarchicalStreamWriter, context: MarshallingContext): Unit = {
    throw new UnsupportedOperationException("this converter can only unmarshal xml...")
  }

  override def unmarshal(reader: HierarchicalStreamReader, context: UnmarshallingContext): AnyRef = {
    val key = reader.getAttribute("key")
    val entityKey = reader.getAttribute("entityKey")
    val size = reader.getAttribute("size").toInt
    val buf = ArrayBuffer.empty[mutable.Map[String, String]]
    while (reader.hasMoreChildren) {
      reader.moveDown()
      buf += unmarshalEntity(reader)
      reader.moveUp()
    }
    List(key, entityKey, size, buf.toArray)
  }

  def unmarshalEntity(reader: HierarchicalStreamReader): mutable.Map[String, String] = {
    val props = mutable.Map.empty[String, String]
    while (reader.hasMoreChildren) {
      reader.moveDown()
      props += reader.getAttribute("key") → reader.getAttribute("value")
      reader.moveUp()
    }
    props
  }

  override def canConvert(`type`: Class[_]): Boolean = `type`.isAssignableFrom(classOf[List])
}

case class List(key: String, entityKey: String, size: Int, entities: Array[mutable.Map[String, String]])
