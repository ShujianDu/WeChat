package com.yada.sdk.tgw.protocol

import java.util.Calendar

import com.yada.sdk.tgw.ITGWClient
import com.yada.sdk.tgw.xml.{Data, Head, TxnReq}

import scala.collection.mutable

/**
  * TGW请求
  */
trait TGWReq {
  var tgwClient: ITGWClient = ITGWClient.GLOBAL
  /**
    * 产生一个yyyyMMddHHmmss的时间
    *
    * @return
    */
  protected val datetime = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance.getTime)

  private val headProps = mutable.Map.empty[String, String]
  headProps += "PTXCOD" -> ptxcod
  headProps += "ACQUID" -> ""
  headProps += "TERMID" -> "0001"
  headProps += "TXNDAT" -> datetime.substring(0, 8)
  headProps += "TXNTIM" -> datetime.substring(8)
  headProps += "ACQSEQ" -> datetime.substring(6)
  private val dataProps = mutable.Map.empty[String, String]

  protected def ptxcod: String

  /**
    * 设置请求头
    *
    * @param key   标签名称
    * @param value 标签值
    */
  def setReqHeadProps(key: String, value: String): Unit = headProps += key -> value

  /**
    * 获取请求头的标签值
    *
    * @param key 标签名称
    * @return
    */
  def getReqHeadProps(key: String): String = headProps.getOrElse(key, "")

  /**
    * 设置请求数据的标签内容
    *
    * @param key   标签名称
    * @param value 标签值
    */
  def setReqDataProps(key: String, value: String): Unit = dataProps += key -> value

  /**
    * 获取请求数据的标签内容
    *
    * @param key 标签名称
    * @return
    */
  def getReqDataProps(key: String): String = dataProps.getOrElse(key, "")

  /**
    * 转换成xml对象
    *
    * @return
    */
  def toTxnReq: TxnReq = TxnReq(Head(headProps.toMap), Data(dataProps.toMap))

  /**
    * 发送并接受响应
    *
    * @return
    */
  def send: TGWResp = {
    val txnResp = tgwClient.send(toTxnReq)
    new TGWResp(txnResp)
  }
}
