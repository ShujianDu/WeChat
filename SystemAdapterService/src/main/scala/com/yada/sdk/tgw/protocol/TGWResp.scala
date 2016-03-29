package com.yada.sdk.tgw.protocol

import com.yada.sdk.tgw.xml.TxnRsp

/**
  * TGW响应
  */
@throws[ErrorRespCodeException]
class TGWResp(txnRsp: TxnRsp) {
  /**
    * 响应码，000000表示成功
    */
  val rspCod = respHeadValue("RSPCOD")
  // 如果失败则抛出异常
  if (failedThrowException) {
    if (rspCod != "000000") throw ErrorRespCodeException(rspCod)
  }

  /**
    * 响应头的值
    *
    * @param key 响应头的标签名称
    * @return
    */
  def respHeadValue(key: String): String = {
    txnRsp.head.props.getOrElse(key, "")
  }

  /**
    * 响应正文的值
    *
    * @param key 响应正文的标签名称
    * @return
    */
  def respDataValue(key: String): String = {
    txnRsp.data.props.getOrElse(key, "")
  }

  /**
    * 失败的时候抛出异常么
    *
    * @return true为抛出异常
    */
  protected def failedThrowException: Boolean = true
}

/**
  * 错误的响应码异常
  *
  * @param rspCod 响应码
  */
case class ErrorRespCodeException(rspCod: String) extends Exception(s"rspCod[$rspCod]")