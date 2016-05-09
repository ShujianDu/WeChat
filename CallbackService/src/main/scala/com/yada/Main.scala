package com.yada

import com.yada.weixin.cb.server.WeixinCallbackServer
import com.yada.wx.cbs.SpringContext

object Main extends App {
  WeixinCallbackServer.start()
  SpringContext.context
  println("hello world...")
}
