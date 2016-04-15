package com.yada.system.adapter.tgw

trait TGWService {
  /**
    * 验密
    *
    * @param verificationPWDParams VerificationPWDParams
    * @return 返回是否验证成功    true/false
    */
  def verificationPWD(verificationPWDParams: TGWVerificationPWDParams): TGWBooleanResult
}

/**
  *
  * @param cardNo 卡号
  * @param pwd    电话银行密码
  */
case class TGWVerificationPWDParams(cardNo: String, pwd: String)

case class TGWBooleanResult(isSuccess: Boolean)