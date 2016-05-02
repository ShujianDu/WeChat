package com.yada.wx.cb.data.service.jpa.model

import javax.persistence.{Column, Entity, Id}

import scala.beans.BeanProperty

@Entity(name = "T_B_NEWS_COM")
class NewsCom {
  @Id
  @BeanProperty
  var news_id: String = _
  @Column
  @BeanProperty
  var msg_id: String = _
  @Column
  @BeanProperty
  var title: String = _
  @Column
  @BeanProperty
  var description: String = _
  @Column
  @BeanProperty
  var picurl: String = _
  @Column
  @BeanProperty
  var pic_link_url: String = _
  @Column
  @BeanProperty
  var orders: String = _
  @Column
  @BeanProperty
  var customize: String = _
}
