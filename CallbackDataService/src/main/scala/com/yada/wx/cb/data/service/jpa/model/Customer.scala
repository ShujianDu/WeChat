package com.yada.wx.cb.data.service.jpa.model

import javax.persistence.{Column, Entity, Id}

/**
  * 用户信息表
  */
@Entity(name = "T_B_CUSTOMER_INFO_NEW")
class Customer {
  @Id
  var id: String = _
  /**
    * 微信用户的唯一标识
    */
  @Column(name = "OPENID")
  var openid: String = _
  /**
    * 证件号
    */
  @Column(name = "IDENTITY_NO")
  var identityNo: String = _
  /**
    * 证件类型
    */
  @Column(name = "IDENTITY_TYPE")
  var identityType: String = _
  /**
    * 绑定日期
    */
  @Column(name = "BINDING_DATE")
  var bindingDate: String = _
  /**
    * 默认卡号
    */
  @Column(name = "DEF_CARD_NO")
  var defCardNo: String = _

  @Column(name = "MOBILE_PHONE")
  var mobilePhone: String = _
}
