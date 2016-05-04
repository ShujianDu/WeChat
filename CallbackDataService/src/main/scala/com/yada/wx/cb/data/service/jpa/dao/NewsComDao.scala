package com.yada.wx.cb.data.service.jpa.dao

import com.yada.wx.cb.data.service.jpa.model.NewsCom
import org.springframework.data.repository.CrudRepository

trait NewsComDao extends CrudRepository[NewsCom, String] {
  def findByMsgID(msgID: String): java.util.List[NewsCom]
}
