package com.yada.sdk.ds.json

import play.api.libs.json._

/**
  * 直销的JSON数据处理
  */
class JsonHandler {

  def toJSON(data: Data): String = {
    val o = JsObject(Seq(
      "head" -> data.head,
      "body" -> data.body
    ))
    Json.stringify(o)
  }

  def mapToJsObject(props: Map[String, String]) = {
    JsObject(props.map(prop => prop._1 -> JsString(prop._2)).toSeq)
  }

  def fromJSON(data: String): Data = {
    val json = Json.parse(data)
    Data(json \ "head", json \ "body")
  }

}

object JsonHandler {
  val GLOBAL = new JsonHandler
}


