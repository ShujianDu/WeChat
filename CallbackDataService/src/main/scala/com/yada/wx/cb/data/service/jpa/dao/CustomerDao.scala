package com.yada.wx.cb.data.service.jpa.dao

import com.yada.wx.cb.data.service.jpa.model.Customer
import org.springframework.data.repository.CrudRepository

/**
  * 客户信息的dao
  */
trait CustomerDao extends CrudRepository[Customer, String] {
  def findByOpenid(openid: String): Customer

  def deleteByOpenid(openid: String): Unit
}
