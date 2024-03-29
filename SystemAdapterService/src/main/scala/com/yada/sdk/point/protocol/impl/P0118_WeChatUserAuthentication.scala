package com.yada.sdk.point.protocol.impl

import com.yada.sdk.point.{IPointClient, PointSecurity}
import com.yada.sdk.point.protocol.PointReq

/**
  * 微信渠道跳转页面时验证卡号接口
  *
  * @param cardNo 卡号
  */
class P0118_WeChatUserAuthentication(cardNo: String)(implicit client: IPointClient = IPointClient.GLOBAL) extends PointReq(client) {
  val security = PointSecurity.GLOBAL
  setReqBodyProps("EncryptCardNo", security.weChatUserAuthenticationEncrypt(cardNo))
  setReqHeadProps("ChannelCode", "14")

  /**
    * 交易码
    *
    * @return
    */
  override def tranCode: String = "0118"
}
