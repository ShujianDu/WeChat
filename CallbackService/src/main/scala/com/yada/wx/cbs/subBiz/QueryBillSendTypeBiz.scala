package com.yada.wx.cbs.subBiz

import com.yada.wx.cb.data.service.jpa.model.{Command, Customer}
import com.yada.wx.cbs.{CmdRespMessage, HttpClient, ICmdSubBiz}

/**
  * 查询账单寄送方式
  */
class QueryBillSendTypeBiz(httpClient: HttpClient = HttpClient) extends ICmdSubBiz {
  private val url = "/gcs/BillSendTypeRoute"

  override def subHandle(command: Command, customer: Customer): CmdRespMessage = {
    httpClient.send("", url)
    // TODO
    ???
  }


}

case class BillSendTypeReq()

case class BillSendTypeResp()
