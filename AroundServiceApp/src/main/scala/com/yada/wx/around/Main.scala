package com.yada.wx.around

import java.util.concurrent.Executors

import com.yada.wx.around.dhtz.DhtzListener
import com.yada.wx.around.syncdata.SyncDataListener

object Main extends App {
  // 初始化上下文
  SpringContext.context
  val pool = Executors.newFixedThreadPool(4)
  pool.execute(new DhtzListener)
  pool.execute(new SyncDataListener)
}
