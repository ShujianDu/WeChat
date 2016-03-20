package com.yada.wx.db.service

/**
  * 绑定用户service
  */
trait CustomerService {

  /**
    * 用户是否绑定
    * @param openId openID
    * @return false-未绑定，true-绑定
    */
  def isBinded(openId: String): Boolean

  /**
    *
    * @param openId openID
    * @param identityNo  证件号
    * @param identityType 证件类型
    * @param pwd 电话银行密码
    * @return (成功失败，返回信息)
    */
  def bind(openId:String,identityNo: String,identityType:String,pwd:String):(Boolean,String)
}

/**
  *
  * @param id          主键
  * @param openid      openId
  * @param identityNo  证件号
  * @param identityType 证件类型
  * @param bindingDate 绑定日期
  * @param customerId  客户ID
  */
case class CustomerInfo(id: String, openid: String, identityNo: String,identityType:String, bindingDate: String, customerId: String)

