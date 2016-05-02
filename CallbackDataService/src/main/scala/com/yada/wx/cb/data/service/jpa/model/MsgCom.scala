package com.yada.wx.cb.data.service.jpa.model

import javax.persistence.{Column, Entity, Id}

import scala.beans.BeanProperty

@Entity(name = "T_B_MSG_COM")
class MsgCom {
  @Id
  @BeanProperty
  var msg_id: String = _
  @Column
  @BeanProperty
  var msg_name: String = _
  @Column
  @BeanProperty
  var msg_type: String = _
  @Column
  @BeanProperty
  var content: String = _
  @Column
  @BeanProperty
  var pic_id: String = _
  @Column
  @BeanProperty
  var pic_path: String = _
  @Column
  @BeanProperty
  var pic_name: String = _
  @Column
  @BeanProperty
  var business_type_id: String = _
  @Column
  @BeanProperty
  var create_user: String = _
  @Column
  @BeanProperty
  var create_date: String = _
  @Column
  @BeanProperty
  var biz_id: String = _
  @Column
  @BeanProperty
  var acc_id: String = _
}
