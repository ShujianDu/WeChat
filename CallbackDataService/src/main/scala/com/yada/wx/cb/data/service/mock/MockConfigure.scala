package com.yada.wx.cb.data.service.mock

import com.yada.wx.cb.data.service.CustomerInfo

object MockConfigure {
  val bingOpenId = "1111111111111111"
  val unBingOpenId = "2222222222222222"

  val defCustomerInfo = CustomerInfo(,bingOpenId,"2310831118","身份证","20160315","6225880131631111")

  val bindCusts =Map{
    bingOpenId -> defCustomerInfo
  }
}
