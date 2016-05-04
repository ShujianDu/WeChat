package com.yada

import com.yada.weixin.cb.server.WeixinCallbackServer

object Main extends App {
  WeixinCallbackServer.start()
  println("hello world...")
}
