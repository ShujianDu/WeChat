package com.yada.sdk.gcs.protocol.impl

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.GCSReq

/**
  * 账单寄送方式修改
  *
  * @param tranSessionId GCS的交易session标识
  * @param reqChannelId  请求的渠道ID
  * @param cardNo        卡号
  * @param billSendType  账单寄送方式 <br>
  *                      BIZ_TYPE_PROP_CODE                       LAN_VALUE <br>
  *                      402881b62fb89aad012fb89aad920000         0 仅发纸质 <br>
  *                      402881b62fb89aad012fb89ab33f0001         4	公司领取(当前不可用) <br>
  *                      402881b62fb89aad012fb89ab3ac0002         5	分行领取(当前不可用) <br>
  *                      402881b62fb89aad012fb89ab40a0003         6	只寄送汇总对账单 <br>
  *                      402881b62fb89aad012fb89ab4670004         7	只寄送明细对账单 <br>
  *                      402881b62fb89aad012fb89ab4c50005         8	纸质、email和短信、推入式 <br>
  *                      402881b62fb89aad012fb89ab5320006         9	纸质、彩信、短信、推入式 <br>
  *                      402881b62fb89aad012fb89ab5af0007         A	彩信 <br>
  *                      402881b62fb89aad012fb89ab5fd0008         B	短信 <br>
  *                      402881b62fb89aad012fb89ab65b0009         C	推入式 <br>
  *                      402881b62fb89aad012fb89ab6aa000a         D	短信、EMAIL、彩信、推入式 <br>
  *                      402881b62fb89aad012fb89ab707000b         E	email和彩信 <br>
  *                      402881b62fb89aad012fb89ab755000c         F	email和短信 <br>
  *                      402881b62fb89aad012fb89ab7c2000d         G	email和推入式 <br>
  *                      402881b62fb89aad012fb89ab810000e         H	彩信、短信 <br>
  *                      402881b62fb89aad012fb89ab86e000f         I	彩信、推入式 <br>
  *                      402881b62fb89aad012fb89ab8cc0010         J	短信、推入式 <br>
  *                      402881b62fb89aad012fb89ab9290011         K	email和彩信、短信 <br>
  *                      402881b62fb89aad012fb89ab9770012         L	email和彩信、推入式 <br>
  *                      402881b62fb89aad012fb89ab9d50013         M	email和短信、推入式 <br>
  *                      402881b62fb89aad012fb89aba320014         N	彩信、短信、推入式 <br>
  *                      402881b62fb89aad012fb89aba800015         O	纸质、彩信 <br>
  *                      402881b62fb89aad012fb89abacf0016         P	纸质、短信 <br>
  *                      402881b62fb89aad012fb89abb2d0017         Q	纸质、推入式 <br>
  *                      402881b62fb89aad012fb89abb8b0018         R	纸质和email、短信、彩信、推入式 <br>
  *                      402881b62fb89aad012fb89abbe80019         S	纸质、email和彩信 <br>
  *                      402881b62fb89aad012fb89abc36001a         T	纸质、email和短信 <br>
  *                      402881b62fb89aad012fb89abc94001b         U	纸质、email和推入式 <br>
  *                      402881b62fb89aad012fb89abcf1001c         V	纸质、彩信、短信 <br>
  *                      958797e530bbd34b0130bbd34b9c0002         3不发送对账单 <br>
  *                      958797e530bbd34b0130bbd34b9c0003         W 纸质、彩信、推入式 <br>
  *                      958797e530bbd34b0130bbd34b9c0004         X 纸质、短信、推入式 <br>
  *                      958797e530bbd34b0130bbd34b9c0005         Y 纸质、EMAIL、短信、彩信 <br>
  *                      958797e530bbd34b0130bbd34b9c0006         Z 纸质、EMAIL、彩信、推入式 <br>
  *                      958797e530bbd34b0130bbd34b9c0000         1纸质和EMAIL <br>
  *                      958797e530bbd34b0130bbd34b9c0001         2EMAIL <br>
  */
class TS010056(tranSessionId: String, reqChannelId: String, cardNo: String, billSendType: String)(gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {

  setPageProps("cardNo", cardNo)
  setPageProps("billSendType", billSendType)

  override def transactionID: String = "010056"

  override def requestChannelId: String = reqChannelId

  override def transactionSessionId: String = tranSessionId

  override def pageKey: String = "RP010002"

  override def transactionCode: String = "010056"
}
