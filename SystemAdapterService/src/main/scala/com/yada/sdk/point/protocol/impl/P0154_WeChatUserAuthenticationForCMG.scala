package com.yada.sdk.point.protocol.impl

import com.yada.sdk.point.{IPointClient, PointSecurity}
import com.yada.sdk.point.protocol.PointReq

/**
  * 微信渠道跳转页面聪明购商城时验证卡号和手机号接口。
  */
class P0154_WeChatUserAuthenticationForCMG(cardNo: String, mobileNo: String)(implicit client: IPointClient = IPointClient.GLOBAL) extends PointReq(client) {
  var security = PointSecurity.GLOBAL
  setReqBodyProps("EncryptCardNo", security.encrypt(cardNo, security.weChatUserAuthenticationDESKeyForCMG))
  setReqBodyProps("EncryptMobile", security.encrypt(mobileNo, security.weChatUserAuthenticationDESKeyForCMG))

  /**
    * 交易码
    *
    * @return
    */
  override def tranCode: String = "0154"
}
