package com.yada.system.adapter.bcsp

/**
  * BCSPService
  */
trait BCSPService {
  /**
    * 短信调用
    *
    * @param bsnType   BCSP业务类型
    * @param content   内容
    * @param handsetNo 手机号
    * @param sysId     系统ID
    * @return 返回是否发送成功 true/false
    */
  def sendSMS(bsnType: String, content: String, handsetNo: String, sysId: String): Boolean
}
