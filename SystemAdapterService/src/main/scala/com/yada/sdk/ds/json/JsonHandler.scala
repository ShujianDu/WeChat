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
    JsObject(props.map(prop => prop._1 -> JsString(prop._2)))
  }

  def fromJSON(data: String): Data = {
    val json = Json.parse(data)
    Data((json \ "head").get, (json \ "body").get)
  }

}

object JsonHandler {
  //  val jsonData = "{ \"head\": {\n\t\"txnId\": \"xxxx\", \n\t\"imei\":\"xxxx\", \n\t\"deviceType\": \"xxxx\",\n\t\"lon\": \"xxxx\",\n\t\"lat\": \"xxxx\",\n\t\"city\": \"xxxx\",\n\t\"deviceToken\": \"xxxx\",\n\t\"channelNo\": \"xxxx\"},\n\"body\": { \n\t\"id\" :\"123\",\n\t\"idType\":\"345\",\n\t\"name\":\"aaa\",\n\t\"currPage\":\"11\"}\n}"
  val GLOBAL = new JsonHandler
}


