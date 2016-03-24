package com.yada.sdk.point.protocol.impl

import com.yada.sdk.point.protocol.PointReq

/**
  * 根据ECIF号查询该客户下产品对应积分的到期日。
  */
class P0013_GetProductBalance(ecifNo: String, cardNo: String, productCode: String = "") extends PointReq {
  setReqBodyProps("EcifNo", ecifNo)
  setReqBodyProps("ProductCode", productCode)
  setReqBodyProps("CardNo", cardNo)

  /**
    * 交易码
    *
    * @return
    */
  override def tranCode: String = "P0013"
}
