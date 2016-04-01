package com.yada.sdk.ds.protocol.impl

import com.yada.sdk.ds.protocol.DSReq

/**
  * 根据查询条件查询客户信用卡申请进度
  */
class P_8WEC060001(id: String, idType: String, name: String, currPage: String) extends DSReq {
  setBodyValue("id", id)
  setBodyValue("idType", idType)
  setBodyValue("name", name)
  setBodyValue("currPage", currPage)

  /**
    * 交易号
    *
    * @return
    */
  override def txnId: String = "8WEC060001"
}
