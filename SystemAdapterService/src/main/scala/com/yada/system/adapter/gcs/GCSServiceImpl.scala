package com.yada.system.adapter.gcs

import com.yada.sdk.gcs.protocol.ErrorGCSReturnCodeException
import com.yada.sdk.gcs.protocol.impl._

import scala.collection.mutable.ListBuffer

class GCSServiceImpl extends GCSService {

  /**
    * 临时提额评测
    *
    * 报文默认值:
    * AT5605 - 任务类型标识 - "2临增"
    * AT5602 - 渠道系统标识 - "06微信"
    *
    * @param creditLimitTemporaryUpReviewParams CreditLimitTemporaryUpReviewParams
    * @return 临时提额评测结果
    */
  override def creditLimitTemporaryUpReview(creditLimitTemporaryUpReviewParams: CreditLimitTemporaryUpReviewParams): GCSCreditLimitTemporaryUpReviewResult = {
    val tS190024 = new TS190024(creditLimitTemporaryUpReviewParams.sessionId, creditLimitTemporaryUpReviewParams.channelId, creditLimitTemporaryUpReviewParams.certType,
      creditLimitTemporaryUpReviewParams.certNum, creditLimitTemporaryUpReviewParams.phoneNumber, creditLimitTemporaryUpReviewParams.cardNo, creditLimitTemporaryUpReviewParams.currencyNo, "2", "06")()
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
    * @param amountLimitParams AmountLimitParams
    * @return 账单金额上下限结果
    */
  override def getAmountLimit(amountLimitParams: AmountLimitParams): GCSAmountLimit = {
    val ts010102 = new TS010102(amountLimitParams.sessionId, amountLimitParams.channelId, amountLimitParams.cardNo, amountLimitParams.currencyCode)()
    val tempResult = ts010102.send
    val (accountId, accountRefNo) = tempResult.pageListValues(Array("accountId", "accountRefNo")).map(props => (props("accountId"), props("accountRefNo"))).head
    val ts011062 = new TS011062(amountLimitParams.sessionId, amountLimitParams.channelId, accountId, amountLimitParams.currencyCode)()
    val result = ts011062.send

    GCSAmountLimit(amountLimitParams.currencyCode, result.pageValue("minAmount"), result.pageValue("maxAmount"), result.systemValue("returnCode"), accountId, accountRefNo)
  }

  /** *
    * 根据实体卡取得虚拟卡
    *
    * @param cardNoParams CardNoParams
    * @return 虚拟卡卡号列表
    */
  override def getVirtualCards(cardNoParams: CardNoParams): List[CardNoResult] = {
    //一次最大查询99条
    val totalNum = "99"
    val rs = ListBuffer.empty[CardNoResult]
    def query(startNo: String): List[CardNoResult] = {
      val ts011031 = new TS011031(cardNoParams.sessionID, cardNoParams.channelID, cardNoParams.cardNo, startNo, totalNum)()
      val temp = ts011031.send
      rs ++= temp.pageListValues(Array("p0110311List")).map(v => CardNoResult(v("virtualCardNo")))
      temp.pageValue("flag") match {
        case "1" =>
          query((startNo.toInt + 1).toString)
        case "0" =>
          rs.toList
      }
    }
    query("1")
  }

  /**
    * 查询账单明细
    *
    * @param billingDetailsParams BillingDetailsParams
    * @return
    */
  override def getBillingDetails(billingDetailsParams: BillingDetailsParams): List[BillingDetailResult] = {
    val ts010310 = new TS010310(billingDetailsParams.sessionId, billingDetailsParams.channelId, billingDetailsParams.cardNo, billingDetailsParams.currencyCode,
      billingDetailsParams.queryType, billingDetailsParams.startNum, billingDetailsParams.totalNum, billingDetailsParams.startDate, billingDetailsParams.endDate)()
    ts010310.send.pageListValues(v =>
      BillingDetailResult(billingDetailsParams.cardNo, billingDetailsParams.currencyCode, v("transactionDate"), v("transactionAmount"), v("transactionDescription"), v("debitCreditCode"))
    )
  }

  /**
    * 币种查询
    *
    * @param cardNoParams CardNoParams
    * @return 该卡的币种
    */
  override def getCurrencyCodes(cardNoParams: CardNoParams): List[CurrencyCodeResult] = {
    val ts010102 = new TS010102(cardNoParams.sessionID, cardNoParams.channelID, cardNoParams.cardNo)()
    ts010102.send.pageListValues(Array("currencyCode")).map(m => CurrencyCodeResult(m("currencyCode")))
  }

  /**
    * 账单摘要查询
    *
    * @param billingSummaryParams BillingSummaryParams
    * @return
    */
  override def getBillingSummary(billingSummaryParams: BillingSummaryParams): BillingSummaryResult = {
    val ts010302 = new TS010302(billingSummaryParams.sessionId, billingSummaryParams.channelId, billingSummaryParams.statementNo, billingSummaryParams.accountId)()
    val ts010302Resp = ts010302.send
    val periodStartDate = ts010302Resp.pageValue("periodStartDate")
    val periodEndDate = ts010302Resp.pageValue("periodEndDate")
    val paymentDueDate = ts010302Resp.pageValue("paymentDueDate")
    val closingBalance = ts010302Resp.pageValue("closingBalance")
    val currencyCode = ts010302Resp.pageValue("currencyCode")
    val minPaymentAmount = ts010302Resp.pageValue("minPaymentAmount")
    BillingSummaryResult(periodStartDate, periodEndDate, paymentDueDate, closingBalance, currencyCode, minPaymentAmount)
  }

  /**
    * 查询卡的币种和产品类型
    *
    * @param cardNoParams CardNoParams
    * @return (币种列表,卡片产品类型)
    */
  override def getCardCurrencyCodeAndStyle(cardNoParams: CardNoParams): CardCurrencyCodeAndStyleResult = {
    val ts010102 = new TS010102(cardNoParams.sessionID, cardNoParams.channelID, cardNoParams.cardNo)()
    val result = ts010102.send
    val pageList = result.pageListValues(Array("currencyCode", "productType"))
    CardCurrencyCodeAndStyleResult(pageList.map(f => CurrencyCodeResult(f("currencyCode"))), pageList.map(f => f("productType")).head)
  }

  /**
    * 账单寄送方式查询
    *
    * @param cardNoParams CardNoParams
    * @return
    */
  override def getBillSendType(cardNoParams: CardNoParams): BillSendTypeResult = {
    val ts010002 = new TS010002(cardNoParams.sessionID, cardNoParams.channelID, cardNoParams.cardNo)()
    BillSendTypeResult(ts010002.send.pageValue("billSendType"))
  }

  /**
    * 获取预约办卡用户手机号
    *
    * @param mobilePhoneParams MobilePhoneParams
    * @return 返回手机号
    */
  override def getMobilePhone(mobilePhoneParams: MobilePhoneParams): MobilePhoneResult = {
    val ts140028 = new TS140028(mobilePhoneParams.sessionId, mobilePhoneParams.channelId, mobilePhoneParams.idType, mobilePhoneParams.idNo)()
    MobilePhoneResult(ts140028.send.pageValue("appiMcMPhone"))
  }

  /**
    * 账单寄送方式修改
    *
    * @param updateBillSendTypeParams UpdateBillSendTypeParams
    * @return BooleanResult
    */
  override def updateBillSendType(updateBillSendTypeParams: UpdateBillSendTypeParams): BooleanResult = {
    val ts010056 = new TS010056(updateBillSendTypeParams.sessionId, updateBillSendTypeParams.channelId, updateBillSendTypeParams.cardNo, updateBillSendTypeParams.billSendType)()
    try {
      ts010056.send
      BooleanResult(true)
    } catch {
      case e: ErrorGCSReturnCodeException => BooleanResult(false)
    }
  }

  /**
    * 查询所有可分期的消费交易
    *
    * @param consumptionInstallmentsParams ConsumptionInstallmentsParams
    * @return (交易数量,是否有下一页,交易集合)
    */
  override def getConsumptionInstallments(consumptionInstallmentsParams: ConsumptionInstallmentsParams): ConsumptionInstallmentsResult = {
    val ts011007 = new TS011007(consumptionInstallmentsParams.sessionId, consumptionInstallmentsParams.channelId, consumptionInstallmentsParams.cardNo,
      consumptionInstallmentsParams.currencyCode, consumptionInstallmentsParams.startNumber, consumptionInstallmentsParams.selectNumber)()
    val result = ts011007.send
    val transactionNumber = result.pageValue("transactionNumber")
    val isFollowUp = result.pageValue("isFollowUp")
    val list = result.pageListValues(f => {
      GCSConsumptionInstallmentsEntity(f.getOrElse("cardNo", ""), f.getOrElse("transactionDate", ""), f.getOrElse("transactionAmount", ""),
        f.getOrElse("debitCreditCode", ""), f.getOrElse("transactionDescription", ""), f.getOrElse("accountID", ""), f.getOrElse("accountedID", ""),
        f.getOrElse("accountNoID", ""))
    })
    //isFollowUp ：1-有下一页，0-没有下一页
    ConsumptionInstallmentsResult(transactionNumber, isFollowUp == "1", list)
  }

  /**
    * 信用卡挂失-永久挂失
    *
    * @param creditCardReportLostParams CreditCardReportLostParams
    * @return 返回是否挂失成功 true/false
    */
  override def creditCardReportLost(creditCardReportLostParams: CreditCardReportLostParams): BooleanResult = {
    val ts010052 = new TS010052(creditCardReportLostParams.sessionId, creditCardReportLostParams.channelId, creditCardReportLostParams.cardNo, creditCardReportLostParams.idType,
      creditCardReportLostParams.idNum, creditCardReportLostParams.familyName, creditCardReportLostParams.lossReason)()
    try {
      ts010052.send
      BooleanResult(true)
    } catch {
      case e: ErrorGCSReturnCodeException => BooleanResult(false)
    }
  }

  /**
    * 信用卡临时挂失
    *
    * @param tempCreditCardReportLostParams TempCreditCardReportLostParams
    * @return 返回是否临时挂失成功 true/false
    */
  override def tempCreditCardReportLost(tempCreditCardReportLostParams: TempCreditCardReportLostParams): BooleanResult = {

    val ts010059 = new TS010059(tempCreditCardReportLostParams.sessionId, tempCreditCardReportLostParams.channelId, tempCreditCardReportLostParams.cardNo,
      tempCreditCardReportLostParams.entyMethod, tempCreditCardReportLostParams.idNum, tempCreditCardReportLostParams.idType, tempCreditCardReportLostParams.familyName,
      tempCreditCardReportLostParams.lostReason)()
    try {
      ts010059.send
      BooleanResult(true)
    } catch {
      case e: ErrorGCSReturnCodeException => BooleanResult(false)
    }
  }

  /**
    * 解除临时挂失(2013-09-23新增)
    *
    * @param relieveCreditCardTempReportLostParams RelieveCreditCardTempReportLostParams
    * @return 返回是否解除临时挂失成功
    */
  override def relieveCreditCardTempReportLost(relieveCreditCardTempReportLostParams: RelieveCreditCardTempReportLostParams): BooleanResult = {

    val ts010060 = new TS010060(relieveCreditCardTempReportLostParams.sessionId, relieveCreditCardTempReportLostParams.channelId, relieveCreditCardTempReportLostParams.cardNo,
      relieveCreditCardTempReportLostParams.idNum, relieveCreditCardTempReportLostParams.familyName, relieveCreditCardTempReportLostParams.idType)()
    try {
      ts010060.send
      BooleanResult(true)
    } catch {
      case e: ErrorGCSReturnCodeException => BooleanResult(false)
    }
  }

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
    * @param gcsTemporaryUpCommitParams 临时提额授权参数
    * @return 临时提额授权结果
    */
  override def temporaryUpCommit(gcsTemporaryUpCommitParams: GCSTemporaryUpCommitParams): BooleanResult = {
    val ts220001 = new TS220001(gcsTemporaryUpCommitParams.sessionId, gcsTemporaryUpCommitParams.channelId, gcsTemporaryUpCommitParams)()
    try {
      ts220001.send
      BooleanResult(true)
    } catch {
      case e: ErrorGCSReturnCodeException => BooleanResult(false)
    }
  }

  /**
    * 海淘卡挂失
    *
    * @param cardNoParams CardNoParams
    * @return 返回是否挂失成功true/false
    */
  override def wbicCardLost(cardNoParams: CardNoParams): BooleanResult = {
    val ts010063 = new TS010063(cardNoParams.sessionID, cardNoParams.channelID, cardNoParams.cardNo)()
    try {
      ts010063.send
      BooleanResult(true)
    } catch {
      case e: ErrorGCSReturnCodeException => BooleanResult(false)
    }
  }

  /**
    * 为海淘卡用户发送短信
    * GCS-TS011113
    *
    * @param cardNoParams CardNoParams
    * @return 返回是否发送成功 true/false
    */
  override def wbicCardSendSMS(cardNoParams: CardNoParams): BooleanResult = {
    val ts011113 = new TS011113(cardNoParams.sessionID, cardNoParams.channelID, cardNoParams.cardNo)()
    try {
      ts011113.send
      BooleanResult(true)
    } catch {
      case e: ErrorGCSReturnCodeException => BooleanResult(false)
    }
  }

  /**
    * 信用卡额度临时提额调整状态查询
    *
    * @param temporaryUpCommitStatusParams TemporaryUpCommitStatusParams
    * @return 信用卡额度临时提额调整状态列表
    */
  override def getTemporaryUpCommitStatus(temporaryUpCommitStatusParams: TemporaryUpCommitStatusParams): List[GCSCreditLimitTemporaryUpStatus] = {
    val ts140031 = new TS140031(temporaryUpCommitStatusParams.sessionId, temporaryUpCommitStatusParams.channelId, temporaryUpCommitStatusParams.eosIDType, temporaryUpCommitStatusParams.idNumber,
      temporaryUpCommitStatusParams.cardNo, temporaryUpCommitStatusParams.eosId)()
    ts140031.send.pageListValues(m =>
      GCSCreditLimitTemporaryUpStatus(m.getOrElse("eosId", ""), m.getOrElse("eosState", ""),
        m.getOrElse("eosImpTime", ""), m.getOrElse("eosLimit", ""), m.getOrElse("eosStarLimitDate", ""),
        m.getOrElse("eosEndlimitdate", ""))
    )
  }

  /**
    * 查询消费分期试算结果
    * transactionNo - 3110( POS消费还原)
    * mcc -“5311”
    * installmentPlan - “EP01”
    * merchantID - “0000000”
    *
    * @param params 参数实体
    * @return 消费分期试算结果
    */
  override def costConsumptionInstallment(params: GCSConsumptionInstallmentParams): GCSConsumptionInstallmentResult = {
    val ts011172 = new TS011172(params)()
    val result = ts011172.send
    GCSConsumptionInstallmentResult(result.pageValue("instalAmount"), result.pageValue("instalmentFee"), result.pageValue("installmentsAlsoAmountFirst"),
      result.pageValue("installmentsAlsoAmountEach"), result.pageValue("billFeeMeans"), result.pageValue("installmentsNumber"))
  }

  /**
    * 账单分期费用试算
    *
    * @param gcsBillInstallmentParams 账单分期费用试算参数
    * @return 账单分期费用试算结果
    */
  override def getBillCost(gcsBillInstallmentParams: GCSBillInstallmentParams): GCSBillInstallmentResult = {
    val ts011170 = new TS011170(gcsBillInstallmentParams)()
    val result = ts011170.send

    GCSBillInstallmentResult(result.pageValue("currentBillMinimum"), result.pageValue("installmentsfee"), result.pageValue("installmentsAlsoAmountFirst"),
      result.pageValue("installmentsAlsoAmountEach"), result.pageValue("currentBillSurplusAmount"), result.pageValue("billFeeMeans"), result.pageValue("installmentsNumber"))
  }

  /**
    * 根据卡号查询客户信息 - TS010201
    *
    * @param cardNoParams CardNoParams
    * @return GCSCardHolderInfo
    */
  override def getCardHolderInfo(cardNoParams: CardNoParams): CardHolderInfoResult = {
    val ts010201 = new TS010201(cardNoParams.sessionID, cardNoParams.channelID, cardNoParams.cardNo)()
    val result = ts010201.send

    CardHolderInfoResult(result.pageValue("familyName"), result.pageValue("firstName"), result.pageValue("gender"), result.pageValue("mobileNo"))
  }

  /**
    * 查账单周期
    *
    * @param cardNoParams CardNoParams
    * @return
    */
  override def getBillingPeriods(cardNoParams: CardNoParams): List[BillingPeriodsResult] = {
    val ts010301 = new TS010301(cardNoParams.sessionID, cardNoParams.channelID, cardNoParams.cardNo)()
    val ts010301Resp = ts010301.send
    ts010301Resp.pageListValues(props => {
      val accountId = props("accountId")
      val currencyCode = props("currencyCode")
      val periodStartDate = props("periodStartDate")
      val periodEndDate = props("periodEndDate")
      val statementNo = props("statementNo")
      BillingPeriodsResult(accountId, currencyCode, periodStartDate, periodEndDate, statementNo)
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
    * @param params 参数实体
    * @return GCS返回码
    */
  override def authorizationConsumptionInstallment(params: GCSConsumptionInstallmentParams): GCSReturnCodeResult = {
    val ts011173 = new TS011173(params)()
    GCSReturnCodeResult(ts011173.send.pageValue("authReturnCode"))
  }

  /** *
    * 根据证件号和类型查询客户手机号
    *
    * @param custMobileParams CustMobileParams
    * @return 手机号
    */
  override def getCustMobile(custMobileParams: CustMobileParams): MobilePhoneResult = {
    val ts011101 = new TS011101(custMobileParams.sessionId, custMobileParams.channelId, custMobileParams.idType, custMobileParams.idNum)()
    MobilePhoneResult(ts011101.send.pageValue("mobilePhone"))
  }

  /**
    * 查询海淘卡
    * -海淘卡只会有一张正常状态的卡片
    *
    * @param wbicCardInfoParams WbicCardInfoParams
    * @return 返回海淘卡
    */
  override def getWbicCardInfo(wbicCardInfoParams: WbicCardInfoParams): WbicCardInfoResult = {

    val ts011111 = new TS011111(wbicCardInfoParams.sessionId, wbicCardInfoParams.channelId, wbicCardInfoParams.idNum, wbicCardInfoParams.idType, wbicCardInfoParams.productCode)()
    //查找卡状态为“ ”空格的卡集合，然后返回第一个元素
    WbicCardInfoResult(ts011111.send.pageListValues[(String, String)](m =>
      (m("cardNo"), m("cardStatus"))
    ).filter(v => v._2.trim.length == 0).map(m => m._1).headOption)
  }

  /**
    * 账单分期授权
    *
    * @param gcsBillInstallmentParams 账单分期授权参数
    * @return GCS返回码
    */
  override def billInstallment(gcsBillInstallmentParams: GCSBillInstallmentParams): GCSReturnCodeResult = {
    val ts011171 = new TS011171(gcsBillInstallmentParams)()
    GCSReturnCodeResult(ts011171.send.pageValue("authReturnCode"))
  }

  /**
    * 根据证件类型和证件号查询所有卡信息
    *
    * @param cardInfosParams CardInfosParams
    * @return (cardNo,主付卡标识)的集合
    */
  override def getCardInfos(cardInfosParams: CardInfosParams): List[CardInfosResult] = {
    val giveUpCardStatus = List("AC", "ACCC", "CANC")
    val giveUpCardPre = List("3", "4", "5", "6")

    val listBuffer = ListBuffer.empty[CardInfosResult]

    //循环查询所有的数据
    def query(startNum: String): List[CardInfosResult] = {
      val ts011005 = new TS011005(cardInfosParams.sessionId, cardInfosParams.channelId, None, Some(cardInfosParams.idType), Some(cardInfosParams.idNum), startNum, "10")()
      val result = ts011005.send
      listBuffer ++= result.pageListValues(m => {
        (m("cardNo"), m("cardStatus"), m("mainFlag"))
      }).filter(v => !giveUpCardStatus.contains(v._2) && !giveUpCardPre.contains(v._1.head.toString)).map(m => CardInfosResult(m._1, m._3))

      result.pageValue("isHaveNext") match {
        case "1" =>
          query(startNum + 1)
        case "0" =>
          listBuffer.toList
      }
    }
    //查询第一页
    query("1")
  }

  /**
    * 根据卡号查询额度
    * 1、根据卡号查询币种
    * 2、根据卡号和币种查询额度
    *
    * @param cardNoParams CardNoParams
    * @return GCSBalance
    */
  override def getBalance(cardNoParams: CardNoParams): List[BalanceResult] = {

    val currencyCodeResults = getCurrencyCodes(cardNoParams)

    if (currencyCodeResults.isEmpty) throw new IllegalArgumentException("currencyCodes can`t be empty...")

    currencyCodeResults.map(
      currencyCodeResult => {
        val ts410103 = new TS410103(cardNoParams.sessionID, cardNoParams.channelID, cardNoParams.cardNo, currencyCodeResult.currencyCode)()
        val ts410103resp = ts410103.send
        val wholeCreditLimit = ts410103resp.pageValue("wholeCreditLimit")
        val periodAvailableCreditLimit = ts410103resp.pageValue("periodAvailbleCreditLimit") // GCS报文拼装的单词错误
        val preCashAdvanceCreditLimit = ts410103resp.pageValue("preCashAdvanceCreditLimit")
        BalanceResult(cardNoParams.cardNo, currencyCodeResult.currencyCode, wholeCreditLimit, periodAvailableCreditLimit, preCashAdvanceCreditLimit)
      }
    )
  }

  /**
    * 历史分期查询
    *
    * @param historyInstallmentParams 参数
    * @return 历史分期查询结果
    */
  override def getHistoryInstallment(historyInstallmentParams: HistoryInstallmentParams): HistoryInstallmentResult = {
    val ts011021 = new TS011021(historyInstallmentParams.sessionId,historyInstallmentParams.channelId,historyInstallmentParams.cardNo,historyInstallmentParams.startNumber,historyInstallmentParams.selectNumber)()
    val rs = ts011021.send
    HistoryInstallmentResult(rs.pageValue("transactionNumber"),rs.pageValue("isFollowUp").toInt !=0 ,rs.pageListValues(props =>
      HistoryInstallmentEntity(props.getOrElse("cardNo",""),props.getOrElse("instalmentOriginalTransactionDate",""),props.getOrElse("instalmentRuleDescription",""),
        props.getOrElse("currencyCode",""),props.getOrElse("instalmentOriginalAmount",""),props.getOrElse("instalmentOriginalNumber",""),
        props.getOrElse("instalmentCompleteDate",""),props.getOrElse("instFeeFlag",""),props.getOrElse("instalmentFirstPostingAmount",""),
        props.getOrElse("instalmentNextPostingAmount",""),props.getOrElse("instalmentPostedNumber",""),props.getOrElse("instalmentReversalAmount",""),
        props.getOrElse("instalmentOutstandingNumber",""),props.getOrElse("instalmentOutstandingAmount",""),props.getOrElse("instalmentNextPostingDate","")
        )
    ))
  }
}

object GCSServiceImpl extends GCSServiceImpl
