package com.yada.wx.cb.data.service.jpa.dao

import com.yada.wx.cb.data.service.jpa.model.Command
import org.springframework.data.repository.CrudRepository

trait CommandDao extends CrudRepository[Command, String] {

  def findByCommand_value(cmd: String): Command
}
