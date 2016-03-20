package com.yada.wx.db.service.mock

import com.yada.wx.db.service.CustomerService

class CustomerServiceMocker extends CustomerService{

  override def isBinded(openId: String): Boolean = {
    openId match {
      case MockConfigure.bingOpenId => true
      case MockConfigure.unBingOpenId => false
    }
  }

  /**
    *
    * @param openId       openID
    * @param identityNo   证件号
    * @param identityType 证件类型
    * @param pwd          电话银行密码
    * @return (成功失败，返回信息)
    */
  override def bind(openId: String, identityNo: String, identityType: String, pwd: String): (Boolean, String) = ???
}
