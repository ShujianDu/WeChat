package com.yada.wx.cb.data.service.jpa.dao

import com.yada.wx.cb.data.service.jpa.model.MsgCom
import org.springframework.data.repository.CrudRepository

trait MsgComDao extends CrudRepository[MsgCom, String] {

}
