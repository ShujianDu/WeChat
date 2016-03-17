package com.yada.wx.db.service

/**
  * 绑定用户service
  */
trait CustomerService {

}

/**
  *
  * @param id 主键
  * @param openid openId
  * @param identityNo 身份证号
  * @param bindingDate 绑定日期
  * @param customerId 客户ID
  */
case class CustomerInfo(id:String,openid:String,identityNo:String,bindingDate:String,customerId:String)