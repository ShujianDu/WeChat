package com.yada.wx.cb.data.service.jpa.model

import javax.persistence.{Column, Entity, Id}

import scala.beans.BeanProperty

@Entity("T_B_BIZ")
class Biz {
  @Id
  @BeanProperty
  var biz_id: String = _
  @Column
  @BeanProperty
  var class_name: String = _
  @Column
  @BeanProperty
  var method: String = _
  @Column
  @BeanProperty
  var biz_des: String = _
}
