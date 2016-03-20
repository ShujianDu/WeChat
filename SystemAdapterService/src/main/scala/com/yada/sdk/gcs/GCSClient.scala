package com.yada.sdk.gcs

import java.net.URL
import java.util.Properties
import javax.xml.namespace.QName

import com.typesafe.scalalogging.slf4j.Logger
import org.slf4j.LoggerFactory

/**
  * GCS客户端
  *
  * @param wsdlDocumentLocation wsdl文件的地址
  * @param serviceQName         服务名称
  * @param portQName            端口名称
  */
class GCSClient(wsdlDocumentLocation: URL, serviceQName: QName, portQName: QName) {
  private val log = Logger(LoggerFactory.getLogger(classOf[GCSClient]))

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
    log.info(s"send to GCS...\r\n$request")
    val resp = port.service(request)
    log.info(s"rece from GCS...\r\n$resp")
    resp
  }
}

object GCSClient {
  private val prop = new Properties()
  prop.load(this.getClass.getClassLoader.getResourceAsStream("GCS.properties"))
  private val wsdlDocumentLocation = new URL(prop.getProperty("wsdlDocumentLocation"))
  var GLOBAL_GCS_CLIENT = new GCSClient(wsdlDocumentLocation)

  def apply(): GCSClient = {
    GLOBAL_GCS_CLIENT
  }
}