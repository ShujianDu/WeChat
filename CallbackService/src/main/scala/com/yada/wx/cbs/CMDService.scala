package com.yada.wx.cbs

trait CMDService {

  /**
    *
    * @param cmdValue 外部指令
    * @return 内部指令
    */
  def getBizIDByCMD(cmdValue:String):String

  /**
    *
    * @param cmd 外部指令
    * @return 成功模板，失败模板
    */
  def getReturnTempByCMD(cmd:String):(ReturnTemp,ReturnTemp)
}

//TODO 参数待补充
case class ReturnTemp()

