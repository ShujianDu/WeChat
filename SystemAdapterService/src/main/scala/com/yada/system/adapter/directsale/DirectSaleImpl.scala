package com.yada.system.adapter.directsale

import com.yada.sdk.ds.protocol.impl.P_8WEC060001

/**
  * 直销系统交易实现
  */
class DirectSaleImpl extends DirectSale {
  /**
    * 预约办卡进度查询
    *
    * @param txnId       交易号
    * @param deviceType  设备类型
    * @param deviceToken TOKEN号
    * @param imei        设备号
    * @param channelNo   渠道编码
    * @param name        客户姓名
    * @param idType      证件类型
    * @param id          证件号
    * @param currentPage 当前页
    * @return (是否有下一页，卡片申请实体集合)
    */
  override def getScheduleOfCrdCardApply(txnId: String, deviceType: String, deviceToken: String, imei: String, channelNo: String, name: String, idType: String, id: String, currentPage: String): (Boolean, List[CardApply]) = {
    val p = new P_8WEC060001(id, idType, name, currentPage)
    val r = p.send
    val hasNextPage = r.bodyValue("isNum").toBoolean
    val vs = r.bodyListValues(List("applyNo", "pdnDes", "passDate", "phase", "phaseDesc"), vs => {
      CardApply(vs.head, vs(1), vs(2), vs(3), vs(4))
    })
    hasNextPage -> vs
  }
}
