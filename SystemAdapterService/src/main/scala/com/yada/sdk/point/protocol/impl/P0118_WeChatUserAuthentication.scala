package com.yada.sdk.point.protocol.impl

import com.yada.sdk.point.protocol.PointReq

/**
  * 微信渠道跳转页面时验证卡号接口
  *
  * @param encryptCardNo 加密的卡号
  */
class P0118_WeChatUserAuthentication(encryptCardNo: String) extends PointReq {
  // TODO 加密要内置
  setReqBodyProps("EncryptCardNo", encryptCardNo)

  /**
    * 交易码
    *
    * @return
    */
  override def tranCode: String = "0118"
}
