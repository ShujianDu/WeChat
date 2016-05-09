package com.yada.wx.around.dhtz

import com.yada.wx.around.KafkaListener
import org.apache.kafka.clients.consumer.{ConsumerRecord, ConsumerRecords}

class DhtzListener extends KafkaListener {
  /**
    * 配置文件关键字
    *
    * @return
    */
  override protected def key: String = "dhtz"

  /**
    * 处理记录
    *
    * @param record 记录
    */
  override def handle(record: ConsumerRecord[String, String]): Unit = {
    // TODO 动户通知记录处理
  }
}
