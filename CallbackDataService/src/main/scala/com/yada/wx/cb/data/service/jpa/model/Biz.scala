package com.yada.wx.cb.data.service.jpa.model

import javax.persistence.{Column, Entity, Id}

import scala.beans.BeanProperty

@Entity(name = "T_B_BIZ")
class Biz {
  @Id
  var biz_id: String = _
  @Column
  var class_name: String = _
  @Column
  var method: String = _
  @Column
  var biz_des: String = _
}
