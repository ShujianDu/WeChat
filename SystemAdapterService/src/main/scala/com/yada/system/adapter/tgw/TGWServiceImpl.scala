package com.yada.system.adapter.tgw

import com.yada.sdk.tgw.protocol.ErrorRespCodeException
import com.yada.sdk.tgw.protocol.impl.Fun001

/**
  * TGW系统接口
  */
class TGWServiceImpl extends TGWService {
  /**
    * 验密
    *
    * @param verificationPWDParams verificationPWDParams
    * @return 返回是否验证成功    true/false
    */
  override def verificationPWD(verificationPWDParams: TGWVerificationPWDParams): Boolean = {
    val fun001Req = new Fun001(verificationPWDParams.cardNo, verificationPWDParams.pwd)
    try {
      val fun001Resp = fun001Req.send
      fun001Resp.rspCod == "000000"
    } catch {
      case e: ErrorRespCodeException =>
        false
    }
  }
}

object TGWServiceImpl extends TGWServiceImpl
