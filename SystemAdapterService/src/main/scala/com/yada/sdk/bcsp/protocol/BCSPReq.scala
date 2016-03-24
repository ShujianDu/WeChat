package com.yada.sdk.bcsp.protocol

import com.yada.sdk.bcsp.IBCSPClient
import com.yada.sdk.bcsp.xml.{Entity, Sms}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * BCSP的请求
  */
trait BCSPReq {
  private val props = mutable.Map.empty[String, String]
  private val items = ListBuffer.empty[String]

  /**
    * 设置请求的属性
    *
    * @param key   标签名称
    * @param value 标签值
    */
  def setReqProps(key: String, value: String): Unit = {
    props += key -> value
  }

  /**
    * 设置请求的item内容
    *
    * @param item item的内容
    */
  def setReqItem(item: String): Unit = {
    items += item
  }

  /**
    * 变成xml对象
    *
    * @return
    */
  def toSMS = Sms(props.toMap, if (items.isEmpty) None else Some(Entity(items.toList)))

  /**
    * 发送并响应
    *
    * @return
    */
  def send: BCSPResp = {
    val resp = client.send(toSMS)
    new BCSPResp(resp)
  }

  protected def client: IBCSPClient = IBCSPClient.GLOBAL
}
