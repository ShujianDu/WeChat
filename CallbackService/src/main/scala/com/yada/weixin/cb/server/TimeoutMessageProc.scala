package com.yada.weixin.cb.server

import java.net.URL
import java.util
import java.util.Collections
import javax.net.ssl.HttpsURLConnection

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
  implicit val reads: Reads[Article] = Reads(jv => {
    val title = (jv \ "Title").toString()
    val description = (jv \ "Description").toString()
    val url = (jv \ "Url").toString()
    val picUrl = (jv \ "PicUrl").toString()
    JsSuccess(Article(title, description, url, picUrl))
  })

  //  // json 解包
  //  implicit val reads: Reads[Article] = (
  //    (__ \ "Title").read[String] ~
  //      (__ \ "Description").read[String] ~
  //      (__ \ "Url").read[String] ~
  //      (__ \ "PicUrl").read[String]
  //    ) (Article.apply _)

  override def proc(msg: String): Future[Unit] = Future.successful {
    //    logger.info(s"start timeout msg proc...msg:\r\n$msg")
    val jv = Json.parse(XML.toJSONObject(msg).toString()) \ "xml"
    logger.info(jv.toString())
    val req = (jv \ "MsgType").as[String] match {
      case "text" =>
        val toUserName = (jv \ "ToUserName").toString()
        val content = (jv \ "Content").toString()
        Json.obj("touser" -> toUserName, "msgtype" -> "text",
          "text" -> Json.obj("content" -> content))
      case "news" =>
        val toUserName = (jv \ "ToUserName").toString()
        val as = if ((jv \ "ArticleCount").as[Int] > 1) {
          (jv \ "Articles" \ "item").as[List[Article]]
        } else {
          (jv \ "Articles" \ "item").as[Article]
        }
        Json.obj("touser" -> toUserName, "msgtype" -> "news",
          "news" -> Json.obj("articles" -> as.toString()))
    }
    httpClient.send(Json.toJson(req).toString())
  }

  protected val httpClient = new CustomHttpClient
}

case class Article(title: String,
                   description: String,
                   url: String,
                   picurl: String)

class CustomHttpClient() extends LazyLogging {
  protected val (connectTimeout, readTimeout) = {
    val c = ConfigFactory.load()
    c.getInt("customHttpClient.connectTimeout") -> c.getInt("customHttpClient.connectTimeout")
  }

  protected val tokenTool = SpringContext.context.getBean(classOf[IAccessTokenTool])

  def send(msg: String): Unit = {
    new URL(s"https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=${tokenTool.getAccessToken}").openConnection() match {
      case connection: HttpsURLConnection =>
        try {
          logger.info(s"send to url[${connection.getURL.toString}]...msg\r\n$msg")
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
            logger.info(s"send failed...resp:\r\n$resp")
          }
        } catch {
          case e: Exception => logger.error(s"send to url[${connection.getURL.toString}] error...", e)
        } finally {
          connection.disconnect()
        }
    }
  }
}

object Test extends App {
  val msg = """"""
  val c = new CustomHttpClient()
  c.send(msg)
}