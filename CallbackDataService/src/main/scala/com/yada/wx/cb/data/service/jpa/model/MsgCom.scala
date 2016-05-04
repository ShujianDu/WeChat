package com.yada.wx.cb.data.service.jpa.model

import javax.persistence.{Column, Entity, Id}

import scala.beans.BeanProperty

@Entity(name = "T_B_MSG_COM")
class MsgCom {
  @Id
  var msg_id: String = _
  @Column
  var msg_name: String = _
  @Column
  var msg_type: String = _
  @Column
  var content: String = _
  @Column
  var pic_id: String = _
  @Column
  var pic_path: String = _
  @Column
  var pic_name: String = _
  @Column
  var business_type_id: String = _
  @Column
  var create_user: String = _
  @Column
  var create_date: String = _
  @Column
  var biz_id: String = _
  @Column
  var acc_id: String = _
}
