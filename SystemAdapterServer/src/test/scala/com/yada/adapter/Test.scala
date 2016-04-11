package com.yada.adapter

import com.yada.system.adapter.server.Balance
import play.api.libs.json._

object Test extends App{

  val rs = new Balance("1","2","3",List{"4","5"})
  val json = Json.toJson(rs).toString()

  println(json)
}
