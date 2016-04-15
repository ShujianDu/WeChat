package com.yada.system.adapter.directsale

/**
  * 直销系统service
  */
trait DirectSale {
  /**
    * 预约办卡进度查询
    *
    * @param scheduleOfCrdCardApplyParams ScheduleOfCrdCardApplyParams
    * @return ScheduleOfCrdCardApplyResult
    */
  def getScheduleOfCrdCardApply(scheduleOfCrdCardApplyParams: ScheduleOfCrdCardApplyParams): ScheduleOfCrdCardApplyResult
}

/**
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
  */
case class ScheduleOfCrdCardApplyParams(txnId: String, deviceType: String, deviceToken: String, imei: String, channelNo: String, name: String, idType: String, id: String, currentPage: String)

/**
  *
  * @param hasNext     是否有下一页
  * @param cardApplies 卡片申请实体集合
  */
case class ScheduleOfCrdCardApplyResult(hasNext: Boolean, cardApplies: List[CardApply])

/**
  * 预约办卡进度实体
  *
  * @param applyNo   申请编号
  * @param pdnDes    卡产品
  * @param passDate  申请通过日期
  * @param phase     阶段编码
  * @param phaseDesc 阶段描述
  */
case class CardApply(applyNo: String, pdnDes: String, passDate: String, phase: String, phaseDesc: String)
