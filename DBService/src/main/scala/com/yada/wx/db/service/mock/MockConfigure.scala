package com.yada.wx.db.service.mock

import com.yada.wx.db.service.CardInfo

object MockConfigure {
  val bingOpenId = "1111111111111111"
  val unBingOpenId = "2222222222222222"

  val defCardOfBingOpenId = CardInfo("5149580068840943", "FULL", "1", bingOpenId, isDefault = true, "CNY", "长城金卡", "MAIN", "20160315")
}
