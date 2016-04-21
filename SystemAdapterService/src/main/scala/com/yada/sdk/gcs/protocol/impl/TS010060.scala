package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 解除临时挂失--总交易
  *
  * @param cardNo     卡号
  * @param idNum      证件号
  * @param familyName 中文姓
  * @param idType     证件类型
  *                   01－居民身份证                         IDCD:身份证
  *                   02－临时身份证                         TPID:临时身份证
  *                   03－护照                               SSNO:护照
  *                   04－户口簿                             RSBL:户口簿
  *                   05－军人身份证                         OFFR:军人身份证
  *                   06－武装警察身份证                     WJID:武装警察身份证
  *                   47－港澳居民来往内地通行证（香港）     HKID:港澳居民来往内地通行证（香港）
  *                   48－港澳居民来往内地通行证（澳门）     MCID:港澳居民来往内地通行证（澳门）
  *                   49－台湾居民往来大陆通行证             TWID:台湾居民往来大陆通行证
  *                   08－外交人员身份证                     DPID:外交人员身份证
  *                   09－外国人居留许可证                   FRPM:外国人居留许可证
  *                   10－边民出入境通行证                   BRPA:边民出入境通行证
  *                   11－其它                               OTHC:其他
  */
class TS010060(sessionId: String, channelId: String, cardNo: String, idNum: String,
               familyName: String, idType: String)(gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {

  setPageProps("cardNo", cardNo)
  setPageProps("entyMethod", "01")
  setPageProps("idNum", idNum)
  setPageProps("idType", idType)
  setPageProps("familyName", familyName)

  override def transactionID: String = "010060"

  override def requestChannelId: String = channelId

  override def transactionSessionId: String = sessionId

  override def pageKey: String = "RQ010060"

  override def transactionCode: String = "010060"
}
