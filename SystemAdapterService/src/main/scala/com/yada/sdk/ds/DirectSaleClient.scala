package com.yada.sdk.ds

import java.net.URL
import javax.xml.namespace.QName

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.slf4j.Logger
import org.slf4j.LoggerFactory

/**
  * 直销系统webservice客户端
  */
class DirectSaleClient(wsdlDocumentLocation: URL, serviceQName: QName, portQName: QName) {
  private val log = Logger(LoggerFactory.getLogger(classOf[DirectSaleClient]))

  protected val service = javax.xml.ws.Service.create(wsdlDocumentLocation, serviceQName)
  protected val port = service.getPort(portQName, classOf[WeChatAppPrj])

  /**
    * 向直销系统发送信息并接受响应
    * @param req 请求
    * @return
    */
  def send(req: String): String = {
    log.info(s"send to DirectSale...")
    log.debug(s"send to DirectSale...msg:\r\n$req")
    val resp = port.getWeChatAppPrj(req)
    log.info(s"receive from DirectSale...")
    log.debug(s"receive from DirectSale...msg:\r\n$resp")
    resp
  }
}

object DirectSaleClient {
  val GLOBAL = DirectSaleClient()

  def apply(wsdlDocumentLocation: URL, serviceQName: QName, portQName: QName): DirectSaleClient = new DirectSaleClient(wsdlDocumentLocation, serviceQName, portQName)

  def apply(): DirectSaleClient = {
    val factory = ConfigFactory.load()
    val wsdlURL = new URL(factory.getString("ds.wsdlURL"))
    val serviceQName = new QName("http://webService.forms.com", "WechatAppPrjService")
    val portQName = new QName("http://webService.forms.com", "WechatAppPrj")
    DirectSaleClient(wsdlURL, serviceQName, portQName)
  }
}