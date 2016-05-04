package com.yada.wx.cbs.subBiz

import com.yada.wx.cb.data.service.jpa.model.{Command, Customer}
import com.yada.wx.cbs.{CmdRespMessage, ICmdSubBiz}

/**
  * Created by locky on 2016/5/4.
  */
class QueryBillSendTypeBiz extends ICmdSubBiz {
  override def subHandle(command: Command, customer: Customer): CmdRespMessage = {
    // TODO
    ???
  }
}
