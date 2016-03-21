package com.yada.system.adapter.gcs

import com.yada.sdk.gcs.protocol.{ErrorGCSReturnCodeException, GCSResp}
import com.yada.sdk.gcs.protocol.impl._

import scala.util.Try

/**
  * Created by locky on 2016/3/17.
  */
class GCSServiceImpl extends GCSService {

  /**
    * 根据一组卡信息查询额度
    *
    * @param sessionID gcsSessionId
    * @param channelID 渠道编号
    * @param cardInfos 一组卡信息
    * @return GCSBalance
    */
  override def getBalance(sessionID: String, channelID: String, cardInfos: Map[String, List[String]]): List[GCSBalance] = {
    cardInfos.flatMap(info => {
      val (cardNo, currencyCodes) = info
      getBalance(sessionID, channelID, cardNo, currencyCodes)
    }).toList
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
  override def creditLimitTemporaryUpReview(sessionId: String, channelId: String, certType: String, certNum: String, phoneNumber: String, cardNo: String, currencyNo: String): GCSCreditLimitTemporaryUpReviewResult = {
    val tS190024 = new TS190024(sessionId, channelId, certType, certNum, phoneNumber, cardNo, currencyNo, "2", "06")
    val result = tS190024.send

    GCSCreditLimitTemporaryUpReviewResult(result.pageValue("principalResultID"),
      result.pageValue("amount"),
      result.pageValue("cardStyle"),
      result.pageValue("issuingBranchId"),
      result.pageValue("creditLimit"),
      result.pageValue("pmtCreditLimit")
    )
  }

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
  override def getAmountLimit(sessionId: String, channelId: String, cardNo: String, currencyCode: String): GCSAmountLimit = {
    val ts010102 = new TS010102(sessionId, channelId, cardNo, currencyCode)
    val tempResult = ts010102.send
    val accountId = tempResult.pageListValues(Array("accountId")).map(props => props("accountId")).head
    val ts011062 = new TS011062(sessionId, channelId, accountId, currencyCode)
    val result = ts011062.send

    GCSAmountLimit(currencyCode, result.pageValue("minAmount"), result.pageValue("maxAmount"), result.systemValue("returnCode"))
  }

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
  override def getBillingSummary(sessionId: String, channelId: String, statementNo: String, accountId: String): GCSBillingSummary = {
    val ts010302 = new TS010302(sessionId, channelId, statementNo, accountId)
    val ts010302Resp = ts010302.send
    val periodStartDate = ts010302Resp.pageValue("periodStartDate")
    val periodEndDate = ts010302Resp.pageValue("periodEndDate")
    val paymentDueDate = ts010302Resp.pageValue("paymentDueDate")
    val closingBalance = ts010302Resp.pageValue("closingBalance")
    val currencyCode = ts010302Resp.pageValue("currencyCode")
    val minPaymentAmount = ts010302Resp.pageValue("minPaymentAmount")
    GCSBillingSummary(periodStartDate, periodEndDate, paymentDueDate, closingBalance, currencyCode, minPaymentAmount)
  }

  /**
    * 查询卡的币种和产品类型
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param cardNo    卡号
    * @return (币种列表,卡片产品类型)
    */
  override def getCardCurrencyCodeAndStyle(sessionId: String, channelId: String, cardNo: String): (List[String], String) = {
    val ts010102 = new TS010102(sessionId, channelId, cardNo)
    val result = ts010102.send
    val pageList = result.pageListValues(Array("currencyCode", "productType"))
    (pageList.map(f => f("currencyCode")), pageList.map(f => f("productType")).head)
  }

  /**
    * 账单寄送方式查询
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param cardNo    卡号
    * @return
    */
  override def getBillSendType(sessionId: String, channelId: String, cardNo: String): String = {
    val ts010002 = new TS010002(sessionId, channelId, cardNo)
    ts010002.send.pageValue("billSendType")
  }

  /**
    * 获取预约办卡用户手机号
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param idType    证件类型
    * @param idNo      证件号
    * @return 返回手机号
    */
  override def getMobilePhone(sessionId: String, channelId: String, idType: String, idNo: String): String = {
    val ts140028 = new TS140028(sessionId, channelId, idType, idNo)
    ts140028.send.pageValue("appiMcMPhone")
  }

  /**
    * 账单寄送方式修改
    *
    * @param sessionId    gcsSessionId
    * @param channelId    渠道编号
    * @param cardNo       卡号
    * @param billSendType 账单寄送方式
    * @return
    */
  override def updateBillSendType(sessionId: String, channelId: String, cardNo: String, billSendType: String): Boolean = {
    val ts010056 = new TS010056(sessionId, channelId, cardNo, billSendType)
    try {
      ts010056.send
      true
    } catch {
      case e: ErrorGCSReturnCodeException => false
    }
  }

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
    val ts010301 = new TS010301(sessionId, channelId, cardNo)
    val ts010301Resp = ts010301.send
    ts010301Resp.pageListValues(props => {
      val accountId = props("accountId")
      val currencyCode = props("currencyCode")
      val periodStartDate = props("periodStartDate")
      val periodEndDate = props("periodEndDate")
      val statementNo = props("statementNo")
      GCSBillingPeriods(accountId, currencyCode, periodStartDate, periodEndDate, statementNo)
    })
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
  override def getBalance(sessionID: String, channelID: String, cardNo: String, currencyCodes: List[String]): List[GCSBalance] = {
    if (currencyCodes.isEmpty) throw new IllegalArgumentException("currencyCodes can`t be empty...")

    currencyCodes.map(
      currencyCode => {
        val ts410103 = new TS410103(sessionID, channelID, cardNo, currencyCode)
        val ts410103resp = ts410103.send
        val wholeCreditLimit = ts410103resp.pageValue("wholeCreditLimit")
        val periodAvailableCreditLimit = ts410103resp.pageValue("periodAvailbleCreditLimit") // GCS报文拼装的单词错误
        val preCashAdvanceCreditLimit = ts410103resp.pageValue("preCashAdvanceCreditLimit")
        GCSBalance(cardNo, currencyCode, wholeCreditLimit, periodAvailableCreditLimit, preCashAdvanceCreditLimit)
      }
    )
  }
}
