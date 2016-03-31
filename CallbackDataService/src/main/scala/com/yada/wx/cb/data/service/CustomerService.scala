package com.yada.wx.cb.data.service

/**
  * 绑定用户service
  */
trait CustomerService {

  /**
    * 用户是否绑定
    *
    * @param openId openID
    * @return false-未绑定，true-绑定
    */
  def isBinded(openId: String): Boolean

  /**
    *
    * @param openId openID
    */
  def getCustomerInfo(openId: String): Option[CustomerInfo]

}

/**
  *
  * @param id           主键
  * @param openid       openId
  * @param identityNo   证件号
  * @param identityType 证件类型
  * @param bindingDate  绑定日期
  * @param defCardNo       默认卡号
  */
case class CustomerInfo(id: String, openid: String, identityNo: String, identityType: String, bindingDate: String, defCardNo: String)

