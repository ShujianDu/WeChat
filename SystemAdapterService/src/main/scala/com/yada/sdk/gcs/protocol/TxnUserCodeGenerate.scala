package com.yada.sdk.gcs.protocol

import java.util.concurrent.atomic.AtomicInteger

/**
  * Created by locky on 2016/3/18.
  */
object TxnUserCodeGenerate {
  private val i = new AtomicInteger(0)

  def get: String = {
    val num = i.getAndIncrement()
    if (num > 999999999) {
      i.set(0)
    }
    "wx" + num
  }
}
