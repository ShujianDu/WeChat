package com.yada.wx.cb.data.service.jpa.dao

import com.yada.wx.cb.data.service.jpa.model.Command
import org.springframework.data.repository.CrudRepository

trait CommandDao extends CrudRepository[Command, String] {

  def findByCommandValue(cmd: String): Command
}
