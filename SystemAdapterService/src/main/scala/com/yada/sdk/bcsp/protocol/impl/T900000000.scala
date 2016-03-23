package com.yada.sdk.bcsp.protocol.impl

import com.yada.sdk.bcsp.protocol.{SeqNoGenerator, BCSPReq}

/**
  * 通用业务类型，即客户端只需要发送组装好的短信，模板不在短信系统维护
  *
  */
class T900000000(seqNo: String, handsetNo: String, sysId: String, bsnType: String, content: String) extends BCSPReq {
  setReqProps("seqNo", SeqNoGenerator.GLOBAL.get)
  setReqProps("handsetNo", handsetNo)
  setReqProps("sysId", sysId)
  setReqProps("bsnType", bsnType)
  setReqItem(content)
}
