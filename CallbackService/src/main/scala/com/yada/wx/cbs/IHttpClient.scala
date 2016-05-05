package com.yada.wx.cbs

import java.io.{BufferedReader, InputStreamReader}
import java.net.{HttpURLConnection, URL}

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.Logger

import scala.collection.mutable.ArrayBuffer
import scala.io.Source


trait IHttpClient {
  def send(value: String, url: String): String
}

class HttpClient extends IHttpClient {

  override def send(value: String, url: String): String = {
    new URL(HttpClient.baseURL + url).openConnection() match {
      case hc: HttpURLConnection =>
        println("===========" + value)
        hc.setDoInput(true)
        hc.setDoOutput(true)
//        hc.setReadTimeout(2000)
//        hc.setConnectTimeout(2000)
        hc.getOutputStream.write(value.getBytes("UTF-8"))
        hc.getOutputStream.flush()
        val source = Source.fromInputStream(hc.getInputStream)
        val resp = source.mkString
        //        val ab = new ArrayBuffer[Byte]()
        //        val bts = new Array[Byte](1024)
        //        var len = -1
        //        do {
        //          len = hc.getInputStream.read(bts)
        //        } while (len > 0)
        //        println("------------" + ab.size)
        //        ""
        println("--------" + resp)
        resp
    }
  }
}

object HttpClient extends HttpClient {
  val baseURL = ConfigFactory.load().getString("systemAdapter.url")
}
