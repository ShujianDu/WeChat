package com.yada.sdk.bcsp.protocol

import com.yada.sdk.bcsp.xml.Sms

/**
  * BCSP响应
  */
class BCSPResp(sms: Sms) {

  /**
    * 获取属性值
    *
    * @param key XML标签名称
    * @return
    */
  def propsValue(key: String): String = sms.props.getOrElse(key, "")

  protected def failedThrowException: Boolean = true
}

case class BCSPErrorResp(retCode: String, retMsg: String) extends RuntimeException(s"retCode[$retCode]retMsg[$retMsg]")