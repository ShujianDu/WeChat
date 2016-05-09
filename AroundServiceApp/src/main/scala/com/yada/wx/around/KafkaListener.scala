package com.yada.wx.around

import java.util.Properties

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.consumer.{ConsumerRecord, ConsumerRecords, KafkaConsumer}

trait KafkaListener extends Runnable with LazyLogging {
  /**
    * 配置文件关键字
    *
    * @return
    */
  protected def key: String

  protected val (customer, poolTimeout) = {
    val config = ConfigFactory.load()
    val props: Properties = new Properties
    props.put("bootstrap.servers", config.getString(s"kafka.$key.servers"))
    props.put("group.id", config.getString(s"kafka.$key.groupID"))
    props.put("enable.auto.commit", "true")
    props.put("auto.commit.interval.ms", "1000")
    props.put("session.timeout.ms", "30000")
    //    props.put("auto.offset.reset", "earliest")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](props)
    consumer.subscribe(config.getStringList(s"kafka.$key.topics"))
    consumer -> config.getLong(s"kafka.$key.poolTimeout")
  }

  override def run(): Unit = {
    while (!Thread.interrupted()) {
      try {
        val records = customer.poll(poolTimeout)
        handle(records)
      } catch {
        case e: InterruptedException =>
          logger.info(s"${this.getClass} has a InterruptedException...", e)
        case e: Exception =>
          logger.warn(s"${this.getClass} has a error...", e)
      }
    }
    logger.info(s"${this.getClass} thread exit...")
  }

  private def handle(records: ConsumerRecords[String, String]): Unit = {
    handle(records.iterator())
  }

  private def handle(records: java.util.Iterator[ConsumerRecord[String, String]]): Unit = {
    if (records.hasNext) {
      val record = records.next()
      handle(record)
      handle(records)
    }
  }

  /**
    * 处理记录
    *
    * @param record 记录
    */
  def handle(record: ConsumerRecord[String, String]): Unit
}
