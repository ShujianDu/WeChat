package com.yada.sdk.point.protocol.impl

import com.yada.sdk.point.protocol.PointReq

/**
  * 积分明细查询
  */
class P0002_GetDetails(ecifNo: String) extends PointReq {
  // 设置ECIF号
  setReqBodyProps("EcifNo", ecifNo)

  /**
    * 交易码
    *
    * @return
    */
  override def tranCode: String = "0002"
}
