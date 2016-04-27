package com.yada.sdk.point.protocol.impl

import com.yada.sdk.point.IPointClient
import com.yada.sdk.point.protocol.PointReq

/**
  * 根据客户卡号查询客户的ECIF号。
  *
  * @param cardNo 卡号
  */
class P0004_GetECIF(cardNo: String)(implicit client: IPointClient = IPointClient.GLOBAL) extends PointReq(client) {
  setReqBodyProps("CardNo", cardNo)

  /**
    * 交易码
    *
    * @return
    */
  override def tranCode: String = "0004"

}
