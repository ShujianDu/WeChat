package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 根据卡号或证件查询“所有”客户信息和卡信息<br>
  * “卡号”或“证件类型，证件号码”必须有一组必输<br>
  * 能查询到的客户相关信息包括：idType（证件类型）；idNum（证件号）；customerName（客户姓名）；
  * customerNo（客户号）；customerType（客户类型）；vipFlag（VIP标识）；vipCustomer（重要客户标识）；
  * @see com.yada.sdk.gcs.protocol.impl.TS011101
  * @param sessionID          交易会话标识，用来表示客户访问身份
  * @param channelID          交易请求渠道标识
  * @param cardNo             卡号
  * @param idType             证件类型
  *                           01=IDCD #居民身份证
  *                           02=TPID #临时身份证
  *                           03=SSNO #护照
  *                           04=RSBL #户口簿
  *                           05=OFFR #军人身份证
  *                           06=WJID #武装警察身份证
  *                           47=HKID #港澳居民来往内地通行证（香港）
  *                           48=MCID #港澳居民来往内地通行证（澳门）
  *                           49=TWID #台湾居民往来大陆通行证
  *                           08=DPID #外交人员身份证
  *                           09=FRPM #外国人居留许可证
  *                           10=BRPA #边民出入境通行证
  *                           11=OTHC #其他
  *                           33=CPYI #组织机构代码
  * @param idNum              证件号码
  * @param startNum           开始条数。想从第一条开始取值，该值上送“1”
  * @param totalNum           总共需要条数。默认10，如果上送“0”，或“”
  * @param isFilterCardStatus 过滤条件。“0”不过滤卡状态；“1”过滤掉卡状态；
  */
class TS011005(sessionID: String, channelID: String,
               cardNo: Option[String],
               idType: Option[String],
               idNum: Option[String],
               startNum: String,
               totalNum: String,
               isFilterCardStatus: String = "0")(gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {

  cardNo.foreach(setPageProps("cardNo", _))
  idType.foreach(setPageProps("idType", _))
  idNum.foreach(setPageProps("idNum", _))
  setPageProps("startNum", startNum)
  setPageProps("totalNum", totalNum)
  setPageProps("isFilterCardStatus", isFilterCardStatus)

  override def transactionID: String = "011005"

  override def requestChannelId: String = channelID

  override def transactionSessionId: String = sessionID

  override def pageKey: String = "RQ011005"

  override def transactionCode: String = "011005"
}
