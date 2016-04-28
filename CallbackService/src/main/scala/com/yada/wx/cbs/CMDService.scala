package com.yada.wx.cbs

trait CMDService {

  //根据一个Key获取能出来的指令列表
  def getCMDsByBiz(key:String):List[String]

  //根据指令值返回处理成功或者失败的信息模板
  def getReturnTempByCMD(cmd:String):(ReturnTemp,ReturnTemp)
}

//TODO 参数待补充
case class ReturnTemp()


