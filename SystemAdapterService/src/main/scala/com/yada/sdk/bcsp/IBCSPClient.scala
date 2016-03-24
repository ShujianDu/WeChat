package com.yada.sdk.bcsp

import com.typesafe.config.ConfigFactory
import com.yada.sdk.bcsp.xml.Sms

/**
  * Created by locky on 2016/3/24.
  */
trait IBCSPClient {
  /**
    * 发送短信
    *
    * @param sms 短信
    * @return
    */
  def send(sms: Sms): Sms
}

object IBCSPClient {
  val GLOBAL = Class.forName(ConfigFactory.load().getString("BCSP.client")).newInstance().asInstanceOf[IBCSPClient]
}