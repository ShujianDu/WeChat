package com.yada.wx.db.service

trait CardInfoService {

  def getDefaultCard(openId:String):String

  def isBindedCard(openID:String):Boolean
}

/**
  *
  * @param cardNo     卡号
  * @param cardType   卡类
  * @param custId     关联客户表ID
  * @param openID     OPENID
  * @param isDefault  是否是默认卡(0 是，1 否)
  * @param currency   币种
  * @param style      产品名称
  * @param mainFlag   主副卡标识
  * @param updateDate 更新时间
  */
case class CardInfo(cardNo: String, cardType: String, custId: String, openID: String, isDefault: String,
                    currency: String, style: String, mainFlag: String, updateDate: String)

class MockCardInfo extends CardInfoService{

  override def getDefaultCard(openId: String): String = ???

  override def isBindedCard(openID: String): Boolean = ???
}
