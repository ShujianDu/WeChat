package com.yada.sdk.ds.protocol

import com.yada.sdk.ds.DirectSaleClient
import com.yada.sdk.ds.json.{Data, JsonHandler}

import scala.collection.mutable

/**
  * 直销系统报文请求
  */
trait DSReq {
  // 请求头信息
  private val headProps = mutable.Map.empty[String, String]
  // 请求正文信息
  private val bodyProps = mutable.Map.empty[String, String]

  setHeadValue("txnId", txnId)
  setHeadValue("imei", "wechat")
  setHeadValue("deviceType", "8")
  setHeadValue("lon", "0")
  setHeadValue("lat", "0")
  setHeadValue("city", "")
  setHeadValue("deviceToken", "")
  setHeadValue("channelNo", "wechat")

  /**
    * 交易号
    *
    * @return
    */
  def txnId: String

  /**
    * 设置头的值
    *
    * @param key   内容的key
    * @param value 内容
    */
  def setHeadValue(key: String, value: String): Unit = headProps += key -> value

  /**
    * 设置正文的值
    *
    * @param key   内容的key
    * @param value 内容
    */
  def setBodyValue(key: String, value: String): Unit = bodyProps += key -> value

  /**
    * 发送
    *
    * @return
    */
  def send: DSResp = {
    val resp = DirectSaleClient.GLOBAL.send(toData)
    new DSResp(resp)
  }

  /**
    * 转成直销的Data对象
    *
    * @return
    */
  def toData: Data = {
    val head = JsonHandler.GLOBAL.mapToJsObject(headProps.toMap)
    val body = JsonHandler.GLOBAL.mapToJsObject(bodyProps.toMap)
    Data(head, body)
  }
}
