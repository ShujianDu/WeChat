package com.yada.sdk.point.protocol.impl

import com.yada.sdk.point.IPointClient
import com.yada.sdk.point.protocol.PointReq

/**
  * 积分余额查询
  *
  * @param ecifNo 积分系统的唯一标识
  */
class P0001_GetBalance(ecifNo: String)(implicit client: IPointClient = IPointClient.GLOBAL) extends PointReq(client) {
  // 设置ECIF NO
  setReqBodyProps("EcifNo", ecifNo)

  /**
    * 交易码
    *
    * @return
    */
  override def tranCode: String = "0001"
}
