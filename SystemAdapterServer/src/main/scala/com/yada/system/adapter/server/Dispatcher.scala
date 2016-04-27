package com.yada.system.adapter.server

import java.nio.charset.StandardCharsets

import com.typesafe.config.ConfigFactory
import com.yada.sdk.commons.SystemIOException
import com.yada.system.adapter.route.Route
import io.netty.handler.codec.http.FullHttpRequest
import play.api.libs.functional.syntax._
import play.api.libs.json.{Writes, _}

import scala.collection.JavaConverters._


class Dispatcher {

  //初始化mapping
  val mapping: Map[String, Route] = init()

  //分发请求
  def dispatch(httpRequest: FullHttpRequest): String = {
    val path = httpRequest.getUri.substring(1)
    val json = httpRequest.content().toString(StandardCharsets.UTF_8)

    val rs = mapping.get(path) match {
      case Some(x) =>
        try {
          val data = x.execute(json)
          Response("00", "处理成功", Some(data))
        } catch {
          case e:SystemIOException =>
            //TODO 发送Event
            Response("98",e.channelName +"发生异常",None)
          case e: Exception => Response("99", "未知异常", None)
        }
      case None =>
        Response("97",f"请求地址:$path,不存在",None)
    }
    Json.toJson(rs).toString()
  }

  private def init(): Map[String, Route] = {
    val a = ConfigFactory.load().getStringList("systemAdapter.server.routeClasses").asScala.map(
      className => (className,Class.forName(className).newInstance().asInstanceOf[Route])
    ).toMap

    a
  }
}

object Dispatcher extends Dispatcher {
}

case class Response(returnCode:String,returnMsg:String,data:Option[String])

object Response{
  implicit val responseWrites: Writes[Response] = (
    (__ \ "returnCode").write[String] ~ (__ \ "returnMsg").write[String] ~ (__ \ "data").writeNullable[String]
    ) (unlift(Response.unapply))
}
