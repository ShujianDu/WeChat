package com.yada.sdk.gcs.xml

import scala.collection.mutable


class PageConverter extends PropsConverter {
  override def generate(props: mutable.ListMap[String, String]): AnyRef = Page(props)

  override def getProps(source: Any): mutable.ListMap[String, String] = source match {
    case p: Page ⇒ p.props
  }

  override def canConvert(`type`: Class[_]): Boolean = `type`.isAssignableFrom(classOf[Page])
}

/**
  * 交易正文
  * 用于传输实际的交易请求/响应信息，即实际的交易信息。
  */
case class Page(props: mutable.ListMap[String, String])
