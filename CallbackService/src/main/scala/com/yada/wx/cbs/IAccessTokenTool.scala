package com.yada.wx.cbs

/**
  * 获取TOKEN的服务
  */
trait IAccessTokenTool {
  /**
    * 获取Token模块内存中的Token值
    *
    * @return token内容
    */
  def getAccessToken: String
}
