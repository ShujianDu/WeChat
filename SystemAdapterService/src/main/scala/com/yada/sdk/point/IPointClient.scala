package com.yada.sdk.point

import com.typesafe.config.ConfigFactory

/**
  * 积分服务的客户端
  */
trait IPointClient {
  /**
    * 发送给积分服务
    *
    * @param req 请求报文
    * @return
    */
  def send(req: String): String
}

object IPointClient {
  val GLOBAL = Class.forName(ConfigFactory.load().getString("Point.client")).newInstance().asInstanceOf[IPointClient]
}