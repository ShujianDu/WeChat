package com.yada.wx.cbs

import com.yada.wx.cb.data.service.jpa.dao._
import com.yada.wx.cb.data.service.jpa.model.{Command, Customer, MsgCom, NewsCom}
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

  protected def createRespMsg(findMsgCom: () => MsgCom, findNewsCom: String => List[NewsCom], normalReplace: String => String, repeatReplace: String => List[String]): CmdRespMessage = {
    val msgCom = findMsgCom()
    msgCom.msg_type match {
      case "1" => // 文本信息
        val c = replace(msgCom.content, normalReplace, repeatReplace)
        TextCmdRespMessage(c)
      case "3" => // 图文信息
        val newsList = findNewsCom(msgCom.msg_id)
        val itemList = newsList.map(newCom => {
          val title = replace(newCom.title, normalReplace, repeatReplace)
          val des = replace(newCom.description, normalReplace, repeatReplace)
          NewsMessageItem(title, des, newCom.picurl, newCom.pic_link_url)
        })
        NewsCmdRespMessage(itemList)
    }

  }

  /**
    *
    * @param template      模板
    * @param normalReplace 普通替换
    * @param repeatReplace 循环体替换
    * @return
    */
  private def replace(template: String, normalReplace: String => String, repeatReplace: String => List[String]): String = {
    val content = new StringBuilder
    val start = template.indexOf("<#>")
    val end = template.indexOf("<_#>")
    if (start != -1 && end != -1) {
      content.append(template.substring(0, start))
      val mTemplate = template.substring(start + 3, end)
      repeatReplace(mTemplate).foreach(item => {
        content.append(item)
      })
      content.append(template.substring(end + 4))
      normalReplace(content.toString())
    } else {
      repeatReplace(normalReplace(template)).head
    }
  }
}
