package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 额度评测交易
  *
  * @param tranSessionID 交易会话标识，用来表示客户访问身份
  * @param reqChannelID  交易请求渠道标识
  * @param certType      客户证件类型：
  *                      IDCD	身份证
  *                      SSNO	护照
  *                      OFFR	军人身份证
  *                      HKID	港澳居民来往内地通行证（香港）
  *                      TWID	台湾居民往来大陆通行证
  *                      OTHC	其他
  *                      TPID	临时身份证
  *                      RSBL	户口簿
  *                      WJID	武装警察身份证
  *                      MCID	港澳居民来往内地通行证（澳门）
  *                      DPID	外交人员身份证
  *                      FRPM	外国人居留许可证
  *                      BRPA	边民出入境通行证
  * @param certNum       客户证号码
  * @param phoneNumber   手机号
  * @param cardNo        卡号
  * @param currencyNo    币种
  *                      CNY = 人民币
  * @param at5605        任务类型标识
  *                      01申请评分；02临增；03长增；04事件式增额；05批量长增
  * @param at5602        渠道系统标识
  *                      01总行NUW；02总行CSR；03总行CRM；04分行落地终端；05网银；06微信；07财富OCRM；08 ACCSRLCMS；09缤纷生活
  */
class TS190024(tranSessionID: String,
               reqChannelID: String,
               certType: String,
               certNum: String,
               phoneNumber: String,
               cardNo: String,
               currencyNo: String,
               at5605: String,
               at5602: String)(implicit gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {
  setPageProps("certType", certType)
  setPageProps("certNum", certNum)
  setPageProps("phoneNumber", phoneNumber)
  setPageProps("cardNo", cardNo)
  setPageProps("currencyNo", currencyNo)
  setPageProps("AT5605", at5605)
  setPageProps("AT5602", at5602)

  override def transactionID: String = "190024"

  override def requestChannelId: String = reqChannelID

  override def transactionSessionId: String = tranSessionID

  override def pageKey: String = "RQ190024"

  override def transactionCode: String = "190024"
}
