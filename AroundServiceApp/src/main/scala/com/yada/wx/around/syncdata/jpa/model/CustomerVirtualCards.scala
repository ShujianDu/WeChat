package com.yada.wx.around.syncdata.jpa.model

import javax.persistence._

/**
  * Created by QinQiang on 2016/5/10.
  */
@Entity(name = "T_B_CUSTOMER_VIRTUAL_CARDS")
class CustomerVirtualCards {
  @Id
  @GeneratedValue(generator = "T_B_CUSTOMER_VIRTUAL_CARDS_SEQ")
  @SequenceGenerator(name = "T_B_CUSTOMER_VIRTUAL_CARDS_SEQ", sequenceName = "SEQ_T_B_CUSTOMER_VIRTUAL_CARDS")
  @Column(name = "ID")
  var id: Long = _
  
  @Column(name = "CARD_NO")
  var cardNo: String = _

  @Column(name = "OPENID")
  var openId: String = _

  @Column(name = "MOBILE_PHONE")
  var mobilePhone: String = _

  @Column(name = "SEX")
  var sex: String = _

  @Column(name = "FAMILY_NAME")
  var familyName: String = _

  @Column(name = "FIRST_NAME")
  var firstName: String = _

  @Column(name = "UPDATE_DATE")
  var updateDate: String = _

  @Column(name = "MAIN_CARD_ID")
  var mainCardId: String = _

  @Column(name = "CARD_LAST_FOUR_NUMBER")
  var cardLastFourNumber: String = _

  @Column(name = "NOTICE")
  var notice: String = _

  @Column(name = "ACC_ID")
  var accId: String = _

}
