package com.yada.wx.around.syncdata.jpa.dao

import com.yada.wx.around.syncdata.jpa.model.CustomerInfo
import org.springframework.data.repository.CrudRepository

/**
  * Created by QinQiang on 2016/5/10.
  */
trait CustomerInfoDao extends CrudRepository[CustomerInfo, String] {
  def findByOpenId(openId: String): CustomerInfo

  def findByIdentityNo(identityNo: String): List[CustomerInfo]

  def deleteByOpenId(openId: String): Int
}
