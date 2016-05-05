package com.yada.wx.cbs

import java.net.{HttpURLConnection, URL}

import com.typesafe.config.ConfigFactory

import scala.io.Source


trait IHttpClient {
  def send(value: String, url: String): String
}

class HttpClient extends IHttpClient {

  override def send(value: String, url: String): String = {
    new URL(HttpClient.baseURL + url).openConnection() match {
      case hc: HttpURLConnection =>
        try {
          println("===========" + value)
          hc.setDoInput(true)
          hc.setDoOutput(true)
          //        hc.setReadTimeout(2000)
          //        hc.setConnectTimeout(2000)
          hc.getOutputStream.write(value.getBytes("UTF-8"))
          hc.getOutputStream.flush()
          val source = Source.fromInputStream(hc.getInputStream)
          val resp = source.mkString
          println("--------" + resp)
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
