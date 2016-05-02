package com.yada.wx.cb.data.service.jpa.model

import javax.persistence.{Column, Entity, Id}

import scala.beans.BeanProperty

@Entity("T_B_COMMAND")
class Command {
  @Id
  @BeanProperty
  var command_id: String = _
  @Column
  @BeanProperty
  var command_value: String = _
  @Column
  @BeanProperty
  var command_des: String = _
  @Column
  @BeanProperty
  var command_state: String = _
  @Column
  @BeanProperty
  var business_type_id: String = _
  @Column
  @BeanProperty
  var success_msg_id: String = _
  @Column
  @BeanProperty
  var fail_msg_id: String = _
  @Column
  @BeanProperty
  var msg_type: String = _
  @Column
  @BeanProperty
  var is_dynamic: String = _
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
  @Column
  @BeanProperty
  var flag: String = _
}
