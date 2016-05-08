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
    val customer = customerDao.findOne(openID)
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
trait ICmdSubBiz {
  def subHandle(command: Command, customer: Customer): CmdRespMessage

  private val (imageDomain, eBankDomain, applyActivityDomain) = {
    val cf = ConfigFactory.load()
    (cf.getString("domain.image"), cf.getString("domain.ebank"), cf.getString("domain.applyActivity"))
  }

  protected def kafkaClient: KafkaClient = KafkaClient

  protected def msgComDao: MsgComDao = SpringContext.context.getBean(classOf[MsgComDao])

  protected def newsComDao: NewsComDao = SpringContext.context.getBean(classOf[NewsComDao])

  /**
    * 创建响应信息
    *
    * @param findMsgCom    查询MsgCom
    * @param findNewsCom   查询NewsCom
    * @param normalReplace 普通的字符替换
    * @param repeatReplace 重复的字符替换
    * @return
    */
  protected def createRespMsg(findMsgCom: () => MsgCom, findNewsCom: String => List[NewsCom], normalReplace: String => String, repeatReplace: String => List[String]): CmdRespMessage = {
    val msgCom = findMsgCom()
    msgCom.msg_type match {
      case "1" => // 文本信息
        val c = replace(msgCom.content, normalReplace, repeatReplace).replace("$_{realmName}", eBankDomain)
        TextCmdRespMessage(c)
      case "3" => // 图文信息
        val newsList = findNewsCom(msgCom.msg_id)
        val itemList = newsList.map(newCom => {
          val title = replace(newCom.title, normalReplace, repeatReplace)
          val des = replace(newCom.description, normalReplace, repeatReplace).replace("$_{realmName}", eBankDomain).replace("$_{applyactivity}", applyActivityDomain)
          NewsMessageItem(title, des, newCom.picurl.replace("$_{realmName}", imageDomain), newCom.pic_link_url.replace("$_{realmName}", eBankDomain))
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
      val rp = repeatReplace(template)
      normalReplace(if (rp.isEmpty) template else rp.head)
    }
  }
}
