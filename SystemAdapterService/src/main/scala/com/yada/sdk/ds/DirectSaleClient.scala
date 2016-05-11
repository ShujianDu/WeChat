package com.yada.sdk.ds

import java.io.IOException
import java.net.URL
import java.util.UUID
import javax.xml.namespace.QName

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.slf4j.Logger
import com.yada.sdk.commons.SystemIOException
import com.yada.sdk.ds.json.{Data, JsonHandler}
import org.slf4j.LoggerFactory

/**
  * 直销系统webservice客户端
  */
class DirectSaleClient(wsdlDocumentLocation: URL, serviceQName: QName, portQName: QName) {
  private val log = Logger(LoggerFactory.getLogger(classOf[DirectSaleClient]))

  def this(wsdlDocumentLocation: URL) {
    this(wsdlDocumentLocation, new QName("http://webService.forms.com", "WechatAppPrjService"), new QName("http://webService.forms.com", "WechatAppPrj"))
  }

  def this() {
    this(new URL(ConfigFactory.load().getString("DS.wsdlDocumentLocation")))
  }

  protected val service = javax.xml.ws.Service.create(wsdlDocumentLocation, serviceQName)
  protected val port = service.getPort(portQName, classOf[WeChatAppPrj])
  protected val jsonHandler: JsonHandler = JsonHandler.GLOBAL

  /**
    * 向直销系统发送信息并接受响应
    *
    * @param reqData 请求
    * @return
    */
  def send(reqData: Data): Data = {
    try {
      val req = jsonHandler.toJSON(reqData)
      val uuid = UUID.randomUUID().toString
      log.info(s"[$uuid] send to DirectSale...")
      log.debug(s"[$uuid] send to DirectSale...msg:\r\n$req")
      val resp = port.getWechatAppPrj(req)
      log.info(s"[$uuid] receive from DirectSale...")
      log.debug(s"[$uuid] receive from DirectSale...msg:\r\n$resp")
      if (resp == null || resp.trim == "") throw new RuntimeException(s"[$uuid] resp is empty")
      jsonHandler.fromJSON(resp)
    } catch {
      case ioe: IOException => throw SystemIOException("DS", "", ioe)
    }

  }
}

object DirectSaleClient {
  private[this] var _GLOBAL: DirectSaleClient = Class.forName(ConfigFactory.load().getString("DS.client")).newInstance().asInstanceOf[DirectSaleClient]

  def GLOBAL: DirectSaleClient = _GLOBAL

  def GLOBAL_=(value: DirectSaleClient): Unit = {
    _GLOBAL = value
  }
}