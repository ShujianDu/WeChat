package com.yada.sdk.commons

import java.io.IOException
import java.nio.charset.Charset
import java.util

import com.ibm.mq.constants.CMQC
import com.ibm.mq._


/**
  * IBM的webSphere的MQ的客户端
  */
class WebSphereMQClient(systemName: String, mqManagerName: String, queueName: String, hostname: String, port: Int, channel: String, ccsid: Int) {

  private var mqManager = initManager
  private var queue = initQueue

  def send(msg: String): Unit = {
    send(msg, Charset.defaultCharset().name())
  }

  def send(msg: String, encoding: String): Unit = {
    try {
      if (!(mqManager isConnected())) {
        mqManager = initManager
      }
      if (!(queue isOpen())) {
        queue = initQueue
      }
      val mqMsg = new MQMessage
      mqMsg.expiry = -1 //消息用不过期
      mqMsg.write(msg.getBytes(encoding))
      queue.put(mqMsg)
    } catch {
      case mqException: MQException => new IOException(s"send to [$systemName] failed", mqException)
      case ioException: IOException => new IOException(s"send to [$systemName] failed", ioException)
    }
  }

  def close(): Unit = {
    queue.close()
    mqManager.disconnect()
  }

  protected def initManager: MQQueueManager = {
    val properties = new util.Hashtable[String, String]()
    properties.put("hostname", hostname)
    properties.put("port", port.toString)
    properties.put("channel", channel)
    properties.put("ccsid", ccsid.toString)
    new MQQueueManager(mqManagerName, properties)
  }

  protected def initQueue: MQQueue = {
    //    CMQC.MQOO_BROWSE
    //    CMQC.MQOO_INPUT_AS_Q_DEF
    //    CMQC.MQOO_INPUT_SHARED
    //    CMQC.MQOO_INPUT_EXCLUSIVE
    //    CMQC.MQOO_OUTPUT
    //    CMQC.MQOO_SAVE_ALL_CONTEXT
    //    CMQC.MQOO_PASS_IDENTITY_CONTEXT
    //    CMQC.MQOO_PASS_ALL_CONTEXT
    //    CMQC.MQOO_SET_IDENTITY_CONTEXT
    //    CMQC.MQOO_SET_ALL_CONTEXT
    //    CMQC.MQOO_ALTERNATE_USER_AUTHORITY
    //    CMQC.MQOO_FAIL_IF_QUIESCING
    mqManager.accessQueue(queueName, CMQC.MQOO_OUTPUT | CMQC.MQOO_FAIL_IF_QUIESCING)
  }
}
