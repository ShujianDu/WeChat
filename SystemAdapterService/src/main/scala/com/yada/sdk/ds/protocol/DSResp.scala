package com.yada.sdk.ds.protocol

import com.yada.sdk.ds.json.Data
import play.api.libs.json.JsString

/**
  * 直销系统响应
  */
class DSResp(data: Data) {
  if (failedThrowException) {
    if (!isSuccess) throw DirectSaleErrorRespCode(stat, result)
  }

  /**
    * 响应头的值
    *
    * @param key json头中的key
    * @return
    */
  def headValue(key: String): String = (data.head \ key).asOpt[String] match {
    case None => ""
    case Some(x) => x
  }

  /**
    * 响应正文的值
    *
    * @param key json正文中的key
    * @return
    */
  def bodyValue(key: String): String = (data.body \ key).asOpt[String] match {
    case None => ""
    case Some(x) => x
  }

  /**
    * 响应内容list的值
    *
    * @param keys        需要转换成对象的标签
    * @param valuesToObj 标签的值转换成对象
    * @tparam T 对象
    * @return
    */
  def bodyListValues[T](keys: List[String], valuesToObj: List[String] => T): List[T] = {
    val list = data.body \\ "applyList"
    list.map(item => {
      val values = keys.map(key => {
        (item \ key).asOpt[String] match {
          case None => ""
          case Some(x) => x
        }
      })
      valuesToObj(values)
    }).toList
  }

  /**
    * 操作结果码
    *
    * @return 00(成功)/其他(失败)
    */
  def stat = headValue("stat")

  /**
    * 操作结果描述
    *
    * @return
    */
  def result = headValue("result")

  /**
    * 操作返回结果时间
    *
    * @return
    */
  def date = headValue("date")

  /**
    * 响应是否成功
    *
    * @return
    */
  def isSuccess: Boolean = stat == "00"

  /**
    * 失败时抛出异常
    *
    * @return
    */
  def failedThrowException: Boolean = true
}

/**
  * 直销系统错误响应码
  *
  * @param stat   响应码
  * @param result 响应描述
  */
case class DirectSaleErrorRespCode(stat: String, result: String) extends RuntimeException(s"Direct Sale Response has a error code...stat[$stat]result[$result]")