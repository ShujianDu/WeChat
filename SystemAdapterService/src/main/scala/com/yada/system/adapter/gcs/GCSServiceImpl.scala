package com.yada.system.adapter.gcs

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.req.{TS010301Req, TS410103Req, TS010102Req}
import com.yada.sdk.gcs.protocol.resp.{TS010301Resp, TS010102Resp}

/**
  * Created by locky on 2016/3/17.
  */
class GCSServiceImpl extends GCSService {

  var gcsClient = GCSClient()

  /**
    * 根据一组卡号查询额度
    * 1、根据卡号查询币种
    * 2、根据卡号和币种查询额度
    *
    * @param sessionID gcsSessionId
    * @param channelID 渠道编号
    * @param cardNos   一组卡号
    * @return GCSBalance
    */
  override def getBalance(sessionID: String, channelID: String, cardNos: List[String]): List[GCSBalance] = {
    cardNos.flatMap(cardNo => {
      //根据卡号查询币种
      val ts010102req = new TS010102Req(transactionSessionId = sessionID, requestChannelId = channelID, cardNo = cardNo)
      val ts010102respXML = gcsClient.send(ts010102req.toXml)
      val ts010102resp = new TS010102Resp(ts010102respXML)
      if (!ts010102resp.isSuccess) throw new RuntimeException(s"error response XML...$ts010102respXML")
      ts010102resp.pageListValues(props => {
        // 根据卡号和币种查询额度
        val currencyCode = props("currencyCode")
        val ts410103req = new TS410103Req(sessionID, channelID, cardNo, currencyCode)
        val ts410103respXML = gcsClient.send(ts410103req.toXml)
        val ts410103resp = new TS010102Resp(ts410103respXML)
        // TODO 3值的对应
        val limitCount = ts410103resp.pageValue("")
        val availableCount = ts410103resp.pageValue("")
        val preCashAdvanceCreditLimit = ts410103resp.pageValue("")
        GCSBalance(cardNo, currencyCode, limitCount, availableCount, preCashAdvanceCreditLimit)
      }).toList
    })
  }

  /**
    * 临时提额评测
    *
    * 报文默认值:
    * AT5605 - 任务类型标识 - "2临增"
    * AT5602 - 渠道系统标识 - "06微信"
    *
    * @param sessionId   gcsSessionId
    * @param channelId   渠道编号
    * @param certType    客户证件类型
    * @param certNum     客户证件号码
    * @param phoneNumber 手机号
    * @param cardNo      卡号
    * @param currencyNo  币种
    * @return 临时提额评测结果
    */
  override def creditLimitTemporaryUpReview(sessionId: String, channelId: String, certType: String, certNum: String, phoneNumber: String, cardNo: String, currencyNo: String): GCSCreditLimitTemporaryUpReviewResult = ???

  /**
    * 获取账单金额上下限
    * 1、根据卡号、币种查询查询账户ID
    * 2、根据账户ID、币种查询账单分期金额上下限
    *
    * @param sessionId    gcsSessionId
    * @param channelId    渠道编号
    * @param cardNo       卡号
    * @param currencyCode 币种
    * @return 账单金额上下限结果
    */
  override def getAmountLimit(sessionId: String, channelId: String, cardNo: String, currencyCode: String): GCSAmountLimit = ???

  /** *
    * 根据实体卡取得虚拟卡
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param cardNo    卡号
    * @return 虚拟卡卡号列表
    */
  override def getVirtualCards(sessionId: String, channelId: String, cardNo: String): List[String] = ???

  /**
    * 查询账单明细
    *
    * @param sessionId    gcsSessionId
    * @param channelId    渠道编号
    * @param cardNo       卡号
    * @param currencyCode 交易币种
    * @param queryType    查询类型
    * @param startNum     起始条数
    * @param totalNum     显示条数
    * @param startDate    交易开始日期
    * @param endDate      交易结束日期
    * @return
    */
  override def getBillingDetails(sessionId: String, channelId: String, cardNo: String, currencyCode: String, queryType: String, startNum: String, totalNum: String, startDate: String, endDate: String): List[GCSBillingDetail] = ???

  /**
    * 根据卡号查询额度
    * 1、根据卡号查询币种
    * 2、根据卡号和币种查询额度
    *
    * @param sessionID gcsSessionId
    * @param channelID 渠道编号
    * @param cardNo    卡号
    * @return GCSBalance
    */
  override def getBalance(sessionID: String, channelID: String, cardNo: String): GCSBalance = {
    // TODO 币种可能有多个...TO ZQD
    ???
  }

