package com.yada.system.adapter.tgw

trait TGWService {
  /**
    * 验密
    *
    * @param cardNo 卡号
    * @param pwd    电话银行密码
    * @return 返回是否验证成功    true/false
    */
  def verificationPWD(cardNo: String, pwd: String): Boolean
}
