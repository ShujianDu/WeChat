package com.yada.wx.cb.data.service.mock

import com.yada.wx.cb.data.service.{CustomerInfo, CustomerService}

class CustomerServiceMocker extends CustomerService{

  override def isBinding(openId: String): Boolean = {
    openId match {
      case MockConfigure.bingOpenId => true
      case MockConfigure.unBingOpenId => false
    }
  }

  /**
    *
    * @param openId openID
    */
  override def getCustomerInfo(openId: String): Option[CustomerInfo] = {
    MockConfigure.bindCusts.get(openId)
  }
}
