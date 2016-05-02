package com.yada.wx.cb.data.service.jpa.dao

import com.yada.wx.cb.data.service.jpa.model.Biz
import org.springframework.data.repository.CrudRepository

trait BizDao extends CrudRepository[Biz, String] {

}
