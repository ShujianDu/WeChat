package com.yada.sdk.tgw.protocol.impl

import com.yada.sdk.tgw.protocol.TGWReq

/**
  * CIF接收前端渠道的客户签约电话银行密码密文验证申请，
  * 调用本地的卡用户密文验密交易从本地的CALL CENTER客户信息表查询密码是否与上传的密码一致，
  * 返回处理结果。
  */
class Fun001(cardNo: String, password: String) extends TGWReq {
  setReqHeadProps("PTXCOD", "109004")
  setReqDataProps("CARDNO", cardNo)
  setReqDataProps("CHKPWD", encrypt(password))

  override protected def ptxcod: String = "109004"

  /**
    * 加密
    *
    * @param password 原始密码
    * @return 经过加密后的密码
    */
  protected def encrypt(password: String): String = {
    //    1、读取电话银行密码 X
    val x = password
    //    2、Y = X * 135791
    val y = (x.toLong * 135791).toString
    //    3、如果 “Y长度 大于等于12位” 左取第二位开始6位 到T
    //    如果 “Y长度 大于等于5位 小于12位” 左取 （Y长度 - 5）位到T
    //    如果 “Y长度 小于5位” 全取Y 到T
    val yLen = y.length
    val t = if (yLen >= 12) {
      y.substring(2, 7)
    } else if (yLen < 5) {
      y
    } else {
      y.substring(0, yLen - 5)
    }
    //    4 、 Z = T + X
    val z = t.toLong + x.toLong
    //    5 、 如果 Z < 1, 000, 000 则 电话银行加密密码 = | Z -135791 |
    //         如果 Z >= 1, 000, 000 则 电话银行加密密码 = | Z -1 ， 000 ， 000 - 135791 |
    // 算出如果少于6位左补0
    //    6 、 如果 电话银行加密密码 = 0 则 电话银行加密密码 = 999888
    val encryptPWD = if (z < 1000000) {
      (z - 135791).abs
    } else {
      (z - 1000000 - 135791).abs
    }
    if (encryptPWD == 0) {
      "999888"
    } else {
      f"$encryptPWD%06d"
    }
  }

}
