package com.yada.wx.around.model

/**
  * Created by QinQiang on 2016/5/9.
  */
class CustomerInfoModel {

  val datetime: String = ???
  val data: Data = ???

  /**
    *
    * @param id           ID
    * @param openId       OPENID
    * @param identityType 证件类型
    * @param identityNo   证件号
    * @param mobilePhone  手机号
    * @param defCardNo    默认卡号
    * @param bindingDate  绑定日期
    */
  case class Data(var id: Long,
                  var openId: String,
                  var identityType: String,
                  var identityNo: String,
                  var mobilePhone: String,
                  var defCardNo: String,
                  var bindingDate: String
                 )

}
