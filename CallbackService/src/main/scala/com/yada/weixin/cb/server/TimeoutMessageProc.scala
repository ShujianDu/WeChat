package com.yada.weixin.cb.server

import java.net.{InetSocketAddress, URL}
import javax.net.ssl.{HostnameVerifier, HttpsURLConnection, SSLSession}

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging
import com.yada.wx.cbs.{IAccessTokenTool, SpringContext}
import org.json.XML
import play.api.libs.functional.syntax._
import play.api.libs.json.{Reads, _}

import scala.concurrent.Future
import scala.io.Source

/**
  * Created by Cuitao on 2016/3/7.
  */
trait TimeoutMessageProc {
  def proc(msg: String): Future[Unit]
}

class SimpleTimeoutMessageProc extends TimeoutMessageProc with LazyLogging {
  // json 打包
  implicit val writes: Writes[Article] = (
    (__ \ "title").write[String] ~
      (__ \ "description").write[String] ~
      (__ \ "url").write[String] ~
      (__ \ "picurl").write[String]
    ) (unlift(Article.unapply))

  // json 解包
  implicit val reads: Reads[Article] = (
    (__ \ "Title").read[String] ~
      (__ \ "Description").read[String] ~
      (__ \ "Url").read[String] ~
      (__ \ "PicUrl").read[String]
    ) (Article.apply _)

  override def proc(msg: String): Future[Unit] = Future.successful {
    logger.info(s"start timeout msg proc...msg:\r\n$msg")
    if (msg.nonEmpty && !msg.equalsIgnoreCase("success")) {
      val jv = Json.parse(XML.toJSONObject(msg).toString()) \ "xml"
      val req = (jv \ "MsgType").as[String] match {
        case "text" =>
          val toUserName = (jv \ "ToUserName").toString().replace("\"", "")
          val content = (jv \ "Content").as[String]
          Json.obj("touser" -> toUserName, "msgtype" -> "text",
            "text" -> Json.obj("content" -> content))
        case "news" =>
          val toUserName = (jv \ "ToUserName").toString().replace("\"", "")
          val as = if ((jv \ "ArticleCount").as[Int] > 1) {
            (jv \ "Articles" \ "item").as[List[Article]]
          } else {
            List((jv \ "Articles" \ "item").as[Article])
          }
          Json.obj("touser" -> toUserName, "msgtype" -> "news",
            "news" -> Json.obj("articles" -> Json.toJson(as)))
      }
      httpClient.send(Json.toJson(req).toString())
    }
  }

  protected val httpClient = new CustomHttpClient
}

case class Article(title: String,
                   description: String,
                   url: String,
                   picurl: String)

class CustomHttpClient() extends LazyLogging {
  protected val (weiXinIP, connectTimeout, readTimeout, proxy) = {
    val c = ConfigFactory.load()
    (c.getString("customHttpClient.weiXinIP"),
      c.getInt("customHttpClient.connectTimeout"),
      c.getInt("customHttpClient.connectTimeout"),
      new java.net.Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress(c.getString("customHttpClient.proxyIP"), c.getInt("customHttpClient.proxyPort"))))
  }

  protected val tokenTool = SpringContext.context.getBean(classOf[IAccessTokenTool])

  def send(msg: String): Unit = {
    new URL(s"https://$weiXinIP/cgi-bin/message/custom/send?access_token=${tokenTool.getAccessToken}").openConnection(proxy) match {
      case connection: HttpsURLConnection =>
        try {
          logger.info(s"send to url[${connection.getURL.toString}]...msg\r\n$msg")
          // 无视证书
          connection.setHostnameVerifier(new HostnameVerifier {
            override def verify(s: String, sslSession: SSLSession): Boolean = true
          })
          connection.setDoInput(true)
          connection.setDoOutput(true)
          connection.setConnectTimeout(connectTimeout)
          connection.setReadTimeout(readTimeout)
          connection.getOutputStream.write(msg.getBytes("UTF-8"))
          connection.getOutputStream.flush()
          val respCode = connection.getResponseCode
          if (respCode != 200) {
            logger.warn(s"send to url[${connection.getURL.toString}] failed...respCode[$respCode]...msg:\r\n$msg")
          } else {
            val resp = Source.fromInputStream(connection.getInputStream).mkString
            logger.info(s"receive...resp:\r\n$resp")
            val respJson = Json.parse(resp)
            if ((respJson \ "errcode").as[Long] != 0) {
              logger.warn(s"send failed...\r\nreqMsg:\r\n$msg \r\nrespMsg:\r\n$resp")
            }
          }
        } catch {
          case e: Exception => logger.error(s"send to url[${connection.getURL.toString}] error...", e)
        } finally {
          connection.disconnect()
        }
    }
  }
}