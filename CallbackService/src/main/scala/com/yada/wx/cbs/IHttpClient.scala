package com.yada.wx.cbs

import java.net.{HttpURLConnection, URL}

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import scala.io.Source


trait IHttpClient {
  def send(value: String, url: String): String
}

class HttpClient extends IHttpClient with LazyLogging {
  override def send(value: String, url: String): String = {
    new URL(HttpClient.baseURL + url).openConnection() match {
      case hc: HttpURLConnection =>
        try {
          logger.info(s"send to url[${hc.getURL.toString}]...msg:\r\n$value")
          hc.setDoInput(true)
          hc.setDoOutput(true)
          hc.getOutputStream.write(value.getBytes("UTF-8"))
          hc.getOutputStream.flush()
          val source = Source.fromInputStream(hc.getInputStream)
          val resp = source.mkString
          logger.info(s"receive from url[${hc.getURL.toString}]...msg:\r\n$resp")
          resp
        } finally {
          hc.disconnect()
        }

    }
  }
}

object HttpClient extends HttpClient {
  val baseURL = ConfigFactory.load().getString("systemAdapter.url")
}
