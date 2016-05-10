package com.yada.sdk.bcsp

import java.io.IOException
import java.net.{InetAddress, InetSocketAddress, Socket}
import java.util.UUID

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.slf4j.Logger
import com.yada.sdk.bcsp.xml.{Sms, XmlHandler}
import com.yada.sdk.commons.SystemIOException
import org.slf4j.LoggerFactory

import scala.io.Source

/**
  * BCSP通讯客户端,同步短链接
  */
private[bcsp] class BCSPClient extends IBCSPClient {
  val log = Logger(LoggerFactory.getLogger(classOf[BCSPClient]))
  val xmlHandler = XmlHandler.GLOBAL
  val address = new InetSocketAddress(ConfigFactory.load().getString("BCSP.ip"), ConfigFactory.load().getInt("BCSP.port"))
  val connectTimeout = ConfigFactory.load().getInt("BCSP.connectTimeout")
  val readTimeout = ConfigFactory.load().getInt("BCSP.readTimeout")

  /**
    * 发送短信
    *
    * @param sms 短信
    * @return
    */
  override def send(sms: Sms): Sms = {
    val reqXML = xmlHandler.toXml(sms)
    val reqXMLLen = reqXML.length
    val req = f"$reqXMLLen%06d$reqXML"
    val uuid = UUID.randomUUID().toString
    val socket = new Socket
    try {
      socket.connect(address, connectTimeout)
      socket.setSoTimeout(readTimeout)
      log.info(s"[$uuid] send to BCSP...")
      log.debug(s"[$uuid] send to BCSP...msg:\r\n$req")
      socket.getOutputStream.write(req.getBytes())
      socket.getOutputStream.flush()
      val source = Source.fromInputStream(socket.getInputStream)
      val resp = source.mkString
      log.info(s"[$uuid] receive from BCSP...")
      log.debug(s"[$uuid] receive from BCSP...msg:\r\n$resp")
      val respXML = resp.substring(6)
      xmlHandler.fromXml(respXML)
    } catch {
      case ioe: IOException => throw SystemIOException("BCSP", address.toString, ioe)
      case e: Exception => throw new RuntimeException(s"BCSP[$address] has a error...", e)
    } finally {
      socket.close()
    }
  }
}
