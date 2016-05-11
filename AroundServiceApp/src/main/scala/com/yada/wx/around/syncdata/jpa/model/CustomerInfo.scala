package com.yada.wx.around.syncdata.jpa.model

import javax.persistence._

/**
  * Created by QinQiang on 2016/5/10.
  */
@Entity(name = "T_B_CUSTOMER_INFO")
class CustomerInfo {
  @Id
  @GeneratedValue(generator = "T_B_CUSTOMER_INFO_SEQ")
  @SequenceGenerator(name = "T_B_CUSTOMER_INFO_SEQ", sequenceName = "SEQ_T_B_CUSTOMER_INFO")
  @Column(name = "ID")
  var id: Long = _

  @Column(name = "OPENID")
  var openId: String = _

  @Column(name = "WECHAT_NO")
  var wechatNo: String = _

  @Column(name = "IDENTITY_NO")
  var identityNo: String = _

  @Column(name = "BINDING_DATE")
  var bindingDate: String = _

  @Column(name = "CUSTOMER_ID")
  var customerId: String = _

  @Column(name = "ACC_ID")
  var accId: String = _

  @Column(name = "MOBILE_PHONE")
  var mobilePhone: String = _

  @Column(name = "SEX")
  var sex: String = _

  @Column(name = "FAMILY_NAME")
  var familyName: String = _

  @Column(name = "FIRST_NAME")
  var firstName: String = _

  @Column(name = "ADD1")
  var add1: String = _

  @Column(name = "ADD2")
  var add2: String = _

  @Column(name = "ADD3")
  var add3: String = _

  @Column(name = "ADD4")
  var add4: String = _

  @Column(name = "ADD5")
  var add5: String = _

  @Column(name = "ADD6")
  var add6: String = _

  @Column(name = "NOTICE")
  var notice: String = _

  @Column(name = "IDENTITY_TYPE")
  var identityType: String = _

}
