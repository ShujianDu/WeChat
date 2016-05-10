package com.yada.sdk.tgw

import java.io.IOException
import java.net.{InetSocketAddress, Socket}
import java.util.UUID

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.slf4j.Logger
import com.yada.sdk.commons.SystemIOException
import com.yada.sdk.tgw.xml.{TxnReq, TxnRsp, XmlHandler}
import org.slf4j.LoggerFactory

import scala.io.Source

/**
  * TGW客户端
  */
class TGWClient extends ITGWClient {
  private val log = Logger(LoggerFactory.getLogger(classOf[TGWClient]))
  private val xmlHandler: XmlHandler = new XmlHandler
  private val (address, connectTimeout, readTimeout) = init()

  /**
    * 发送
    *
    * @param txnReq 请求报文的xml对象
    * @return
    */
  override def send(txnReq: TxnReq): TxnRsp = {
    val reqXML = xmlHandler.toXml(txnReq)
    val reqXMLLen = reqXML.length
    val req = f"$reqXMLLen%04d$reqXML"
    val uuid = UUID.randomUUID().toString

    val socket = new Socket()
    try {
      socket.connect(address, connectTimeout)
      socket.setSoTimeout(readTimeout)
      log.info(s"[$uuid] send to TGW...")
      log.debug(s"[$uuid] send to TGW...msg:\r\n$req")
      socket.getOutputStream.write(req.getBytes("UTF-8"))
      socket.getOutputStream.flush()
      val source = Source.fromInputStream(socket.getInputStream)
      val resp = source.mkString
      log.info(s"[$uuid] receive from TGW...")
      log.debug(s"[$uuid] receive from TGW...\r\n$resp")
      xmlHandler.fromXML(resp.substring(4))
    } catch {
      case e: IOException => throw SystemIOException("TGW", address.toString, e)
    } finally {
      socket.close()
    }
  }

  /**
    * 初始化
    *
    * @return
    */
  protected def init(): (InetSocketAddress, Int, Int) = {
    val factory = ConfigFactory.load()
    val ip = factory.getString("TGW.ip")
    val port = factory.getInt("TGW.port")
    val connectTimeout = factory.getInt("TGW.connectTimeout")
    val readTimeout = factory.getInt("TGW.readTimeout")
    (new InetSocketAddress(ip, port), connectTimeout, readTimeout)
  }
}
