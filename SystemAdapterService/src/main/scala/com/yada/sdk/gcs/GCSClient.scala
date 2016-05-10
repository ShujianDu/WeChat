package com.yada.sdk.gcs

import java.net.URL
import javax.xml.namespace.QName

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.slf4j.Logger
import com.yada.sdk.commons.SystemIOException
import org.slf4j.LoggerFactory

/**
  * GCS客户端
  *
  */
class GCSClient(wsdlDocumentLocation: URL, serviceQName: QName, portQName: QName) {
  private val log = Logger(LoggerFactory.getLogger(classOf[GCSClient]))

  def this(wsdlDocumentLocation: URL) {
    this(wsdlDocumentLocation, new QName("www.boc.com.cn/gcs", "GcsGatewayService"), new QName("www.boc.com.cn/gcs", "GatewayPort"))
  }

  protected val service = javax.xml.ws.Service.create(wsdlDocumentLocation, serviceQName)
  protected val port = service.getPort(portQName, classOf[Gateway])

  def send(request: String): String = {
    try {
      log.info(s"send to GCS...\r\n$request")
      val resp = port.service(request)
      log.info(s"rece from GCS...\r\n$resp")
      resp
    } catch {
      case e: GatewayException => throw SystemIOException("GCS", "", e)
    }
  }
}

object GCSClient {
  private val config = ConfigFactory.load
  val wsdlDocumentLocation: URL = new URL(config.getString("GCS.wsdlDocumentLocation"))
  val GLOBAL = Class.forName(config.getString("GCS.client")).getConstructor(classOf[URL]).newInstance(wsdlDocumentLocation).asInstanceOf[GCSClient]
}