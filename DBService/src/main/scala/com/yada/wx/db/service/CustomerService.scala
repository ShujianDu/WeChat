package com.yada.wx.db.service

/**
  * 绑定用户service
  */
trait CustomerService {

  def isBinded(openId:String):Boolean

}

/**
  *
  * @param id 主键
  * @param openid openId
  * @param identityNo 身份证号
  * @param bindingDate 绑定日期
  * @param customerId 客户ID
  */
case class CustomerInfo(id:String,openid:String,identityNo:String,bindingDate:String,customerId:String)

class MockCustomerService extends CustomerService{

  val bingOpenId = "1111111111111111"
  val unBingOpenId ="2222222222222222"

  override def isBinded(openId: String): Boolean = {
    openId match {
      case bingOpenId => true
      case unBingOpenId => false
    }
  }
}