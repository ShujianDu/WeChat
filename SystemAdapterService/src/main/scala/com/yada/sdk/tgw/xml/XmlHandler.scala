package com.yada.sdk.tgw.xml

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.{XmlFriendlyNameCoder, XppDriver}

/**
  * TGW的XML处理者
  */
private[tgw] class XmlHandler {
  // 初始化stream
  private val xstream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")))
  // 注册定制的xml转换器
  xstream.registerConverter(new TxnReqConverter)
  xstream.registerConverter(new TxnRspConverter)
  xstream.registerConverter(new HeadConverter)
  xstream.registerConverter(new DataConverter)
  // 声明短名称
  xstream.alias("txnreq", classOf[TxnReq])
  xstream.alias("txnrsp", classOf[TxnRsp])

  val XML_HEADER = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\r\n"

  def toXml(txnReq: TxnReq): String = {
    xstream.toXML(txnReq)
  }

  /**
    * 从XML到一个对象
    *
    * @param xml xml的字符串
    * @return
    */
  def fromXML(xml: String): TxnRsp = {
    xstream.fromXML(xml) match {
      case txnRsp: TxnRsp => txnRsp
    }
  }
}

private[tgw] object XmlHandler