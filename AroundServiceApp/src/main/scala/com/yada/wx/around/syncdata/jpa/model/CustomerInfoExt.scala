package com.yada.wx.around.syncdata.jpa.model

import javax.persistence._

/**
  * Created by QinQiang on 2016/5/10.
  */
@Entity(name = "T_B_CUSTOMER_INFO_EXT")
class CustomerInfoExt {
  @Id
  @GeneratedValue(generator = "T_B_CUSTOMER_INFO_EXT_SEQ")
  @SequenceGenerator(name = "T_B_CUSTOMER_INFO_EXT_SEQ", sequenceName = "SEQ_T_B_CUSTOMER_INFO_EXT")
  @Column(name = "ID")
  var id: Long = _
  @Column(name = "CARDNO")
  var cardNo: String = _
  @Column(name = "CARD_TYPE")
  var cardType: String = _
  @Column(name = "UPDATE_DATE")
  var updateDate: String = _
  @Column(name = "INFO_ID")
  var infoId: String = _
  @Column(name = "OPENID")
  var openId: String = _
  @Column(name = "CUSTOMER_ID")
  var customerId: String = _
  @Column(name = "IS_DEFAULT")
  var isDefault: String = _
  @Column(name = "CURRENCY")
  var currency: String = _
  @Column(name = "STYLE")
  var style: String = _
  @Column(name = "DATA_DT")
  var dataDt: String = _
  @Column(name = "BILL_DATE")
  var billDate: String = _
  @Column(name = "REPAY_DATE")
  var repayDate: String = _
  @Column(name = "CUR_TYPE_01")
  var curType01: String = _
  @Column(name = "CRT_TERM_BAL_01")
  var crtTermBal01: String = _
  @Column(name = "LOWEST_REPAY_NUM_01")
  var lowestRepayNum01: String = _
  @Column(name = "CUR_TYPE_02")
  var curType02: String = _
  @Column(name = "CRT_TERM_BAL_02")
  var crtTermBal02: String = _
  @Column(name = "LOWEST_REPAY_NUM_02")
  var lowestRepayNum02: String = _
  @Column(name = "REMINDER_TIME")
  var reminderTime: String = _
  @Column(name = "REMINDER_FLAG")
  var reminderFlag: String = _
  @Column(name = "NOTICE")
  var notice: String = _
  @Column(name = "CARD_LAST_FOUR_NUMBER")
  var cardLastFourNumber: String = _
  @Column(name = "MAIN_FLAG")
  var mainFlag: String = _
}
