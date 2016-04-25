package com.yada.wx.cb.data.service.jpa.model

import javax.persistence.{Column, Entity, Id}

/**
  * 用户信息表
  */
@Entity
class Customer {
  /**
    * 微信用户的唯一标识
    */
  @Id
  var openid: String = _
  /**
    * 证件号
    */
  @Column
  var identityNo: String = _
  /**
    * 证件类型
    */
  @Column
  var identityType: String = _
  /**
    * 绑定日期
    */
  @Column
  var bindingDate: String = _
  /**
    * 默认卡号
    */
  @Column
  var defCardNo: String = _
}
