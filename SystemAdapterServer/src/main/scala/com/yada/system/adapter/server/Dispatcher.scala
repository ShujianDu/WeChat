package com.yada.system.adapter.server

import java.nio.charset.StandardCharsets
import java.util.Calendar

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.slf4j.Logger
import com.yada.sdk.commons.SystemIOException
import com.yada.sdk.gcs.protocol.ErrorGCSReturnCodeException
import com.yada.system.adapter.route.Route
import io.netty.handler.codec.http.FullHttpRequest
import org.slf4j.LoggerFactory
import play.api.libs.functional.syntax._
import play.api.libs.json.{Writes, _}

import scala.collection.JavaConverters._


class Dispatcher {
  private val log = Logger(LoggerFactory.getLogger(classOf[Dispatcher]))
  private[server] var kafkaClient: KafkaClient = KafkaClient
  //初始化mapping
  val mapping: Map[String, Route] = init()

  //分发请求
  def dispatch(httpRequest: FullHttpRequest): String = {
    val path = httpRequest.getUri.substring(1)
    val json = httpRequest.content().toString(StandardCharsets.UTF_8)

    val rs = mapping.get(path) match {
      case Some(x) =>
        try {
          log.info(s"$x handle msg...\r\n$json")
          val data = x.execute(json)
          log.info(s"$x handle complete data...$data")
          kafkaClient.send("systemAdapter", path, Json.obj("datetime" -> String.format("%s", Calendar.getInstance.getTime), "reqData" -> json).toString())
          Response("00", "处理成功", Some(Json.parse(data)))
        } catch {
          case e: SystemIOException =>
            log.error(s"${e.channelName} io exception", e)
            // TODO 错误信息没做
            kafkaClient.send("exception", "mail", s"${e.channelName} io exception")
            Response("98", e.channelName + "发生异常", None)
          case e: ErrorGCSReturnCodeException =>
            Response("97", "GCS返回码错误:" + e.returnCode + ":" + e.returnMessage, None)
          case e: Exception =>
            log.error("", e)
            Response("99", "未知异常", None)
        }
      case None =>
        Response("97", f"请求地址:$path,不存在", None)
    }
    Json.toJson(rs).toString()
  }

  private def init(): Map[String, Route] = {
    ConfigFactory.load().getStringList("systemAdapter.server.routeClasses").asScala.map(
      className => {
        val temps = className.split('.')
        (temps(temps.length - 2).toLowerCase + "/" + temps.last, Class.forName(className).newInstance().asInstanceOf[Route])
      }
    ).toMap
  }
}

object Dispatcher extends Dispatcher

case class Response(returnCode: String, returnMsg: String, data: Option[JsValue])

object Response {
  implicit val responseWrites: Writes[Response] = (
    (__ \ "returnCode").write[String] ~ (__ \ "returnMsg").write[String] ~ (__ \ "data").writeNullable[JsValue]
    ) (unlift(Response.unapply))
}
