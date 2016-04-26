package com.yada.wx.cb.data.service

import com.yada.wx.cb.data.service.jpa.dao.CustomerDao

/**
  * 客户信息服务实现
  */
class CustomerServiceImpl(customerDao: CustomerDao = SpringContext.context.getBean(classOf[CustomerDao])) extends CustomerService {

  /**
    * 用户是否绑定
    *
    * @param openId openID
    * @return false-未绑定，true-绑定
    */
  override def isBinding(openId: String): Boolean = {
    customerDao.exists(openId)
  }

  /**
    * 获取客户信息
    *
    * @param openId openID
    */
  override def getCustomerInfo(openId: String): Option[CustomerInfo] = {
    val customer = customerDao.findOne(openId)
    if (customer == null) {
      None
    } else {
      Some(CustomerInfo(openid = customer.openid,
        identityNo = customer.identityNo,
        identityType = customer.identityType,
        bindingDate = customer.bindingDate,
        defCardNo = customer.defCardNo))
    }
  }
}
