package com.yada.sdk.point.protocol.impl

import com.yada.sdk.point.protocol.PointReq

/**
  * 微信渠道跳转页面聪明购商城时验证卡号和手机号接口。
  */
class P0154_WeChatUserAuthenticationForCMG(cardNo: String, mobileNo: String) extends PointReq {
  //TODO 卡号和手机号需要加密
  setReqBodyProps("EncryptCardNo", cardNo)
  setReqBodyProps("EncryptMobile", mobileNo)

  /**
    * 交易码
    *
    * @return
    */
  override def tranCode: String = "0154"
}
