package com.yada.wx.cbs.subBiz

import com.yada.wx.cb.data.service.jpa.model.{Command, Customer}
import com.yada.wx.cbs.{CmdRespMessage, ICmdSubBiz}

/**
  * 查询附近网点
  */
class QueryNearBankBiz extends ICmdSubBiz {
  override def subHandle(command: Command, customer: Customer): CmdRespMessage = {
    ???
  }
}
