package com.yada.wx.cbs

import com.yada.wx.cb.data.service.jpa.dao.{BizDao, CommandDao, CustomerDao}
import com.yada.wx.cb.data.service.jpa.model.Customer

/**
  * 微信命令业务
  */
class CmdBiz(commandDao: CommandDao, customerDao: CustomerDao, bizDao: BizDao) {

  val cmdMap: Map[String, ICmdSubBiz] = Map.empty[String, ICmdSubBiz]

  def handle(cmd: String, openID: String): String = {
    val command = commandDao.findByCommand_value(cmd)
    val customer = customerDao.findOne(openID)
    if (command.flag == "0" && customer == null) {
      // 提示用户绑定 TODO
      ???
    } else {
      val biz = bizDao.findOne(command.biz_id)
      cmdMap(biz.method).subHandle(customer)
      //      val respMsg: String = biz.method match {
      //        case "localProc" => ???
      //        case "selectLimit" => ???
      //        case "selectBillSum" => ???
      //        case "selectIntegral" => ???
      //        case "unBinding" => ???
      //        case "selectBillSendType" => ???
      //        case "openSpread" => ???
      //        case "closeSpread" => ???
      //        case "findNearbyBankOfChina" => ???
      //      }
    }
  }
}

trait ICmdSubBiz {
  def subHandle(customer: Customer): String
}
