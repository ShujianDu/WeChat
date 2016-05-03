package com.yada.wx.cbs

import com.yada.wx.cb.data.service.jpa.dao._
import com.yada.wx.cb.data.service.jpa.model.{Command, Customer}
import com.yada.wx.cbs.subBiz.QueryBalanceBiz

/**
  * 微信命令业务
  */
class CmdBiz(commandDao: CommandDao, customerDao: CustomerDao, bizDao: BizDao) {

  private val cmdMap: Map[String, ICmdSubBiz] = {
    //   selectLimit	查询默认卡额度
    //    selectBillSum	查询默认卡账单
    //    selectIntegral	查询默认卡积分
    //    localProc	直接返回
    //    unBinding	解除用户绑定
    //    selectBillSendType	查询默认卡寄送方式
    //    openSpread	开启推送
    //    closeSpread	关闭推送
    //    findNearbyBankOfChina	查询附近中国银行
    //    findNearByMerchant	查询附近特约商户

    Map("selectLimit" -> new QueryBalanceBiz())
  }

  def handle(cmd: String, openID: String): CmdRespMessage = {
    val command = commandDao.findByCommand_value(cmd)
    val customer = customerDao.findOne(openID)
    if (command.flag == "0" && customer == null) {
      // 提示用户绑定 TODO
      ???
    } else {
      val biz = bizDao.findOne(command.biz_id)
      cmdMap(biz.method).subHandle(command, customer)
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
      ???
    }
  }
}

trait ICmdSubBiz {
  def subHandle(command: Command, customer: Customer): CmdRespMessage
}
