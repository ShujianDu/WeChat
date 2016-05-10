package com.yada

import com.typesafe.scalalogging.LazyLogging
import com.yada.weixin.cb.server.WeixinCallbackServer
import com.yada.wx.cbs.SpringContext

object Main extends App with LazyLogging {
  WeixinCallbackServer.start()
  SpringContext.context
  logger.info(s"system start...")
}
