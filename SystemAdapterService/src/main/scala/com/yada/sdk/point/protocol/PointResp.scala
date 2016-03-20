package com.yada.sdk.point.protocol

import com.yada.sdk.point.xml.XmlHandler

/**
  * 积分消费响应
  */
class PointResp(xml: String) {

  private val msg = XmlHandler.GOLBAL.fromXML(xml)
}