  /**
    * 币种查询
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param cardNo    卡号
    * @return 该卡的币种
    */
  override def getCurrencyCodes(sessionId: String, channelId: String, cardNo: String): List[String] = ???

  /**
    * 账单摘要查询
    *
    * @param sessionId   gcsSessionId
    * @param channelId   渠道编号
    * @param statementNo 账期号
    * @param accountId   账户ID
    * @return
    */
  override def getBillingSummary(sessionId: String, channelId: String, statementNo: String, accountId: String): GCSBillingSummary = ???

  /**
    * 查询卡的币种和产品类型
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param cardNo    卡号
    * @return (币种列表,卡片产品类型)
    */
  override def getCardCurrencyCodeAndStyle(sessionId: String, channelId: String, cardNo: String): (List[String], String) = ???

  /**
    * 账单寄送方式查询
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param cardNo    卡号
    * @return
    */
  override def getBillSendType(sessionId: String, channelId: String, cardNo: String): GCSBillSendType = ???

  /**
    * 获取预约办卡用户手机号
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param idType    证件类型
    * @param idNo      证件号
    * @return 返回手机号
    */
  override def getMobilePhone(sessionId: String, channelId: String, idType: String, idNo: String): String = ???

  /**
    * 账单寄送方式修改
    *
    * @param sessionId    gcsSessionId
    * @param channelId    渠道编号
    * @param cardNo       卡号
    * @param billSendType 账单寄送方式
    * @return
    */
  override def updateBillSendType(sessionId: String, channelId: String, cardNo: String, billSendType: String): Boolean = ???

  /**
    * 查询所有可分期的消费交易
    *
    * @param sessionId    gcsSessionId
    * @param channelId    渠道编号
    * @param cardNo       卡号
    * @param currencyCode 币种
    * @param startNumber  起始条数
    * @param selectNumber 显示条数
    * @return (交易数量,是否有下一页,交易集合)
    */
  override def getConsumptionInstallments(sessionId: String, channelId: String, cardNo: String, currencyCode: String, startNumber: String, selectNumber: String): (String, Boolean, List[GCSConsumptionInstallmentsEntity]) = ???

  /**
    * 信用卡挂失-永久挂失
    *
    * @param sessionId  gcsSessionId
    * @param channelId  渠道编号
    * @param cardNo     卡号
    * @param idType     证件类型
    * @param idNum      证件号
    * @param familyName 姓-海外的只有性，国内是姓名
    * @param lossReason 挂失原因
    * @return 返回是否挂失成功 true/false
    */
  override def creditCardReportLost(sessionId: String, channelId: String, cardNo: String, idType: String, idNum: String, familyName: String, lossReason: String): Boolean = ???

  /**
    * 信用卡临时挂失
    *
    * @param sessionId  gcsSessionId
    * @param channelId  渠道编号
    * @param cardNo     卡号
    * @param entyMethod 卡号录入方式 01-手工  07-接触 98-非接
    * @param idNum      证件号
    * @param idType     证件类型
    * @param familyName 姓-海外的只有性，国内是姓名
    * @param lostReason 挂失原因
    * @return 返回是否临时挂失成功 true/false
    */
  override def tempCreditCardReportLost(sessionId: String, channelId: String, cardNo: String, entyMethod: String, idNum: String, idType: String, familyName: String, lostReason: String): Boolean = ???

  /**
    * 解除临时挂失(2013-09-23新增)
    *
    * @param sessionId  gcsSessionId
    * @param channelId  渠道编号
    * @param cardNo     卡号
    * @param idNum      证件号
    * @param familyName 姓-海外的只有性，国内是姓名
    * @param idType     证件类型
    * @return 返回是否解除临时挂失成功
    */
  override def relieveCreditCardTempReportLost(sessionId: String, channelId: String, cardNo: String, idNum: String, familyName: String, idType: String): Boolean = ???

  /**
    * 临时提额提交
    *
    * 报文默认值:
    * eosID - 工单ID - 固定规则生成
    * eosType - 工单类型  - "02临增"
    * eosReason - 增额原因 - "01客户主动"
    * eosEmergencyDegree - 紧急程度 - "2：中（紧急）"
    * eosCustomerType - 客户预设额度类型 - "1-增额类"
    * eosRequestPath - 工单渠道标示 - "06微信"
    *
    * @param sessionId                  gcsSessionId
    * @param channelId                  渠道编号
    * @param gcsTemporaryUpCommitParams 临时提额授权参数
    * @return 临时提额授权结果
    */
  override def temporaryUpCommit(sessionId: String, channelId: String, gcsTemporaryUpCommitParams: GCSTemporaryUpCommitParams): Boolean = ???

