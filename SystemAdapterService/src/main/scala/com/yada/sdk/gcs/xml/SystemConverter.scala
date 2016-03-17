package com.yada.sdk.gcs.xml

import scala.collection.mutable

class SystemConverter extends PropsConverter {


  override def canConvert(`type`: Class[_]): Boolean = `type`.isAssignableFrom(classOf[System])

  override def generate(props: mutable.ListMap[String, String]): AnyRef = System(props)

  override def getProps(source: Any): mutable.ListMap[String, String] = source match {
    case s: System ⇒ s.props
  }
}

/**
  * GCS报文头
  * 用于传输系统信息，如终端号、渠道号、交易发生日期、流水号等系统信息，即报文头信息
  */
case class System(props: scala.collection.mutable.ListMap[String, String])
