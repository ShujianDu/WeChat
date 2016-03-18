package com.yada.sdk.gcs

import java.net.URL
import javax.xml.namespace.QName

/**
  * GCS客户端
  *
  * @param wsdlDocumentLocation wsdl文件的地址
  * @param serviceQName         服务名称
  * @param portQName            端口名称
  */
class Gcs_Client(wsdlDocumentLocation: URL, serviceQName: QName, portQName: QName) {
  /**
    * GCS客户端
    *
    * @param wsdlDocumentLocation wsdl文件的地址
    */
  def this(wsdlDocumentLocation: URL) {
    this(wsdlDocumentLocation, new QName("www.boc.com.cn/gcs", "GcsGatewayService"), new QName("www.boc.com.cn/gcs", "GatewayPort"))
  }

  private val service = javax.xml.ws.Service.create(wsdlDocumentLocation, serviceQName)
  private val port = service.getPort(portQName, classOf[Gateway])

  def send(request: String): String = {
    port.service(request)
  }
}