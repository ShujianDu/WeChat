package com.yada.wx.around.syncdata.jpa.dao

import com.yada.wx.around.syncdata.jpa.model.CustomerInfoExt
import org.springframework.data.repository.CrudRepository

/**
  * Created by QinQiang on 2016/5/10.
  */
trait CustomerInfoExtDao extends CrudRepository[CustomerInfoExt, String] {
  def deleteByOpenId(openId: String) : Int
}
