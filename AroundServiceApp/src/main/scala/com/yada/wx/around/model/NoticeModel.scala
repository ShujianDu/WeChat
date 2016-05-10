package com.yada.wx.around.model

/**
  * Created by QinQiang on 2016/5/10.
  */
class NoticeModel {

  val datetime: String = ???
  val data: Data = ???

  case class Data(
                   var identityNo: String,
                   var notice: String,
                   var billNotice: String,
                   var repaymentNotice: String
                 )

}
