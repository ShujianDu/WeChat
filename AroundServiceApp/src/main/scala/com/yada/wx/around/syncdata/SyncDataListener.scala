package com.yada.wx.around.syncdata

import com.yada.wx.around.KafkaListener
import com.yada.wx.around.syncdata.biz.SyncDataBiz
import org.apache.kafka.clients.consumer.ConsumerRecord

class SyncDataListener(syncDataBiz: SyncDataBiz = new SyncDataBiz) extends KafkaListener {

  /**
    * 配置文件关键字
    *
    * @return
    */
  override protected def key: String = "syncdata"

  /**
    * 处理记录
    *
    * @param record 记录
    */
  override def handle(record: ConsumerRecord[String, String]): Unit = {
    // 数据同步
    record.key() match {
      // 绑定处理
      case "BindingCustBinding" => syncDataBiz.syncCustomerInfo(record.value())
      // 绑定默认卡处理
      case "BindingBindingDef" => syncDataBiz.syncCustomerInfoExtAndVirtualCards(record.value())
    }
  }

}
