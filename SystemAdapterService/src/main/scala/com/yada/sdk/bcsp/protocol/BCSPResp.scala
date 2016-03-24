package com.yada.sdk.bcsp.protocol

import com.yada.sdk.bcsp.xml.Sms

/**
  * BCSP响应
  */
class BCSPResp(sms: Sms) {
  if (failedThrowException && retCode != "0") throw BCSPErrorRetCodeException(retCode, retMsg)

  /**
    * 获取属性值
    *
    * @param key XML标签名称
    * @return
    */
  def propsValue(key: String): String = sms.props.getOrElse(key, "")

  /**
    * 返回码
    *
    * @return 0表示成功
    */
  def retCode: String = propsValue("retCode")

  /**
    * 返回信息
    *
    * @return
    */
  def retMsg: String = propsValue("retMsg")

  protected def failedThrowException: Boolean = true

  override def toString = s"BCSPResp($retCode, $retMsg)"
}

/**
  * BCSP失败的响应
  *
  * @param retCode 响应码
  * @param retMsg  响应信息
  */
case class BCSPErrorRetCodeException(retCode: String, retMsg: String) extends RuntimeException(s"retCode[$retCode]retMsg[$retMsg]")