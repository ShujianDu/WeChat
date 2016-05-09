package com.yada.wx.around

import java.util.concurrent.Executors

import com.yada.wx.around.dhtz.DhtzListener

object Main extends App {
  // 初始化上下文
  SpringContext.context
  val pool = Executors.newFixedThreadPool(1)
  pool.execute(new DhtzListener)
}
