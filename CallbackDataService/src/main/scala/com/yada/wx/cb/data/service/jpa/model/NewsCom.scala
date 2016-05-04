package com.yada.wx.cb.data.service.jpa.model

import javax.persistence.{Column, Entity, Id}

import scala.beans.BeanProperty

@Entity(name = "T_B_NEWS_COM")
class NewsCom {
  @Id
  var news_id: String = _
  @Column(name = "msg_id")
  var msgID: String = _
  @Column
  var title: String = _
  @Column
  var description: String = _
  @Column
  var picurl: String = _
  @Column
  var pic_link_url: String = _
  @Column
  var orders: String = _
  @Column
  var customize: String = _
}
