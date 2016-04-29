package com.yada.system.adapter.kafka

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

/**
  * 信息生产者
  */
class MessageProducer {

  protected val kafkaProducer = {
    val props: Properties = new Properties
    props.put("bootstrap.servers", "22.7.14.148:9091")
    // 0：生产者不等待响应；1：生产者等待leader写入本地日志；all：生产者等待leader同步
    props.put("acks", "all")
    props.put("retries", 0)
    props.put("batch.size", 16384)
    props.put("linger.ms", 1)
    props.put("buffer.memory", 33554432)
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    new KafkaProducer[String, String](props)
  }

  /**
    * 发送通知信息
    *
    * @param topic   通知信息的主题
    * @param key     信息类型的唯一标识
    * @param message 通知的信息
    */
  def send(topic: String, key: String, message: String): Unit = {
    kafkaProducer.send(new ProducerRecord[String, String](topic, key, message))
  }

  def close(): Unit = {
    kafkaProducer.close()
  }
}

object MessageProducer extends MessageProducer

