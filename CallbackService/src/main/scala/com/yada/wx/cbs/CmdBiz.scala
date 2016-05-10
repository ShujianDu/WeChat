package com.yada.wx.cbs

import java.text.SimpleDateFormat
import java.util.Calendar

import com.yada.wx.cb.data.service.jpa.dao._
import com.yada.wx.cb.data.service.jpa.model.{Command, Customer}
import com.yada.wx.cbs.subBiz._

/**
  * 微信命令业务
  */
class CmdBiz(commandDao: CommandDao = SpringContext.context.getBean(classOf[CommandDao]),
             customerDao: CustomerDao = SpringContext.context.getBean(classOf[CustomerDao]),
             bizDao: BizDao = SpringContext.context.getBean(classOf[BizDao])) {

  private val cmdMap: Map[String, ICmdSubBiz] = {

    Map("selectLimit" -> new QueryBalanceBiz(), // selectLimit 查询默认卡额度
      "selectBillSum" -> new QueryBillSumBiz(), // electBillSum	查询默认卡账单
      "selectIntegral" -> new QueryPointBalanceBiz(), // selectIntegral	查询默认卡积分
      "localProc" -> new DirectReturnBiz(), // localProc 直接返回
      "unBinding" -> new UnBindingBiz(), // unBinding	解除用户绑定
      "selectBillSendType" -> new QueryBillSendTypeBiz(), // selectBillSendType	查询默认卡寄送方式
      "openSpread" -> new OpenSpreadBiz(), // openSpread	开启推送
      "closeSpread" -> new CloseSpreadBiz(), // closeSpread	关闭推送
      "findNearbyBankOfChina" -> new QueryNearBankBiz()) // findNearbyBankOfChina	查询附近中国银行
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

  protected def hideCardNo(cardNo: String): String = {
    val src = cardNo.toCharArray
    val dest = Array.fill(src.length - 8)('*')
    val r = src.take(4) ++ dest ++ src.takeRight(4)
    new String(r)
  }

  private val simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss")

  protected def currentDatetime: String = simpleDateFormat.format(Calendar.getInstance.getTime)
}
