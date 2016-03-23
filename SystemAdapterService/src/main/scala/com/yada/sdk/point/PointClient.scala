package com.yada.sdk.point

import java.net.{InetSocketAddress, Socket}
import java.util.UUID

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.slf4j.Logger
import com.yada.sdk.point.xml.{XmlHandler, Message, IPointClient}
import org.slf4j.LoggerFactory

import scala.io.Source

/**
  * 积分同步短链的客户端
  */
private[point] class PointClient extends IPointClient {
  // 日志
  private val log = Logger(LoggerFactory.getLogger(classOf[PointClient]))
  // 积分服务的IP
  protected val ip = ConfigFactory.load().getString("Point.ip")
  // 积分服务的端口
  protected val port = ConfigFactory.load().getInt("Point.port")
  // 积分服务连接超时（单位：毫秒）
  protected val connectTimeOut = ConfigFactory.load().getInt("Point.connectTimeOut")
  // 积分服务的读取超时（单位：毫秒）
  protected val readTimeOut = ConfigFactory.load().getInt("Point.readTimeOut")

  /**
    * 发送给积分服务
    *
    * @param req 请求报文
    * @return
    */
  def send(req: Message): Message = {
    val reqXML = XmlHandler.GLOBAL.toXML(req)
    val socket = new Socket()
    val uuid = UUID.randomUUID().toString
    try {
      socket.connect(new InetSocketAddress(ip, port), connectTimeOut)
      socket.setSoTimeout(readTimeOut)
      log.info(s"[$uuid] send to POINT...")
      log.debug(s"[$uuid] send to POINT msg :\r\n$req")
      socket.getOutputStream.write(reqXML.getBytes("GBK"))
      socket.getOutputStream.flush()
      val source = Source.fromInputStream(socket.getInputStream, "GBK")
      val resp = source.mkString
      log.debug(s"[$uuid] receive from POINT msg :\r\n$resp")
      log.info(s"[$uuid] receive from POINT...")
      if (resp.isEmpty) throw new RuntimeException("receive from POINT msg can`t be empty...")
      XmlHandler.GLOBAL.fromXML(resp)
    } catch {
      case e: Exception => throw PointSocketException(ip, port)
    } finally {
      socket.close()
    }
  }
}

/**
  * 积分socket异常
  *
  * @param ip   积分服务地址
  * @param port 积分服务端口
  */
case class PointSocketException(ip: String, port: Int) extends RuntimeException(s"IP[$ip]PORT[$port]")