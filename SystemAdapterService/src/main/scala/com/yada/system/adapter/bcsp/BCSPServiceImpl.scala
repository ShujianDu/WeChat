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
    * @param sendSMSParams BCSPSendSMSParams
    * @return 返回是否发送成功 true/false
    */
  override def sendSMS(sendSMSParams: BCSPSendSMSParams): BCSPBooleanResult = {
    try {
      log.info(s"sendSMS props:bsnType[$sendSMSParams.bsnType]content[$sendSMSParams.content]handsetNo[$sendSMSParams.handsetNo]sysId[$sendSMSParams.sysId]")
      val t9req = new T900000000(sendSMSParams.handsetNo, sendSMSParams.sysId, sendSMSParams.bsnType, sendSMSParams.content)
      t9req.send
      BCSPBooleanResult(true)
    } catch {
      case e: Exception =>
        log.error("sending to bcsp has a error...", e)
        BCSPBooleanResult(false)
    }
  }
}