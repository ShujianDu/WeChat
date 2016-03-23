package com.yada.sdk.point.protocol.impl

import com.yada.sdk.point.PointSecurity
import com.yada.sdk.point.protocol.PointReq

/**
  * 微信渠道跳转页面时验证卡号接口
  *
  * @param cardNo 卡号
  */
class P0118_WeChatUserAuthentication(cardNo: String) extends PointReq {
  val security = PointSecurity.GLOBAL
  setReqBodyProps("EncryptCardNo", security.encrypt(cardNo, security.weChatUserAuthenticationDESKey))

  /**
    * 交易码
    *
    * @return
    */
  override def tranCode: String = "0118"
}
