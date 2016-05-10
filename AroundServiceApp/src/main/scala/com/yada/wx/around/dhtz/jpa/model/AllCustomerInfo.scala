package com.yada.wx.around.dhtz.jpa.model

import javax.persistence._

/**
  * Created by QinQiang on 2016/5/9.
  */
@Entity(name = "T_B_ALL_CUSTOMER_INFO")
class AllCustomerInfo {

  @Id
  @GeneratedValue(generator = "ALL_CUSTOMER_INFO_SEQ")
  @SequenceGenerator(name = "ALL_CUSTOMER_INFO_SEQ", sequenceName = "SEQ_T_B_ALL_CUSTOMER_INFO")
  @Column(name = "ID")
  var id: String = _

  @Column(name = "OPENID")
  var openId: String = _

  @Column(name = "IDENTITY_TYPE")
  var identityType: String = _

  @Column(name = "IDENTITY_NO")
  var identityNo: String = _

  @Column(name = "MOBILE_PHONE")
  var mobilePhone: String = _

  @Column(name = "FAMILY_NAME")
  var familyName: String = _

  @Column(name = "FIRST_NAME")
  var firstName: String = _

  @Column(name = "SEX")
  var sex: String = _

  @Column(name = "BINDING_DATE")
  var bindingDate: String = _

  @Column(name = "CARDNO")
  var cardNo: String = _

  @Column(name = "CARD_TYPE")
  var cardType: String = _

  @Column(name = "CARD_LAST_FOUR_NUMBER")
  var cardLastFourNumber: String = _

  @Column(name = "STYLE")
  var style: String = _

  @Column(name = "IS_DEFAULT")
  var isDefault: String = _

  @Column(name = "ACC_ID")
  var accId: String = _

  @Column(name = "NOTICE")
  var notice: String = _

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

  @Column(name = "BILL_NOTICE")
  var billNotice: String = _

  @Column(name = "REPAYMENT_NOTICE")
  var repaymentNotice: String = _

}
