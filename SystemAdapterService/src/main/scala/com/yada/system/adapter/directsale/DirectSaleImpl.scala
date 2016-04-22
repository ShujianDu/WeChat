package com.yada.system.adapter.directsale

import com.yada.sdk.ds.protocol.impl.P_8WEC060001

/**
  * 直销系统交易实现
  */
class DirectSaleImpl extends DirectSale {
  /**
    * 预约办卡进度查询
    *
    * @param scheduleOfCrdCardApplyParams ScheduleOfCrdCardApplyParams
    * @return (是否有下一页，卡片申请实体集合)
    */
  override def getScheduleOfCrdCardApply(scheduleOfCrdCardApplyParams: ScheduleOfCrdCardApplyParams): ScheduleOfCrdCardApplyResult = {
    val p = new P_8WEC060001(scheduleOfCrdCardApplyParams.id, scheduleOfCrdCardApplyParams.idType, scheduleOfCrdCardApplyParams.name, scheduleOfCrdCardApplyParams.currentPage)
    val r = p.send
    val hasNextPage = r.bodyValue("isNum").toBoolean
    val vs = r.bodyListValues(List("applyNo", "pdnDes", "passDate", "phase", "phaseDesc"), vs => {
      CardApply(vs.head, vs(1), vs(2), vs(3), vs(4))
    })
    ScheduleOfCrdCardApplyResult(hasNextPage, vs)
  }
}

object DirectSaleImpl extends DirectSaleImpl
