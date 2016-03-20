package com.yada.sdk.point.protocol

import java.util.Calendar

import scala.collection.mutable

/**
  * 积分请求
  */
trait PointReq[PointResp] {
  private val tranDateTime = Calendar.getInstance.getTime
  private val reqHeadProps = mutable.Map.empty[String, String]
  private val reqBodyProps = mutable.Map.empty[String, String]

  setReqHeadProps("Version", version)
  setReqHeadProps("TranCode", tranCode)
  setReqHeadProps("SerialNo", serialNo)
  setReqHeadProps("ChannelCode", channelCode)
  setReqHeadProps("TranDate", tranDate)
  setReqHeadProps("TranTime", tranTime)

  /**
    * 版本号
    *
    * @return
    */
  def version: String = "1"

  /**
    * 交易码
    *
    * @return
    */
  def tranCode: String

  /**
    * 渠道的请求报文为能确定当日唯一的10位流水号
    *
    * @return
    */
  def serialNo: String = (tranDate + tranTime).substring(4)

  /**
    * 渠道代码
    *
    * @return
    */
  def channelCode: String = "05"

  /**
    * 交易日期。格式yyyy/MM/dd
    *
    * @return
    */
  def tranDate: String = String.format("%1/$tY/%1$tm/%1$td", tranDateTime)

  /**
    * 交易时间。格式HH:mm:ss
    *
    * @return
    */
  def tranTime: String = String.format("%1$tH:%1$tM:%1$tS", tranDateTime)

  /**
    * 设置请求头属性
    *
    * @param key   属性名称
    * @param value 属性值
    */
  def setReqHeadProps(key: String, value: String): Unit = {
    reqHeadProps += key -> value
  }

  /**
    * 设置请求正文属性
    *
    * @param key   属性名称
    * @param value 属性值
    */
  def setReqBodyProps(key: String, value: String): Unit = {
    reqBodyProps += key -> value
  }

  def send: PointResp = {
    generateResp("")
  }

  def generateResp(xml: String): PointResp
}
