package com.yada.sdk.bcsp.protocol

import java.util.concurrent.atomic.AtomicInteger

/**
  * SeqNo生成器
  */
private[bcsp] class SeqNoGenerator {
  val no = new AtomicInteger(0)

  /**
    * 获取一个seqNo
    *
    * @return
    */
  def get: String = {
    val num = no.getAndIncrement()
    if (num > 900000000) no.set(0)
    num.toString
  }
}

private[bcsp] object SeqNoGenerator {
  var GLOBAL = new SeqNoGenerator
}