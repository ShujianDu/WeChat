package com.yada.system.adapter.bcsp

import com.typesafe.scalalogging.slf4j.Logger
import com.yada.sdk.bcsp.protocol.impl.T900000000
import org.slf4j.LoggerFactory

/**
  * BCSP服务实现
  */
class BCSPServiceImpl extends BCSPService {
  private val log = Logger(LoggerFactory.getLogger(classOf[BCSPServiceImpl]))

  /**
    * 短信调用
    *
    * @param bsnType   BCSP业务类型
    * @param content   内容
    * @param handsetNo 手机号
    * @param sysId     系统ID
    * @return 返回是否发送成功 true/false
    */
  override def sendSMS(bsnType: String, content: String, handsetNo: String, sysId: String): Boolean = {
    try {
      log.info(s"sendSMS props:bsnType[$bsnType]content[$content]handsetNo[$handsetNo]sysId[$sysId]")
      val t9req = new T900000000(handsetNo, sysId, bsnType, content)
      t9req.send
      true
    } catch {
      case e: Exception =>
        log.error("sending to bcsp has a error...", e)
        false
    }
  }
}