  /**
    * 海淘卡挂失
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param cardNo    卡号
    * @return 返回是否挂失成功true/false
    */
  override def wbicCardLost(sessionId: String, channelId: String, cardNo: String): Boolean = ???

  /**
    * 为海淘卡用户发送短信
    * GCS-TS011113
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param cardNo    卡号
    * @return 返回是否发送成功 true/false
    */
  override def wbicCardSendSMS(sessionId: String, channelId: String, cardNo: String): Boolean = ???

  /**
    * 信用卡额度临时提额调整状态查询
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param eosIDType 证件类型
    * @param idNumber  证件号码
    * @param cardNo    卡号
    * @param eosId     工作单ID
    * @return 信用卡额度临时提额调整状态列表
    */
  override def getTemporaryUpCommitStatus(sessionId: String, channelId: String, eosIDType: String, idNumber: String, cardNo: String, eosId: String): List[GCSCreditLimitTemporaryUpStatus] = ???

  /**
    * 查询消费分期试算结果
    * transactionNo - 3110( POS消费还原)
    * mcc -“5311”
    * installmentPlan - “EP01”
    * merchantID - “0000000”
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param params    参数实体
    * @return 消费分期试算结果
    */
  override def costConsumptionInstallment(sessionId: String, channelId: String, params: GCSConsumptionInstallmentParams): GCSConsumptionInstallmentResult = ???

  /**
    * 账单分期费用试算
    *
    * @param sessionId                gcsSessionId
    * @param channelId                渠道编号
    * @param gcsBillInstallmentParams 账单分期费用试算参数
    * @return 账单分期费用试算结果
    */
  override def queryBillCost(sessionId: String, channelId: String, gcsBillInstallmentParams: GCSBillInstallmentParams): GCSBillInstallmentResult = ???

  /**
    * 根据卡号查询客户信息 - TS010201
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param cardNo    卡号
    * @return
    */
  override def getCardHolderInfo(sessionId: String, channelId: String, cardNo: String): GCSCardHolderInfo = ???

  /**
    * 查账单周期
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param cardNo    卡号
    * @return
    */
  override def getBillingPeriods(sessionId: String, channelId: String, cardNo: String): List[GCSBillingPeriods] = {
    val req = new TS010301Req(sessionId, channelId, cardNo)
    val respXML = gcsClient.send(req.toXml)
    val resp = new TS010301Resp(respXML)
    resp.pageListValues(props => {
      val accountId = props("accountId")
      val currencyCode = props("currencyCode")
      val periodStartDate = props("periodStartDate")
      val periodEndDate = props("periodEndDate")
      val statementNo = props("statementNo")
      GCSBillingPeriods(accountId, currencyCode, periodStartDate, periodEndDate, statementNo)
    }).toList
  }

  /**
    * 消费分期授权
    *
    * 报文默认值:
    * transactionNo - “3110”( POS消费还原)
    * mcc -“5311”
    * installmentPlan - “EP01”
    * merchantID - “0000000”
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param params    参数实体
    * @return GCS返回码
    */
  override def authorizationConsumptionInstallment(sessionId: String, channelId: String, params: GCSConsumptionInstallmentParams): String = ???

  /** *
    * 根据证件号和类型查询客户手机号
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param idType    证件类型
    * @param idNum     证件号
    * @return 手机号
    */
  override def getCustMobile(sessionId: String, channelId: String, idType: String, idNum: String): String = ???

  /**
    * 查询海淘卡
    *
    * @param sessionId   gcsSessionId
    * @param channelId   渠道编号
    * @param idNum       证件号
    * @param idType      证件类型
    * @param productCode 产品代码
    * @return 返回海淘卡
    */
  override def getWbicCardInfo(sessionId: String, channelId: String, idNum: String, idType: String, productCode: String): String = ???

  /**
    * 账单分期授权
    *
    * @param sessionId                gcsSessionId
    * @param channelId                渠道编号
    * @param gcsBillInstallmentParams 账单分期授权参数
    * @return GCS返回码
    */
  override def billInstallment(sessionId: String, channelId: String, gcsBillInstallmentParams: GCSBillInstallmentParams): String = ???

  /**
    * 根据证件类型和证件号查询所有卡信息
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param idType    证件类型
    * @param idNum     证件号码
    * @return (cardNo,主付卡标识)的集合
    */
  override def geCardInfos(sessionId: String, channelId: String, idType: String, idNum: String): List[(String, String)] = ???
}
