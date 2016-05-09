package com.yada.wx.cbs.subBiz

import com.yada.wx.cb.data.service.jpa.dao.CustomerDao
import com.yada.wx.cb.data.service.jpa.model.{Command, Customer, MsgCom, NewsCom}
import com.yada.wx.cbs.{CmdRespMessage, ICmdSubBiz, SpringContext}

import scala.collection.convert.WrapAsScala

/**
  * 解绑业务
  */
class UnBindingBiz(customerDao: CustomerDao = SpringContext.context.getBean(classOf[CustomerDao])) extends ICmdSubBiz {
  override def subHandle(command: Command, customer: Customer): CmdRespMessage = {
    customerDao.deleteByOpenid(customer.openid)
    val findMsgCom: () => MsgCom = () => msgComDao.findOne(command.success_msg_id)
    val findNewsCom: String => List[NewsCom] = msgID => WrapAsScala.asScalaBuffer(newsComDao.findByMsgID(msgID)).toList
    val np: String => String = t => t
    val rp: String => List[String] = t => List(t)
    createRespMsg(findMsgCom, findNewsCom, np, rp)
  }
}
