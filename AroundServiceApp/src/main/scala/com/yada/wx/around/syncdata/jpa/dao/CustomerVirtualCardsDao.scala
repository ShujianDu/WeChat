package com.yada.wx.around.syncdata.jpa.dao

import com.yada.wx.around.syncdata.jpa.model.CustomerVirtualCards
import org.springframework.data.repository.CrudRepository

/**
  * Created by QinQiang on 2016/5/10.
  */
trait CustomerVirtualCardsDao extends CrudRepository[CustomerVirtualCards, String] {
  def deleteByOpenId(openId: String) : Int
}
