package com.yada.wx.cb.data.service.jpa.model

import javax.persistence.{Column, Entity, Id}

import scala.beans.BeanProperty

@Entity(name = "T_B_COMMAND")
class Command {
  @Id
  var command_id: String = _
  @Column(name = "command_value")
  var commandValue: String = _
  @Column
  var command_des: String = _
  @Column
  var command_state: String = _
  @Column
  var business_type_id: String = _
  @Column
  var success_msg_id: String = _
  @Column
  var fail_msg_id: String = _
  @Column
  var msg_type: String = _
  @Column
  var is_dynamic: String = _
  @Column
  var create_user: String = _
  @Column
  var create_date: String = _
  @Column
  var biz_id: String = _
  @Column
  var acc_id: String = _
  @Column
  var flag: String = _
}
