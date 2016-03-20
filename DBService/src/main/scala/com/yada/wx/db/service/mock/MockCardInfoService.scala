package com.yada.wx.db.service.mock

import com.yada.wx.db.service.{CardInfo, CardInfoService}

class MockCardInfoService extends CardInfoService{

  override def getDefaultCard(openId: String): Option[CardInfo] = {
    openId match {
      case MockConfigure.bingOpenId => Some(MockConfigure.defCardOfBingOpenId)
      case _ => None
    }
  }
}
