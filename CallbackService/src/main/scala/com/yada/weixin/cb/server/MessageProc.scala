package com.yada.weixin.cb.server

import play.api.libs.json.JsValue

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
/**
  * 信息处理类，暂时放在这里，等引入包后删除
  */
trait MessageProc[T, U] {
  val filter: JsValue => Boolean
  val process: T => Future[U]
  val requestCreator: JsValue => T
  val responseCreator: (JsValue, U) => Option[JsValue]

  def proc(jsValue: JsValue)(op: Option[JsValue] => String): Future[String] = {
    process(requestCreator(jsValue)).map(m => op(responseCreator(jsValue, m)))
  }
}
