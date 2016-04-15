package com.yada.system.adapter.bcsp

/**
  * BCSPService
  */
trait BCSPService {
  /**
    * 短信调用
    *
    * @param sendSMSParams BCSPSendSMSParams
    * @return 返回是否发送成功 true/false
    */
  def sendSMS(sendSMSParams: BCSPSendSMSParams): BCSPBooleanResult
}

/**
  *
  * @param bsnType   BCSP业务类型
  * @param content   内容
  * @param handsetNo 手机号
  * @param sysId     系统ID
  */
case class BCSPSendSMSParams(bsnType: String, content: String, handsetNo: String, sysId: String)

case class BCSPBooleanResult(isSuccess: Boolean)