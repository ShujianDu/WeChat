package com.yada.wx.around.dhtz

import com.yada.wx.around.KafkaListener
import com.yada.wx.around.dhtz.biz.DhtzBiz
import org.apache.kafka.clients.consumer.ConsumerRecord

class DhtzListener(dhtzBiz: DhtzBiz = new DhtzBiz) extends KafkaListener {

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
    // 动户通知记录处理
    record.key() match {
      // 绑定默认卡、更换默认卡
      case "BindingBindingDef" => dhtzBiz.saveAllCustomerInfo(record.value())
      // 解除绑定
      case "unBinding" => dhtzBiz.unBinding(record.value())
      // 修改推送管理设置
      case "AllCustomerInfoUpdateNoticeByIdentityNo" => dhtzBiz.updateNotice(record.value())
    }
  }

}
