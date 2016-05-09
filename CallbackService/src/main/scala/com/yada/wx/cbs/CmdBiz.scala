package com.yada.wx.cbs

import com.typesafe.config.ConfigFactory
import com.yada.wx.cb.data.service.jpa.dao._
import com.yada.wx.cb.data.service.jpa.model.{Command, Customer, MsgCom, NewsCom}
import com.yada.wx.cbs.subBiz._

/**
  * 微信命令业务
  */
class CmdBiz(commandDao: CommandDao = SpringContext.context.getBean(classOf[CommandDao]),
             customerDao: CustomerDao = SpringContext.context.getBean(classOf[CustomerDao]),
             bizDao: BizDao = SpringContext.context.getBean(classOf[BizDao])) {

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

    Map("selectLimit" -> new QueryBalanceBiz(),
      "selectBillSum" -> new QueryBillSumBiz(),
      "selectIntegral" -> new QueryPointBalanceBiz(),
      "localProc" -> new DirectReturnBiz(),
      "unBinding" -> new UnBindingBiz(),
      "selectBillSendType" -> new QueryBillSendTypeBiz(),
      "openSpread" -> new OpenSpreadBiz(),
      "closeSpread" -> new CloseSpreadBiz(),
      "findNearbyBankOfChina" -> new QueryNearBankBiz())
  }

  def handle(cmd: String, openID: String): CmdRespMessage = {
    // 查询命令
    val command = commandDao.findByCommandValue(cmd)
    // 获取用户信息
    val customer = customerDao.findByOpenid(openID)
    // 得到执行的命令
    val exeCmd = if (command.flag == "0" && customer == null) {
      commandDao.findByCommandValue("WELCOME")
    } else {
      command
    }
    val biz = bizDao.findOne(exeCmd.biz_id)
    cmdMap(biz.method).subHandle(exeCmd, customer)
  }
}

/**
  * 子业务处理
  */
trait ICmdSubBiz extends ITemplate {
  def subHandle(command: Command, customer: Customer): CmdRespMessage

  protected def kafkaClient: KafkaClient = KafkaClient
}
