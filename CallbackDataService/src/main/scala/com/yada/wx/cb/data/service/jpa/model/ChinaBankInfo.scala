package com.yada.wx.cb.data.service.jpa.model

import javax.persistence.{Column, Entity, Id}

@Entity(name = "T_B_CHINABANK_INFO")
class ChinaBankInfo {
  @Id
  var id: String = _
  @Column(name = "BRANCH_NO")
  var branchNo: String = _
  @Column
  var name: String = _
  @Column
  var addr: String = _
  @Column
  var area1: String = _
  @Column
  var area2: String = _
  @Column
  var area3: String = _
  @Column
  var latitude: String = _
  @Column
  var longitude: String = _
}
