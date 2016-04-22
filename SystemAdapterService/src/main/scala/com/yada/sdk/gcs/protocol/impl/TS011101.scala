package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 根据证件号码或卡号查询客户信息<br>
  * 查询到的客户信息为：mobilePhone（移动电话）；emailAddress（电子邮箱）；workPhone（公司电话）；
  * familyName（客户姓）；firstName（客户名）；idType（证件类型）；idNum（证件号）；
  * 注：这是从发卡系统查询的信息
  *
  * @see com.yada.sdk.gcs.protocol.impl.TS011005
  * @see com.yada.sdk.gcs.protocol.impl.TS140028 （从NUW系统查询客户预留手机号）
  * @param sessionId 交易会话标识，用来表示客户访问身份
  * @param channelId 交易请求渠道标识
  * @param idType    证件类型
  *                  IDCD	身份证
  *                  SSNO	护照
  *                  OFFR	军人身份证
  *                  HKID	港澳居民来往内地通行证（香港）
  *                  TWID	台湾居民往来大陆通行证
  *                  OTHC	其他
  *                  TPID	临时身份证
  *                  RSBL	户口簿
  *                  WJID	武装警察身份证
  *                  MCID	港澳居民来往内地通行证（澳门）
  *                  DPID	外交人员身份证
  *                  FRPM	外国人居留许可证
  *                  BRPA	边民出入境通行证
  * @param idNum     证件号
  */
class TS011101(sessionId: String,
               channelId: String,
               cardNo: Option[String],
               idType: Option[String],
               idNum: Option[String])(implicit gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {
  cardNo.foreach(setPageProps("cardNo", _))
  idType.foreach(setPageProps("idType", _))
  idNum.foreach(setPageProps("idNum", _))

  override def transactionID: String = "011101"

  override def requestChannelId: String = channelId

  override def transactionSessionId: String = sessionId

  override def pageKey: String = "RQ011101"

  override def transactionCode: String = "011101"
}
