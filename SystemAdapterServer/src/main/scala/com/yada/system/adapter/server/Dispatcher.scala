package com.yada.system.adapter.server

import java.nio.charset.StandardCharsets

import com.yada.system.adapter.route.Route
import io.netty.handler.codec.http.FullHttpRequest
import play.api.libs.json.Json


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
          //TODO 异常处理
          case e: Exception => Response("99", "未知异常", None)
        }
    }
    Json.toJson(rs).toString()
  }

  private def init(): Map[String, Route] = {
    //TODO 扫描包反射初始化Map
    Map.empty[String, Route]
  }
}

object Dispatcher extends Dispatcher {
}

case class Response(returnCode:String,returnMsg:String,data:Option[String])
