package com.yada.sdk.gcs.xml

import com.thoughtworks.xstream.converters.{Converter, MarshallingContext, UnmarshallingContext}
import com.thoughtworks.xstream.io.{HierarchicalStreamReader, HierarchicalStreamWriter}

class SystemConverter extends Converter with PropsConverter {


  override def canConvert(`type`: Class[_]): Boolean = `type`.isAssignableFrom(classOf[System])

  override def marshal(source: scala.Any, writer: HierarchicalStreamWriter, context: MarshallingContext): Unit = {
    source match {
      case system: System ⇒ marshalProps(system.props, writer)
    }
  }

  override def unmarshal(reader: HierarchicalStreamReader, context: UnmarshallingContext): AnyRef = {
    val props = unmarshalProps(reader, context)
    System(props)
  }
}

/**
  * GCS报文头
  * 用于传输系统信息，如终端号、渠道号、交易发生日期、流水号等系统信息，即报文头信息
  */
case class System(props: scala.collection.mutable.ListMap[String, String])
