package com.yada.wx.around.dhtz

import com.alibaba.fastjson.JSON
import com.yada.wx.around.client.HttpClient
import com.yada.wx.around.dhtz.jpa.dao.AllCustomerInfoDao
import com.yada.wx.around.dhtz.jpa.model.AllCustomerInfo
import com.yada.wx.around.model.CustomerInfo
import com.yada.wx.around.{KafkaListener, SpringContext}
import org.apache.kafka.clients.consumer.ConsumerRecord

class DhtzListener(allCustomerInfoDao: AllCustomerInfoDao = SpringContext.context.getBean(classOf[AllCustomerInfoDao]),
                   httpClient: HttpClient = SpringContext.context.getBean(classOf[HttpClient])) extends KafkaListener {
  /**
    * 配置文件关键字
    *
    * @return
    */
  override protected def key: String = "dhtz"

  /**
    * 处理记录
    *
    * @param record 记录
    */
  override def handle(record: ConsumerRecord[String, String]): Unit = {
    // TODO 动户通知记录处理
    record.key() match {
      // 绑定默认卡、更换默认卡
      case "BindingBindingDef" =>
        val model = JSON.parseObject(record.value(), classOf[CustomerInfo])
        saveAllCustomerInfo(model)
      // 修改推送管理设置
      case "AllCustomerInfoUpdateNoticeByIdentityNo" => updateNotice(record.value())
    }
  }

  /**
    * 保存客户信息
    *
    * @param model
    */
  private def saveAllCustomerInfo(model: CustomerInfo): Unit = {
    val allCustomerInfo = new AllCustomerInfo();
    allCustomerInfoDao.save(allCustomerInfo)
    println(model)
  }

  /**
    * 修改推送配置
    *
    * @param value
    */
  private def updateNotice(value: String): Unit = {
    println(value)
  }
}
