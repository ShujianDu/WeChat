package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 查询客户手机预留号码
  * 注：从NUW系统查询预约办卡的客户的手机号
  *
  * @see com.yada.sdk.gcs.protocol.impl.TS011101 （从发卡系统查询用户手机号）
  * @param tranSessionID 交易会话标识，用来表示客户访问身份
  * @param reqChannelID  交易请求渠道标识
  * @param idType        证件类型：<br>
  *                      IDCD	身份证；
  *                      SSNO	护照；
  *                      OFFR	军人身份证；
  *                      HKID	港澳居民来往内地通行证（香港）；
  *                      TWID	台湾居民往来大陆通行证；
  *                      OTHC	其他；
  *                      WJID	武装警察身份证；
  *                      MCID	港澳居民来往内地通行证（澳门）；
  * @param idNo          证件号
  */
class TS140028(tranSessionID: String,
               reqChannelID: String,
               idType: String,
               idNo: String)(implicit gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {

  setPageProps("idType", idType)
  setPageProps("idNo", idNo)

  override def transactionID: String = "140028"

  override def requestChannelId: String = reqChannelID

  override def transactionSessionId: String = tranSessionID

  override def pageKey: String = "RQ140028"

  override def transactionCode: String = "140028"
}
