package com.yada.sdk.point

import java.io.IOException
import java.net.{InetSocketAddress, Socket}
import java.util.UUID

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.slf4j.Logger
import com.yada.sdk.commons.SystemIOException
import org.slf4j.LoggerFactory

import scala.io.Source

/**
  * 积分同步短链的客户端
  */
private[point] class PointClient extends IPointClient {
  // 日志
  private val log = Logger(LoggerFactory.getLogger(classOf[PointClient]))
  // 积分服务连接超时（单位：毫秒）
  protected val connectTimeout = ConfigFactory.load().getInt("Point.connectTimeout")
  // 积分服务的读取超时（单位：毫秒）
  protected val readTimeout = ConfigFactory.load().getInt("Point.readTimeout")
  // 积分服务的地址
  protected val address = new InetSocketAddress(ConfigFactory.load().getString("Point.ip"), ConfigFactory.load().getInt("Point.port"))

  /**
    * 发送给积分服务
    *
    * @param req 请求报文
    * @return
    */
  def send(req: String): String = {
    // 计算头长度
    val reqXMLLen = req.length
    // 组装完整报文
    val reqMsg = f"$reqXMLLen%06d$req"
    val socket = new Socket()
    val uuid = UUID.randomUUID().toString
    try {
      socket.connect(address, connectTimeout)
      socket.setSoTimeout(readTimeout)
      log.info(s"[$uuid] send to POINT...")
      log.debug(s"[$uuid] send to POINT msg :\r\n$reqMsg")
      socket.getOutputStream.write(reqMsg.getBytes("GBK"))
      socket.getOutputStream.flush()
      val source = Source.fromInputStream(socket.getInputStream, "GBK")
      val resp = source.mkString
      log.info(s"[$uuid] receive from POINT...")
      log.debug(s"[$uuid] receive from POINT msg :\r\n$resp")
      if (resp.isEmpty) throw new RuntimeException("receive from POINT msg can`t be empty...")
      resp.substring(6)
    } catch {
      case e: IOException => throw SystemIOException("POINT", address.toString, e)
      case e: Exception => throw new RuntimeException(s"Point [$address] has a error...", e)
    } finally {
      socket.close()
    }
  }
}