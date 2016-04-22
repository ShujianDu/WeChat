package com.yada.sdk.gcs.protocol

import java.util.Random

/**
  * 产生一个0-99999999的随机数
  */
object RandomGenerate {
  val random = new Random

  def get: Int = {
    random.nextInt(99999999)
  }
}
