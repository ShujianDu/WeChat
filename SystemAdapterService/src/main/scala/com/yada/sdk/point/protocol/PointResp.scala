package com.yada.sdk.point.protocol

import com.yada.sdk.point.xml.Message

/**
  * 积分消费响应
  */
class PointResp(msg: Message) {
  if (failedThrowException && rtnCode != "0000") throw new PointErrorRespCodeException(rtnCode, rtnMsg)

  /**
    * 响应码
    *
    * @return
    */
  def rtnCode = respHeadValue("RtnCode")

  /**
    * 响应信息
    *
    * @return
    */
  def rtnMsg = respHeadValue("RtnMsg")

  /**
    * 响应消息头获取标签的内容
    *
    * @param key 内容所对应的标签名称
    * @return
    */
  def respHeadValue(key: String): String = {
    msg.head.props.getOrElse(key, "")
  }

  /**
    * 响应正文获取标签的内容
    *
    * @param key 内容所对应的标签名称
    * @return
    */
  def respBodyValue(key: String): String = {
    msg.body match {
      case None => throw new RuntimeException("Point Response Body is empty..." + msg)
      case Some(body) =>
        body.props.getOrElse(key, "")
    }
  }

  /**
    * 响应正文获取List型的内容
    *
    * @param propsToObj 将一组属性转换成一个对象
    * @tparam T 对象类型
    * @return
    */
  def respBodyListValues[T](propsToObj: Map[String, String] => T): List[T] = {
    msg.body match {
      case None => throw new RuntimeException("Point Response Body is empty..." + msg)
      case Some(body) =>
        body.listProps.map(props => {
          propsToObj(props)
        })
    }
  }

  /**
    * 响应正文获取List型内容
    *
    * @param keys 要取出的值
    * @return
    */
  def respBodyListValues(keys: List[String]): List[Map[String, String]] = {
    respBodyListValues(props => {
      keys.map(key => key -> props.getOrElse(key, "")).toMap
    })
  }

  /**
    * 响应码不是0000（成功）时抛出异常
    *
    * @return
    */
  protected def failedThrowException: Boolean = true
}

/**
  * 积分错误的响应码异常
  *
  * @param rtnCode 响应码
  * @param rtnMsg  响应信息
  */
case class PointErrorRespCodeException(rtnCode: String, rtnMsg: String) extends Exception(s"rtnCode[$rtnCode]rtnMsg[$rtnMsg]")