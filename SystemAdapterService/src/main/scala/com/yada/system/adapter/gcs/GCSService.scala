package com.yada.system.adapter.gcs

import play.api.libs.functional.syntax._
import play.api.libs.json.{Writes, _}

trait GCSService {

  /**
    * 根据卡号查询额度
    *
    * @param cardNoParams 参数
    * @return GCSBalance
    */
  def getBalance(cardNoParams: CardNoParams): List[BalanceResult]

  /**
    * 查账单周期
    *
    * @param cardNoParams 参数
    * @return
    */
  def getBillingPeriods(cardNoParams: CardNoParams): List[BillingPeriodsResult]

  /**
    * 账单摘要查询
    *
    * @param billingSummaryParams BillingSummaryParams
    * @return BillingSummaryResult
    */
  def getBillingSummary(billingSummaryParams: BillingSummaryParams): BillingSummaryResult

  /**
    * 查询账单明细-已出
    *
    * @param billingDetailsParams BillingDetailsSettlementParams
    * @return BillingDetailResult列表
    */
  def getBillingDetailsSettlement(billingDetailsParams: BillingDetailsSettlementParams): List[BillingDetailResult]

  /**
    * 查询账单明细-未出
    *
    * @param billingDetailsParams BillingDetailsUnSettlementParams
    * @return BillingDetailResult列表
    */
  def getBillingDetailsUnSettlement(billingDetailsParams: BillingDetailsUnSettlementParams): List[BillingDetailResult]

  /**
    * 币种查询
    *
    * @param cardNoParams 卡号参数
    * @return 该卡的币种
    */
  def getCurrencyCodes(cardNoParams: CardNoParams): List[CurrencyCodeResult]

  /**
    * 查询卡的币种和产品类型
    *
    * @param cardNoParams 卡号参数
    * @return CardCurrencyCodeAndStyleResult
    */
  def getCardCurrencyCodeAndStyle(cardNoParams: CardNoParams): CardCurrencyCodeAndStyleResult

  /**
    * 账单寄送方式查询
    *
    * @param cardNoParams 卡号参数
    * @return BillSendTypeResult
    */
  def getBillSendType(cardNoParams: CardNoParams): String

  /**
    * 账单寄送方式修改
    *
    * @param updateBillSendTypeParams UpdateBillSendTypeParams
    * @return
    */
  def updateBillSendType(updateBillSendTypeParams: UpdateBillSendTypeParams): Boolean

  /**
    * 信用卡挂失-永久挂失
    *
    * @param creditCardReportLostParams CreditCardReportLostParams
    * @return 返回是否挂失成功 true/false
    */
  def creditCardReportLost(creditCardReportLostParams: CreditCardReportLostParams): Boolean

  /**
    * 信用卡临时挂失
    *
    * @param tempCreditCardReportLostParams TempCreditCardReportLostParams
    * @return 返回是否临时挂失成功 true/false
    */
  def tempCreditCardReportLost(tempCreditCardReportLostParams: TempCreditCardReportLostParams): Boolean

  /**
    * 解除临时挂失
    *
    * @param relieveCreditCardTempReportLost RelieveCreditCardTempReportLost
    * @return 返回是否解除临时挂失成功
    */
  def relieveCreditCardTempReportLost(relieveCreditCardTempReportLost: RelieveCreditCardTempReportLostParams): Boolean

  /**
    * 根据卡号查询客户信息 - TS010201
    *
    * @param cardNoParams 卡号参数
    * @return
    */
  def getCardHolderInfo(cardNoParams: CardNoParams): CardHolderInfoResult

  /**
    * 获取预约办卡用户手机号
    *
    * @param mobilePhoneParams MobilePhoneParams
    * @return 返回手机号
    */
  def getMobilePhone(mobilePhoneParams: MobilePhoneParams): String

  /**
    * 查询海淘卡
    *
    * @param wbicCardInfoParams WbicCardInfoParams
    * @return 返回海淘卡
    */
  def getWbicCardInfo(wbicCardInfoParams: WbicCardInfoParams): WbicCardInfoResult

  /**
    * 海淘卡挂失
    *
    * @param cardNoParams 卡号参数
    * @return 返回是否挂失成功true/false
    */
  def wbicCardLost(cardNoParams: CardNoParams): Boolean

  /**
    * 为海淘卡用户发送短信
    * GCS-TS011113
    *
    * @param cardNoParams 卡号参数
    * @return 返回是否发送成功 true/false
    */
  def wbicCardSendSMS(cardNoParams: CardNoParams): Boolean

  /** *
    * 根据证件号和类型查询客户手机号
    *
    * @param custMobileParams CustMobileParams
    * @return 手机号
    */
  def getCustMobile(custMobileParams: CustMobileParams): String

  /** *
    * 根据实体卡取得虚拟卡
    *
    * @param cardNoParams CardNoParams
    * @return 虚拟卡卡号列表
    */
  def getVirtualCards(cardNoParams: CardNoParams): List[CardNoResult]

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
  def creditLimitTemporaryUpReview(creditLimitTemporaryUpReviewParams: CreditLimitTemporaryUpReviewParams): GCSCreditLimitTemporaryUpReviewResult

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
  def temporaryUpCommit(gcsTemporaryUpCommitParams: GCSTemporaryUpCommitParams): Boolean

  /**
    * 信用卡额度临时提额调整状态查询
    *
    * @param temporaryUpCommitStatusParams TemporaryUpCommitStatusParams
    * @return 信用卡额度临时提额调整状态列表
    */
  def getTemporaryUpCommitStatus(temporaryUpCommitStatusParams: TemporaryUpCommitStatusParams): List[GCSCreditLimitTemporaryUpStatus]

  /**
    * 查询所有可分期的消费交易
    *
    * @param consumptionInstallments ConsumptionInstallments
    * @return ConsumptionInstallmentsResult
    */
  def getConsumptionInstallments(consumptionInstallments: ConsumptionInstallmentsParams): ConsumptionInstallmentsResult

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
  def costConsumptionInstallment(params: GCSConsumptionInstallmentParams): GCSConsumptionInstallmentResult

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
  def authorizationConsumptionInstallment(params: GCSConsumptionInstallmentParams): String

  /**
    * 获取账单金额上下限
    * 1、根据卡号、币种查询查询账户ID
    * 2、根据账户ID、币种查询账单分期金额上下限
    *
    * @param amountLimitParams AmountLimitParams
    * @return 账单金额上下限结果
    */
  def getAmountLimit(amountLimitParams: AmountLimitParams): GCSAmountLimit

  /**
    * 账单分期费用试算
    *
    * @param gcsBillInstallmentParams 账单分期费用试算参数
    * @return 账单分期费用试算结果
    */
  def getBillCost(gcsBillInstallmentParams: GCSBillInstallmentParams): GCSBillInstallmentResult

  /**
    * 账单分期授权
    *
    * @param gcsBillInstallmentParams 账单分期授权参数
    * @return GCS返回码
    */
  def billInstallment(gcsBillInstallmentParams: GCSBillInstallmentParams): String

  /**
    * 根据证件类型和证件号查询所有卡信息
    *
    * @return CardInfosResult的集合
    */
  def getCardInfos(cardInfosParams: CardInfosParams): List[CardInfosResult]

  /**
    * 历史分期查询
    *
    * @param historyInstallmentParams 参数
    * @return 历史分期查询结果
    */
  def getHistoryInstallment(historyInstallmentParams: HistoryInstallmentParams): HistoryInstallmentResult

  /**
    *
    * @param cardNoParams 参数
    * @return 卡片状态
    */
  def getCardStatCode(cardNoParams: CardNoParams):String

  /**
    *
    * @param cardNoParams 参数
    * @return 卡片激活是否成功
    */
  def activationCard(cardNoParams: CardNoParams):Boolean
}

/**
  * 公用的卡号参数
  *
  * @param tranSessionID GCStranSessionID
  * @param reqChannelID  GCS渠道号
  * @param cardNo        卡号
  */
case class CardNoParams(tranSessionID: String, reqChannelID: String, cardNo: String)

object CardNoParams {
  implicit val cardNoParamsReads: Reads[CardNoParams] = (
    (__ \ "tranSessionID").read[String] ~ (__ \ "reqChannelID").read[String] ~ (__ \ "cardNo").read[String]
    ) (CardNoParams.apply _)

  implicit val cardNoParamsWrites: Writes[CardNoParams] = (
    (__ \ "tranSessionID").write[String] ~ (__ \ "reqChannelID").write[String] ~ (__ \ "cardNo").write[String]
    ) (unlift(CardNoParams.unapply))
}

/**
  * 公用的卡号结果
  *
  * @param cardNo 卡号
  */
case class CardNoResult(cardNo: String)

object CardNoResult {
  implicit val cardNoResultWrites: Writes[CardNoResult] = Writes(cardNoResult => Json.toJson(JsObject(Map("cardNo" -> JsString(cardNoResult.cardNo)).toSeq)))
}

/**
  * 卡余额
  *
  * @param cardNo                     卡号
  * @param currencyCode               币种
  * @param wholeCreditLimit           总授信额度
  * @param periodAvailableCreditLimit 总可用额
  * @param preCashAdvanceCreditLimit  取现可用额度
  */
case class BalanceResult(cardNo: String, currencyCode: String, wholeCreditLimit: String, periodAvailableCreditLimit: String, preCashAdvanceCreditLimit: String)

object BalanceResult {
  implicit val balanceResultReads: Reads[BalanceResult] = (
    (__ \ "cardNo").read[String] ~ (__ \ "currencyCode").read[String] ~ (__ \ "wholeCreditLimit").read[String] ~ (__ \ "periodAvailableCreditLimit").read[String] ~ (__ \ "preCashAdvanceCreditLimit").read[String]
    ) (BalanceResult.apply _)

  implicit val balanceResultWrites: Writes[BalanceResult] = (
    (__ \ "cardNo").write[String] ~ (__ \ "currencyCode").write[String] ~ (__ \ "wholeCreditLimit").write[String] ~ (__ \ "periodAvailableCreditLimit").write[String] ~ (__ \ "preCashAdvanceCreditLimit").write[String]
    ) (unlift(BalanceResult.unapply))
}


/**
  * 账单周期
  *
  * @param accountId       账户ID
  * @param currencyCode    币种
  * @param periodStartDate 账期开始日期
  * @param periodEndDate   账期结束日期
  * @param statementNo     账期号
  */
case class BillingPeriodsResult(accountId: String, currencyCode: String, periodStartDate: String, periodEndDate: String, statementNo: String)

object BillingPeriodsResult {
  implicit val billingPeriodsResultReads: Reads[BillingPeriodsResult] = (
    (__ \ "accountId").read[String] ~ (__ \ "currencyCode").read[String] ~ (__ \ "periodStartDate").read[String] ~ (__ \ "periodEndDate").read[String] ~ (__ \ "statementNo").read[String]
    ) (BillingPeriodsResult.apply _)

  implicit val billingPeriodsResultWrites: Writes[BillingPeriodsResult] = (
    (__ \ "accountId").write[String] ~ (__ \ "currencyCode").write[String] ~ (__ \ "periodStartDate").write[String] ~ (__ \ "periodEndDate").write[String] ~ (__ \ "statementNo").write[String]
    ) (unlift(BillingPeriodsResult.unapply))
}

/**
  * 账单摘要
  *
  * @param periodStartDate  账期开始日期
  * @param periodEndDate    账期结束日期/账单日期
  * @param paymentDueDate   到期还款日
  * @param closingBalance   本期结欠金额(应还款额：)
  * @param currencyCode     币种
  * @param minPaymentAmount 最小还款额
  */
//TODO 最小还款额 是否没有用到！
case class BillingSummaryResult(periodStartDate: String, periodEndDate: String, paymentDueDate: String, closingBalance: String, currencyCode: String, minPaymentAmount: String)

object BillingSummaryResult {
  implicit val billingSummaryResultReads: Reads[BillingSummaryResult] = (
    (__ \ "periodStartDate").read[String] ~ (__ \ "periodEndDate").read[String] ~ (__ \ "paymentDueDate").read[String] ~ (__ \ "closingBalance").read[String] ~ (__ \ "currencyCode").read[String] ~ (__ \ "minPaymentAmount").read[String]
    ) (BillingSummaryResult.apply _)

  implicit val billingSummaryResultWrites: Writes[BillingSummaryResult] = (
    (__ \ "periodStartDate").write[String] ~ (__ \ "periodEndDate").write[String] ~ (__ \ "paymentDueDate").write[String] ~ (__ \ "closingBalance").write[String] ~ (__ \ "currencyCode").write[String] ~ (__ \ "minPaymentAmount").write[String]
    ) (unlift(BillingSummaryResult.unapply))
}

/**
  * 账单明细
  *
  * @param cardNo                 卡号
  * @param currencyCode           币种
  * @param transactionDate        交易日期
  * @param transactionAmount      交易金额
  * @param transactionDescription 交易描述
  * @param debitCreditCode        借记,贷记标记  "DEBT"表示:借方 (比如：网上消费这种就是借方) "CRED"表示:贷方 "NMON"表示:非金融交易
  */
//TODO debitCreditCode 改成枚举
case class BillingDetailResult(cardNo: String, currencyCode: String, transactionDate: String, transactionAmount: String, transactionDescription: String, debitCreditCode: String)

object BillingDetailResult {
  implicit val billingDetailResultReads: Reads[BillingDetailResult] = (
    (__ \ "cardNo").read[String] ~ (__ \ "currencyCode").read[String] ~ (__ \ "transactionDate").read[String] ~ (__ \ "transactionAmount").read[String]
      ~ (__ \ "transactionDescription").read[String] ~ (__ \ "debitCreditCode").read[String]
    ) (BillingDetailResult.apply _)

  implicit val billingDetailResultWrites: Writes[BillingDetailResult] = (
    (__ \ "cardNo").write[String] ~ (__ \ "currencyCode").write[String] ~ (__ \ "transactionDate").write[String] ~ (__ \ "transactionAmount").write[String]
      ~ (__ \ "transactionDescription").write[String] ~ (__ \ "debitCreditCode").write[String]
    ) (unlift(BillingDetailResult.unapply))
}

/**
  *
  * 持卡人信息
  *
  * @param familyName       客户姓
  * @param firstName        客户名
  * @param gender           性别
  * @param mobileNo         手机号码
  * @param postalCode       邮政编码
  * @param workUnitName     单位名称
  * @param workUnitPhone    单位电话
  * @param mailBox          电子邮箱
  * @param homeAddressPhone 住宅电话
  * @param billAddressLine  账单地址由账单地址1,账单地址2,账单地址3拼接完成
  */
case class CardHolderInfoResult(familyName: String, firstName: String, gender: String, mobileNo: String, postalCode: String, workUnitName: String,
                                workUnitPhone: String, mailBox: String, homeAddressPhone: String, billAddressLine: String)

object CardHolderInfoResult {
  implicit val cardHolderInfoResultReads: Reads[CardHolderInfoResult] = (
    (__ \ "familyName").read[String] ~ (__ \ "firstName").read[String] ~ (__ \ "gender").read[String] ~ (__ \ "mobileNo").read[String]
      ~ (__ \ "postalCode").read[String] ~ (__ \ "workUnitName").read[String] ~ (__ \ "workUnitPhone").read[String] ~ (__ \ "mailBox").read[String] ~ (__ \ "homeAddressPhone").read[String]
      ~ (__ \ "billAddressLine").read[String]
    ) (CardHolderInfoResult.apply _)

  implicit val cardHolderInfoResultWrites: Writes[CardHolderInfoResult] = (
    (__ \ "familyName").write[String] ~ (__ \ "firstName").write[String] ~ (__ \ "gender").write[String] ~ (__ \ "mobileNo").write[String] ~ (__ \ "postalCode").write[String]
      ~ (__ \ "workUnitName").write[String] ~ (__ \ "workUnitPhone").write[String] ~ (__ \ "mailBox").write[String] ~ (__ \ "homeAddressPhone").write[String] ~ (__ \ "billAddressLine").write[String]
    ) (unlift(CardHolderInfoResult.unapply))
}

/**
  * 临时提额评测结果
  *
  * @param principalResultID 授权是否批准 （A-批准 B-拒绝）
  * @param amount            建议额度
  * @param cardStyle         产品类型（1，个人；2，白金；3，公务卡）
  * @param issuingBranchId   发卡行号
  * @param creditLimit       当前卡整体信用额度
  * @param pmtCreditLimit    当前卡的永久额度
  */
case class GCSCreditLimitTemporaryUpReviewResult(principalResultID: String, amount: String, cardStyle: String, issuingBranchId: String, creditLimit: String, pmtCreditLimit: String)

object GCSCreditLimitTemporaryUpReviewResult {
  implicit val gcsCreditLimitTemporaryUpReviewResultReads: Reads[GCSCreditLimitTemporaryUpReviewResult] = (
    (__ \ "principalResultID").read[String] ~ (__ \ "amount").read[String] ~ (__ \ "cardStyle").read[String] ~ (__ \ "issuingBranchId").read[String]
      ~ (__ \ "creditLimit").read[String] ~ (__ \ "pmtCreditLimit").read[String]
    ) (GCSCreditLimitTemporaryUpReviewResult.apply _)

  implicit val gcsCreditLimitTemporaryUpReviewResultWrites: Writes[GCSCreditLimitTemporaryUpReviewResult] = (
    (__ \ "principalResultID").write[String] ~ (__ \ "amount").write[String] ~ (__ \ "cardStyle").write[String] ~ (__ \ "issuingBranchId").write[String]
      ~ (__ \ "creditLimit").write[String] ~ (__ \ "pmtCreditLimit").write[String]
    ) (unlift(GCSCreditLimitTemporaryUpReviewResult.unapply))
}

/**
  * 信用卡额度临时提额调整状态实体
  *
  * @param eosId            工单ID
  * @param eosState         工单状态 70-审批批准并已同步CSR、0-待分配、0-待重新分配、20-待领取、30-审批中、40-转上级审批中、
  *                         50-审批批准、60-审批拒绝、80-审批拒绝并已同步CSR、21-联机工单接入
  * @param eosImpTime       申请时间
  * @param eosLimit         工单额度
  * @param eosStarLimitDate 增额生效开始日期 格式YYYY-MM-DD
  * @param eosEndLimitDate  增额生效结束日期 格式YYYY-MM-DD
  */
case class GCSCreditLimitTemporaryUpStatus(eosId: String, eosState: String, eosImpTime: String, eosLimit: String, eosStarLimitDate: String, eosEndLimitDate: String)

object GCSCreditLimitTemporaryUpStatus {
  implicit val gcsCreditLimitTemporaryUpStatusReads: Reads[GCSCreditLimitTemporaryUpStatus] = (
    (__ \ "eosId").read[String] ~ (__ \ "eosState").read[String] ~ (__ \ "eosImpTime").read[String] ~ (__ \ "eosLimit").read[String]
      ~ (__ \ "eosStarLimitDate").read[String] ~ (__ \ "eosEndLimitDate").read[String]
    ) (GCSCreditLimitTemporaryUpStatus.apply _)

  implicit val gcsCreditLimitTemporaryUpStatusWrites: Writes[GCSCreditLimitTemporaryUpStatus] = (
    (__ \ "eosId").write[String] ~ (__ \ "eosState").write[String] ~ (__ \ "eosImpTime").write[String] ~ (__ \ "eosLimit").write[String]
      ~ (__ \ "eosStarLimitDate").write[String] ~ (__ \ "eosEndLimitDate").write[String]
    ) (unlift(GCSCreditLimitTemporaryUpStatus.unapply))
}

/**
  * 可分期的消费交易
  *
  * @param cardNo                    卡号
  * @param transactionDate           交易日期
  * @param transactionAmount         交易金额
  * @param debitCreditCode           借方、贷方(交易类型)
  * @param transactionDescription    交易描述i
  * @param accountID                 账户ID
  * @param accountedID               入账账户ID
  * @param accountNoID               账户ID -供消费分期费用试算上送用
  * @param originalCurrencyCode      清算币种
  * @param originalTransactionAmount 清算金额
  * @param transactionCurrencyCode   交易币种
  * @param cycleNumber               周期号
  * @param transactionNo             交易序号
  */
case class GCSConsumptionInstallmentsEntity(cardNo: String, transactionDate: String, transactionAmount: String, debitCreditCode: String, transactionDescription: String,
                                            accountID: String, accountedID: String, accountNoID: String, originalCurrencyCode: String, originalTransactionAmount: String,
                                            transactionCurrencyCode: String, cycleNumber: String, transactionNo: String)

object GCSConsumptionInstallmentsEntity {
  implicit val gcsConsumptionInstallmentsEntityReads: Reads[GCSConsumptionInstallmentsEntity] = (
    (__ \ "cardNo").read[String] ~ (__ \ "transactionDate").read[String] ~ (__ \ "transactionAmount").read[String] ~ (__ \ "debitCreditCode").read[String]
      ~ (__ \ "transactionDescription").read[String] ~ (__ \ "accountID").read[String] ~ (__ \ "accountedID").read[String] ~ (__ \ "accountNoID").read[String]
      ~ (__ \ "originalCurrencyCode").read[String] ~ (__ \ "originalTransactionAmount").read[String] ~ (__ \ "transactionCurrencyCode").read[String]
      ~ (__ \ "cycleNumber").read[String] ~ (__ \ "transactionNo").read[String]
    ) (GCSConsumptionInstallmentsEntity.apply _)

  implicit val gcsConsumptionInstallmentsEntityWrites: Writes[GCSConsumptionInstallmentsEntity] = (
    (__ \ "cardNo").write[String] ~ (__ \ "transactionDate").write[String] ~ (__ \ "transactionAmount").write[String] ~ (__ \ "debitCreditCode").write[String]
      ~ (__ \ "transactionDescription").write[String] ~ (__ \ "accountID").write[String] ~ (__ \ "accountedID").write[String] ~ (__ \ "accountNoID").write[String]
      ~ (__ \ "originalCurrencyCode").write[String] ~ (__ \ "originalTransactionAmount").write[String] ~ (__ \ "transactionCurrencyCode").write[String]
      ~ (__ \ "cycleNumber").write[String] ~ (__ \ "transactionNo").write[String]
    ) (unlift(GCSConsumptionInstallmentsEntity.unapply))
}

/**
  * 消费分期结果
  *
  * @param installmentAmount           分期金额
  * @param installmentFee              分期手续费
  * @param installmentsAlsoAmountFirst 分期后每期应还金额-首期
  * @param installmentsAlsoAmountEach  分期后每期应还金额-后每期
  * @param billFeeMeans                分期手续费收取方式
  * @param installmentsNumber          分期期数
  */
case class GCSConsumptionInstallmentResult(installmentAmount: String, installmentFee: String, installmentsAlsoAmountFirst: String, installmentsAlsoAmountEach: String,
                                           billFeeMeans: String, installmentsNumber: String)

object GCSConsumptionInstallmentResult {
  implicit val gcsConsumptionInstallmentResultReads: Reads[GCSConsumptionInstallmentResult] = (
    (__ \ "installmentAmount").read[String] ~ (__ \ "installmentFee").read[String] ~ (__ \ "installmentsAlsoAmountFirst").read[String] ~ (__ \ "installmentsAlsoAmountEach").read[String]
      ~ (__ \ "billFeeMeans").read[String] ~ (__ \ "installmentsNumber").read[String]
    ) (GCSConsumptionInstallmentResult.apply _)

  implicit val gcsConsumptionInstallmentResultWrites: Writes[GCSConsumptionInstallmentResult] = (
    (__ \ "installmentAmount").write[String] ~ (__ \ "installmentFee").write[String] ~ (__ \ "installmentsAlsoAmountFirst").write[String] ~ (__ \ "installmentsAlsoAmountEach").write[String]
      ~ (__ \ "billFeeMeans").write[String] ~ (__ \ "installmentsNumber").write[String]
    ) (unlift(GCSConsumptionInstallmentResult.unapply))
}

/**
  * 消费分期参数
  *
  * @param tranSessionID      gcsSessionId
  * @param reqChannelID       交易请求渠道标识
  * @param accountKeyOne      帐户键值1--原消费中账户ID。请使用TS011007查询后的“accountID”域值
  * @param accountKeyTwo      帐户键值2--原消费中入账账户ID。请使用TS011007查询后的“accountedID”域值
  * @param currencyCode       币种--原消费币种
  * @param billDateNo         帐期号--原消费帐期号
  * @param transactionNo      交易序号
  * @param transactionAmount  交易金额
  * @param cardNo             卡号
  * @param accountNoID        账号id。请使用TS011007查询后的“accountNoID”域值
  * @param installmentPeriods 分期付款期数。“3”、“6”、“9”、“12”、“18”、“24”、“36”
  * @param isfeeFlag          是否分期收取手续费
  * @param channelId          渠道标识
  *
  */
case class GCSConsumptionInstallmentParams(tranSessionID: String,
                                           reqChannelID: String,
                                           accountKeyOne: String,
                                           accountKeyTwo: String,
                                           currencyCode: String,
                                           billDateNo: String,
                                           transactionNo: String,
                                           transactionAmount: String,
                                           cardNo: String,
                                           accountNoID: String,
                                           installmentPeriods: String,
                                           isfeeFlag: String,
                                           channelId: String)

object GCSConsumptionInstallmentParams {
  implicit val gcsConsumptionInstallmentResultReads: Reads[GCSConsumptionInstallmentParams] = (
    (__ \ "tranSessionID").read[String] ~ (__ \ "reqChannelID").read[String] ~ (__ \ "accountKeyOne").read[String] ~ (__ \ "accountKeyTwo").read[String]
      ~ (__ \ "currencyCode").read[String] ~ (__ \ "billDateNo").read[String] ~ (__ \ "transactionNo").read[String] ~ (__ \ "transactionAmount").read[String]
      ~ (__ \ "cardNo").read[String] ~ (__ \ "accountNoID").read[String] ~ (__ \ "installmentPeriods").read[String] ~ (__ \ "isfeeFlag").read[String] ~ (__ \ "channelId").read[String]
    ) (GCSConsumptionInstallmentParams.apply _)

  implicit val gcsConsumptionInstallmentResultWrites: Writes[GCSConsumptionInstallmentParams] = (
    (__ \ "tranSessionID").write[String] ~ (__ \ "reqChannelID").write[String] ~ (__ \ "accountKeyOne").write[String] ~ (__ \ "accountKeyTwo").write[String]
      ~ (__ \ "currencyCode").write[String] ~ (__ \ "billDateNo").write[String] ~ (__ \ "transactionNo").write[String] ~ (__ \ "transactionAmount").write[String]
      ~ (__ \ "cardNo").write[String] ~ (__ \ "accountNoID").write[String] ~ (__ \ "installmentPeriods").write[String] ~ (__ \ "isfeeFlag").write[String] ~ (__ \ "channelId").write[String]
    ) (unlift(GCSConsumptionInstallmentParams.unapply))
}

/**
  * 账单分期上下限结果
  *
  * @param currencyCode 币种
  * @param minAmount    账单分期金额下限真实
  * @param maxAmount    账单分期金额上限
  * @param accountId    账户ID
  * @param accountNo    账户号码
  */
case class GCSAmountLimit(currencyCode: String, minAmount: String, maxAmount: String, respCode: String, accountId: String, accountNo: String)

object GCSAmountLimit {
  implicit val gcsAmountLimitReads: Reads[GCSAmountLimit] = (
    (__ \ "currencyCode").read[String] ~ (__ \ "minAmount").read[String] ~ (__ \ "maxAmount").read[String] ~ (__ \ "respCode").read[String]
      ~ (__ \ "accountId").read[String] ~ (__ \ "accountNo").read[String]
    ) (GCSAmountLimit.apply _)

  implicit val gcsAmountLimitWrites: Writes[GCSAmountLimit] = (
    (__ \ "currencyCode").write[String] ~ (__ \ "minAmount").write[String] ~ (__ \ "maxAmount").write[String] ~ (__ \ "respCode").write[String]
      ~ (__ \ "accountId").write[String] ~ (__ \ "accountNo").write[String]
    ) (unlift(GCSAmountLimit.unapply))
}

/**
  * 账单分期参数
  * 用于账单分期试算和账单分期授权2个接口中
  *
  * @param tranSessionID       tranSessionID
  * @param reqChannelID        渠道编号
  * @param accountId           账户ID
  * @param accountNumber       帐户号码(使用TS011006查询后的“accountNo”域值)
  * @param currencyCode        币种
  * @param billLowerAmount     账单分期下限金额
  * @param billActualAmount    账单分期实际金额
  * @param installmentsNumber  分期期数
  * @param feeInstallmentsFlag 手续费分期标识(1---标识手续费分期收取，0---标识手续费一次性收取)
  * @param channelId           渠道ID（目前渠道标识为1位长的字符，存放在reqChannelId 的第一位，渠道标识的具体含义待业务提供）
  */
case class GCSBillInstallmentParams(tranSessionID: String, reqChannelID: String, accountId: String, accountNumber: String, currencyCode: String, billLowerAmount: String,
                                    billActualAmount: String, installmentsNumber: String, feeInstallmentsFlag: String,
                                    channelId: String)

object GCSBillInstallmentParams {
  implicit val gcsBillInstallmentParamsReads: Reads[GCSBillInstallmentParams] = (
    (__ \ "tranSessionID").read[String] ~ (__ \ "reqChannelID").read[String] ~ (__ \ "accountId").read[String] ~ (__ \ "accountNumber").read[String]
      ~ (__ \ "currencyCode").read[String] ~ (__ \ "billLowerAmount").read[String] ~ (__ \ "billActualAmount").read[String] ~ (__ \ "installmentsNumber").read[String]
      ~ (__ \ "feeInstallmentsFlag").read[String] ~ (__ \ "channelId").read[String]
    ) (GCSBillInstallmentParams.apply _)

  implicit val gcsBillInstallmentParamsWrites: Writes[GCSBillInstallmentParams] = (
    (__ \ "tranSessionID").write[String] ~ (__ \ "reqChannelID").write[String] ~ (__ \ "accountId").write[String] ~ (__ \ "accountNumber").write[String]
      ~ (__ \ "currencyCode").write[String] ~ (__ \ "billLowerAmount").write[String] ~ (__ \ "billActualAmount").write[String] ~ (__ \ "installmentsNumber").write[String]
      ~ (__ \ "feeInstallmentsFlag").write[String] ~ (__ \ "channelId").write[String]
    ) (unlift(GCSBillInstallmentParams.unapply))
}

/**
  * 账单分期费用试算结果
  *
  * @param currentBillMinimum          本期账单最小还款额
  * @param installmentsfee             分期手续费
  * @param installmentsAlsoAmountFirst 分期后每期应还金额-首期
  * @param installmentsAlsoAmountEach  分期后每期应还金额-后每期
  * @param currentBillSurplusAmount    本期账单剩余还款金额
  * @param billFeeMeans                分期手续费收取方式
  * @param installmentsNumber          分期期数
  */
case class GCSBillInstallmentResult(currentBillMinimum: String, installmentsfee: String, installmentsAlsoAmountFirst: String, installmentsAlsoAmountEach: String,
                                    currentBillSurplusAmount: String, billFeeMeans: String, installmentsNumber: String)

object GCSBillInstallmentResult {
  implicit val gcsBillInstallmentResultReads: Reads[GCSBillInstallmentResult] = (
    (__ \ "currentBillMinimum").read[String] ~ (__ \ "installmentsfee").read[String] ~ (__ \ "installmentsAlsoAmountFirst").read[String] ~ (__ \ "installmentsAlsoAmountEach").read[String]
      ~ (__ \ "currentBillSurplusAmount").read[String] ~ (__ \ "billFeeMeans").read[String] ~ (__ \ "installmentsNumber").read[String]
    ) (GCSBillInstallmentResult.apply _)

  implicit val gcsBillInstallmentResultWrites: Writes[GCSBillInstallmentResult] = (
    (__ \ "currentBillMinimum").write[String] ~ (__ \ "installmentsfee").write[String] ~ (__ \ "installmentsAlsoAmountFirst").write[String] ~ (__ \ "installmentsAlsoAmountEach").write[String]
      ~ (__ \ "currentBillSurplusAmount").write[String] ~ (__ \ "billFeeMeans").write[String] ~ (__ \ "installmentsNumber").write[String]
    ) (unlift(GCSBillInstallmentResult.unapply))
}

/**
  * 临时提额授权参数
  *
  * @param tranSessionID     GCSSessionId
  * @param reqChannelID      GCS渠道号
  * @param eosCustomerName   客户姓名
  * @param eosCustomerIdType 客户证件类型
  * @param certNum           客户证件号码
  * @param phoneNumber       手机号
  * @param cardNo            卡号
  * @param eosCurrency       币种
  * @param eosPreAddLimit    客户期望额度
  * @param eosStarLimitDate  增额生效开始日期
  * @param eosEndLimitDate   增额生效结束日期
  * @param cardStyle         产品类型 - 评测接口的返回结果
  * @param issuingBranchId   发卡行号 - 评测接口的返回结果
  * @param pmtCreditLimit    当前卡的永久额度 -评测接口的返回结果
  */
case class GCSTemporaryUpCommitParams(tranSessionID: String, reqChannelID: String, eosCustomerName: String, eosCustomerIdType: String,
                                      certNum: String, phoneNumber: String, cardNo: String, eosCurrency: String, eosPreAddLimit: String, eosStarLimitDate: String,
                                      eosEndLimitDate: String, cardStyle: String, issuingBranchId: String, pmtCreditLimit: String)

object GCSTemporaryUpCommitParams {
  implicit val gcsTemporaryUpCommitParamsReads: Reads[GCSTemporaryUpCommitParams] = (
    (__ \ "tranSessionID").read[String] ~ (__ \ "reqChannelID").read[String] ~ (__ \ "eosCustomerName").read[String] ~ (__ \ "eosCustomerIdType").read[String]
      ~ (__ \ "certNum").read[String] ~ (__ \ "phoneNumber").read[String] ~ (__ \ "cardNo").read[String] ~ (__ \ "eosCurrency").read[String]
      ~ (__ \ "eosPreAddLimit").read[String] ~ (__ \ "eosStarLimitDate").read[String] ~ (__ \ "eosEndLimitDate").read[String]
      ~ (__ \ "cardStyle").read[String] ~ (__ \ "issuingBranchId").read[String] ~ (__ \ "pmtCreditLimit").read[String]
    ) (GCSTemporaryUpCommitParams.apply _)

  implicit val gcsTemporaryUpCommitParamsWrites: Writes[GCSTemporaryUpCommitParams] = (
    (__ \ "tranSessionID").write[String] ~ (__ \ "reqChannelID").write[String] ~ (__ \ "eosCustomerName").write[String] ~ (__ \ "eosCustomerIdType").write[String]
      ~ (__ \ "certNum").write[String] ~ (__ \ "phoneNumber").write[String] ~ (__ \ "cardNo").write[String] ~ (__ \ "eosCurrency").write[String]
      ~ (__ \ "eosPreAddLimit").write[String] ~ (__ \ "eosStarLimitDate").write[String] ~ (__ \ "eosEndLimitDate").write[String]
      ~ (__ \ "cardStyle").write[String] ~ (__ \ "issuingBranchId").write[String] ~ (__ \ "pmtCreditLimit").write[String]
    ) (unlift(GCSTemporaryUpCommitParams.unapply))
}

/**
  *
  * @param tranSessionID gcsSessionId
  * @param reqChannelID  渠道编号
  * @param statementNo   账期号
  * @param accountId     账户ID
  */
case class BillingSummaryParams(tranSessionID: String, reqChannelID: String, statementNo: String, accountId: String)


object BillingSummaryParams {
  implicit val billingSummaryReads: Reads[BillingSummaryParams] = (
    (__ \ "tranSessionID").read[String] ~ (__ \ "reqChannelID").read[String] ~ (__ \ "statementNo").read[String] ~ (__ \ "accountId").read[String]) (BillingSummaryParams.apply _)

  implicit val billingSummaryWrites: Writes[BillingSummaryParams] = (
    (__ \ "tranSessionID").write[String] ~ (__ \ "reqChannelID").write[String] ~ (__ \ "statementNo").write[String] ~ (__ \ "accountId").write[String]) (unlift(BillingSummaryParams.unapply))
}

/**
  *
  * @param tranSessionID gcsSessionId
  * @param reqChannelID  渠道编号
  * @param cardNo        卡号
  * @param currencyCode  交易币种
  * @param queryType     查询类型
  * @param startNum      起始条数
  * @param totalNum      显示条数
  * @param startDate     交易开始日期
  * @param endDate       交易结束日期
  */
case class BillingDetailsSettlementParams(tranSessionID: String, reqChannelID: String, cardNo: String, currencyCode: String, queryType: String, startNum: String,
                                          totalNum: String, startDate: String, endDate: String)

object BillingDetailsSettlementParams {
  implicit val billingDetailsSettlementParamsReads: Reads[BillingDetailsSettlementParams] = (
    (__ \ "tranSessionID").read[String] ~ (__ \ "reqChannelID").read[String] ~ (__ \ "cardNo").read[String] ~ (__ \ "currencyCode").read[String]
      ~ (__ \ "queryType").read[String] ~ (__ \ "startNum").read[String] ~ (__ \ "totalNum").read[String] ~ (__ \ "startDate").read[String]
      ~ (__ \ "endDate").read[String]
    ) (BillingDetailsSettlementParams.apply _)

  implicit val billingDetailsSettlementParamsWrites: Writes[BillingDetailsSettlementParams] = (
    (__ \ "tranSessionID").write[String] ~ (__ \ "reqChannelID").write[String] ~ (__ \ "cardNo").write[String] ~ (__ \ "currencyCode").write[String]
      ~ (__ \ "queryType").write[String] ~ (__ \ "startNum").write[String] ~ (__ \ "totalNum").write[String] ~ (__ \ "startDate").write[String]
      ~ (__ \ "endDate").write[String]
    ) (unlift(BillingDetailsSettlementParams.unapply))
}

/**
  *
  * @param tranSessionID gcsSessionId
  * @param reqChannelID  渠道编号
  * @param cardNo        卡号
  * @param queryType     查询类型
  * @param startNum      起始条数
  * @param totalNum      显示条数
  */
case class BillingDetailsUnSettlementParams(tranSessionID: String, reqChannelID: String, cardNo: String, queryType: String, startNum: String,
                                            totalNum: String)

object BillingDetailsUnSettlementParams {
  implicit val billingDetailsSettlementParamsReads: Reads[BillingDetailsUnSettlementParams] = (
    (__ \ "tranSessionID").read[String] ~ (__ \ "reqChannelID").read[String] ~ (__ \ "cardNo").read[String]
      ~ (__ \ "queryType").read[String] ~ (__ \ "startNum").read[String] ~ (__ \ "totalNum").read[String]
    ) (BillingDetailsUnSettlementParams.apply _)

  implicit val billingDetailsSettlementParamsWrites: Writes[BillingDetailsUnSettlementParams] = (
    (__ \ "tranSessionID").write[String] ~ (__ \ "reqChannelID").write[String] ~ (__ \ "cardNo").write[String]
      ~ (__ \ "queryType").write[String] ~ (__ \ "startNum").write[String] ~ (__ \ "totalNum").write[String]
    ) (unlift(BillingDetailsUnSettlementParams.unapply))
}

/**
  *
  * @param currencyCode 币种
  */
case class CurrencyCodeResult(currencyCode: String)

object CurrencyCodeResult {
  implicit val currencyCodeResultWrites: Writes[CurrencyCodeResult] = Writes(currencyCodeResult => Json.toJson(JsObject(Map("currencyCode" -> JsString(currencyCodeResult.currencyCode)).toSeq)))
}

/**
  *
  * @param currencyCodes 币种列表
  * @param productType   卡片产品类型
  */
case class CardCurrencyCodeAndStyleResult(currencyCodes: List[CurrencyCodeResult], productType: String)

object CardCurrencyCodeAndStyleResult {
  //  implicit val cardCurrencyCodeAndStyleResultReads: Reads[CardCurrencyCodeAndStyleResult] = (
  //    (__ \ "currencyCodes").read[List[CurrencyCodeResult]] ~ (__ \ "productType").read[String]
  //    ) (CardCurrencyCodeAndStyleResult.apply _)

  //  implicit val currencyCodeResultListWrites: Writes[List[CurrencyCodeResult]] = Writes(currencyCodeResults => Json.toJson(JsArray(currencyCodeResults.map(k=>Json.toJson(k)))))

  implicit val cardCurrencyCodeAndStyleResultWrites: Writes[CardCurrencyCodeAndStyleResult] = (
    (__ \ "currencyCodes").write[List[CurrencyCodeResult]] ~ (__ \ "productType").write[String]
    ) (unlift(CardCurrencyCodeAndStyleResult.unapply))
}

///**
//  *
//  * @param billSendType     账单寄送方式
//  * @param billSendTypeDesc 账单寄送方式描述
//  */
//case class BillSendTypeResult(billSendType: String, billSendTypeDesc: String)
//
//object BillSendTypeResult {
//  implicit val billSendTypeResultReads: Reads[BillSendTypeResult] = (
//    (__ \ "billSendType").read[String] ~ (__ \ "billSendTypeDesc").read[String]
//    ) (BillSendTypeResult.apply _)
//
//  implicit val billSendTypeResultWrites: Writes[BillSendTypeResult] = (
//    (__ \ "billSendType").write[String] ~ (__ \ "billSendTypeDesc").write[String]
//    ) (unlift(BillSendTypeResult.unapply))
//}

///**
//  * 公用的boolean结果
//  *
//  * @param isSuccess 是否成功
//  */
//case class BooleanResult(isSuccess: Boolean)
//
//object BooleanResult {
//  implicit val booleanResultWrites: Writes[BooleanResult] = Writes(booleanResult => Json.toJson(JsObject(Map("isSuccess" -> JsBoolean(booleanResult.isSuccess)).toSeq)))
//}

/**
  *
  * @param tranSessionID gcsSessionId
  * @param reqChannelID  渠道编号
  * @param cardNo        卡号
  * @param idType        证件类型
  * @param idNum         证件号
  * @param familyName    姓-海外的只有性，国内是姓名
  * @param lossReason    挂失原因
  */
case class CreditCardReportLostParams(tranSessionID: String, reqChannelID: String, cardNo: String, idType: String, idNum: String, familyName: String, lossReason: String)

object CreditCardReportLostParams {
  implicit val creditCardReportLostParamsReads: Reads[CreditCardReportLostParams] = (
    (__ \ "tranSessionID").read[String] ~ (__ \ "reqChannelID").read[String] ~ (__ \ "cardNo").read[String] ~ (__ \ "idType").read[String]
      ~ (__ \ "idNum").read[String] ~ (__ \ "familyName").read[String] ~ (__ \ "lossReason").read[String]
    ) (CreditCardReportLostParams.apply _)

  implicit val creditCardReportLostParamsWrites: Writes[CreditCardReportLostParams] = (
    (__ \ "tranSessionID").write[String] ~ (__ \ "reqChannelID").write[String] ~ (__ \ "cardNo").write[String] ~ (__ \ "idType").write[String]
      ~ (__ \ "idNum").write[String] ~ (__ \ "familyName").write[String] ~ (__ \ "lossReason").write[String]
    ) (unlift(CreditCardReportLostParams.unapply))
}

/**
  *
  * @param tranSessionID gcsSessionId
  * @param reqChannelID  渠道编号
  * @param cardNo        卡号
  * @param entyMethod    卡号录入方式 01-手工  07-接触 98-非接
  * @param idNum         证件号
  * @param idType        证件类型
  * @param familyName    姓-海外的只有性，国内是姓名
  * @param lostReason    挂失原因
  */
case class TempCreditCardReportLostParams(tranSessionID: String, reqChannelID: String, cardNo: String, entyMethod: String, idNum: String, idType: String, familyName: String, lostReason: String)

object TempCreditCardReportLostParams {
  implicit val tempCreditCardReportLostParamsReads: Reads[TempCreditCardReportLostParams] = (
    (__ \ "tranSessionID").read[String] ~ (__ \ "reqChannelID").read[String] ~ (__ \ "cardNo").read[String] ~ (__ \ "entyMethod").read[String]
      ~ (__ \ "idNum").read[String] ~ (__ \ "idType").read[String] ~ (__ \ "familyName").read[String] ~ (__ \ "lostReason").read[String]
    ) (TempCreditCardReportLostParams.apply _)

  implicit val tempCreditCardReportLostParamsWrites: Writes[TempCreditCardReportLostParams] = (
    (__ \ "tranSessionID").write[String] ~ (__ \ "reqChannelID").write[String] ~ (__ \ "cardNo").write[String] ~ (__ \ "entyMethod").write[String]
      ~ (__ \ "idNum").write[String] ~ (__ \ "idType").write[String] ~ (__ \ "familyName").write[String] ~ (__ \ "lostReason").write[String]
    ) (unlift(TempCreditCardReportLostParams.unapply))
}

/**
  *
  * @param tranSessionID gcsSessionId
  * @param reqChannelID  渠道编号
  * @param cardNo        卡号
  * @param idNum         证件号
  * @param familyName    姓-海外的只有性，国内是姓名
  * @param idType        证件类型
  */
case class RelieveCreditCardTempReportLostParams(tranSessionID: String, reqChannelID: String, cardNo: String, idNum: String, familyName: String, idType: String)

object RelieveCreditCardTempReportLostParams {
  implicit val relieveCreditCardTempReportLostParamsReads: Reads[RelieveCreditCardTempReportLostParams] = (
    (__ \ "tranSessionID").read[String] ~ (__ \ "reqChannelID").read[String] ~ (__ \ "cardNo").read[String] ~ (__ \ "idNum").read[String]
      ~ (__ \ "familyName").read[String] ~ (__ \ "idType").read[String]
    ) (RelieveCreditCardTempReportLostParams.apply _)

  implicit val relieveCreditCardTempReportLostParamsWrites: Writes[RelieveCreditCardTempReportLostParams] = (
    (__ \ "tranSessionID").write[String] ~ (__ \ "reqChannelID").write[String] ~ (__ \ "cardNo").write[String] ~ (__ \ "idNum").write[String]
      ~ (__ \ "familyName").write[String] ~ (__ \ "idType").write[String]
    ) (unlift(RelieveCreditCardTempReportLostParams.unapply))
}

/**
  *
  * @param tranSessionID gcsSessionId
  * @param reqChannelID  渠道编号
  * @param idType        证件类型-4位的字母
  * @param idNo          证件号
  */
case class MobilePhoneParams(tranSessionID: String, reqChannelID: String, idType: String, idNo: String)

object MobilePhoneParams {
  implicit val mobilePhoneParamsReads: Reads[MobilePhoneParams] = (
    (__ \ "tranSessionID").read[String] ~ (__ \ "reqChannelID").read[String] ~ (__ \ "idType").read[String] ~ (__ \ "idNo").read[String]
    ) (MobilePhoneParams.apply _)

  implicit val mobilePhoneParamsWrites: Writes[MobilePhoneParams] = (
    (__ \ "tranSessionID").write[String] ~ (__ \ "reqChannelID").write[String] ~ (__ \ "idType").write[String] ~ (__ \ "idNo").write[String]
    ) (unlift(MobilePhoneParams.unapply))
}

///**
//  * 公用的手机号结果
//  *
//  * @param mobilePhoneNo 手机号
//  */
//case class MobilePhoneResult(mobilePhoneNo: String)
//
//object MobilePhoneResult {
//  implicit val mobilePhoneResultWrites: Writes[MobilePhoneResult] = Writes(mobilePhoneNoResult => Json.toJson(JsObject(Map("mobilePhoneNo" -> JsString(mobilePhoneNoResult.mobilePhoneNo)).toSeq)))
//}

/**
  *
  * @param tranSessionID gcsSessionId
  * @param reqChannelID  渠道编号
  * @param idNum         证件号
  * @param idType        证件类型
  * @param productCode   产品代码
  */
case class WbicCardInfoParams(tranSessionID: String, reqChannelID: String, idNum: String, idType: String, productCode: String = "WBIC")

object WbicCardInfoParams {
  implicit val wbicCardInfoParamsReads: Reads[WbicCardInfoParams] = (
    (__ \ "tranSessionID").read[String] ~ (__ \ "reqChannelID").read[String] ~ (__ \ "idNum").read[String] ~ (__ \ "idType").read[String]
      ~ (__ \ "productCode").read[String]
    ) (WbicCardInfoParams.apply _)

  implicit val wbicCardInfoParamsWrites: Writes[WbicCardInfoParams] = (
    (__ \ "tranSessionID").write[String] ~ (__ \ "reqChannelID").write[String] ~ (__ \ "idNum").write[String] ~ (__ \ "idType").write[String]
      ~ (__ \ "productCode").write[String]
    ) (unlift(WbicCardInfoParams.unapply))
}

/**
  *
  * @param wbicCardNo 海淘卡卡号
  */
case class WbicCardInfoResult(wbicCardNo: Option[String])

object WbicCardInfoResult {
  implicit val wbicCardInfoResultWrites: Writes[WbicCardInfoResult] = Writes(wbicCardInfoResult => Json.toJson(JsObject(Map("wbicCardNo" -> JsString(wbicCardInfoResult.wbicCardNo match {
    case Some(x) => x
    case None => ""
  })).toSeq)))
}

/**
  *
  * @param tranSessionID gcsSessionId
  * @param reqChannelID  渠道编号
  * @param idType        证件类型
  * @param idNum         证件号
  */
case class CustMobileParams(tranSessionID: String, reqChannelID: String, idType: String, idNum: String)

object CustMobileParams {
  implicit val custMobileParamsReads: Reads[CustMobileParams] = (
    (__ \ "tranSessionID").read[String] ~ (__ \ "reqChannelID").read[String] ~ (__ \ "idType").read[String] ~ (__ \ "idNum").read[String]
    ) (CustMobileParams.apply _)

  implicit val custMobileParamsWrites: Writes[CustMobileParams] = (
    (__ \ "tranSessionID").write[String] ~ (__ \ "reqChannelID").write[String] ~ (__ \ "idType").write[String] ~ (__ \ "idNum").write[String]
    ) (unlift(CustMobileParams.unapply))
}

/**
  *
  * @param tranSessionID gcsSessionId
  * @param reqChannelID  渠道编号
  * @param certType      客户证件类型
  * @param certNum       客户证件号码
  * @param phoneNumber   手机号
  * @param cardNo        卡号
  * @param currencyNo    币种
  */
case class CreditLimitTemporaryUpReviewParams(tranSessionID: String, reqChannelID: String, certType: String, certNum: String,
                                              phoneNumber: String, cardNo: String, currencyNo: String)

object CreditLimitTemporaryUpReviewParams {
  implicit val creditLimitTemporaryUpReviewParamsReads: Reads[CreditLimitTemporaryUpReviewParams] = (
    (__ \ "tranSessionID").read[String] ~ (__ \ "reqChannelID").read[String] ~ (__ \ "certType").read[String] ~ (__ \ "certNum").read[String]
      ~ (__ \ "phoneNumber").read[String] ~ (__ \ "cardNo").read[String] ~ (__ \ "currencyNo").read[String]
    ) (CreditLimitTemporaryUpReviewParams.apply _)

  implicit val creditLimitTemporaryUpReviewParamsWrites: Writes[CreditLimitTemporaryUpReviewParams] = (
    (__ \ "tranSessionID").write[String] ~ (__ \ "reqChannelID").write[String] ~ (__ \ "certType").write[String] ~ (__ \ "certNum").write[String]
      ~ (__ \ "phoneNumber").write[String] ~ (__ \ "cardNo").write[String] ~ (__ \ "currencyNo").write[String]
    ) (unlift(CreditLimitTemporaryUpReviewParams.unapply))
}

/**
  *
  * @param tranSessionID gcsSessionId
  * @param reqChannelID  渠道编号
  * @param cardNo        卡号
  */
case class TemporaryUpCommitStatusParams(tranSessionID: String,
                                         reqChannelID: String,
                                         cardNo: String)

object TemporaryUpCommitStatusParams {
  implicit val temporaryUpCommitStatusParamsReads: Reads[TemporaryUpCommitStatusParams] = (
    (__ \ "tranSessionID").read[String] ~ (__ \ "reqChannelID").read[String] ~ (__ \ "cardNo").read[String]
    ) (TemporaryUpCommitStatusParams.apply _)

  implicit val temporaryUpCommitStatusParamsWrites: Writes[TemporaryUpCommitStatusParams] = (
    (__ \ "tranSessionID").write[String] ~ (__ \ "reqChannelID").write[String] ~ (__ \ "cardNo").write[String]
    ) (unlift(TemporaryUpCommitStatusParams.unapply))
}

/**
  *
  * @param tranSessionID gcsSessionId
  * @param reqChannelID  渠道编号
  * @param cardNo        卡号
  * @param currencyCode  币种
  * @param startNumber   起始条数
  * @param selectNumber  显示条数
  */
case class ConsumptionInstallmentsParams(tranSessionID: String, reqChannelID: String, cardNo: String, currencyCode: String, startNumber: String, selectNumber: String)

object ConsumptionInstallmentsParams {
  implicit val consumptionInstallmentsParamsReads: Reads[ConsumptionInstallmentsParams] = (
    (__ \ "tranSessionID").read[String] ~ (__ \ "reqChannelID").read[String] ~ (__ \ "cardNo").read[String]
      ~ (__ \ "currencyCode").read[String] ~ (__ \ "startNumber").read[String] ~ (__ \ "selectNumber").read[String]
    ) (ConsumptionInstallmentsParams.apply _)

  implicit val consumptionInstallmentsParamsWrites: Writes[ConsumptionInstallmentsParams] = (
    (__ \ "tranSessionID").write[String] ~ (__ \ "reqChannelID").write[String] ~ (__ \ "cardNo").write[String]
      ~ (__ \ "currencyCode").write[String] ~ (__ \ "startNumber").write[String] ~ (__ \ "selectNumber").write[String]
    ) (unlift(ConsumptionInstallmentsParams.unapply))
}

/**
  *
  * @param transactionNumber                 交易数量
  * @param hasNext                           是否有下一页
  * @param gcsConsumptionInstallmentsEntitys 交易集合
  */
case class ConsumptionInstallmentsResult(transactionNumber: String, hasNext: Boolean, gcsConsumptionInstallmentsEntitys: List[GCSConsumptionInstallmentsEntity])

object ConsumptionInstallmentsResult {
  implicit val consumptionInstallmentsResultReads: Reads[ConsumptionInstallmentsResult] = (
    (__ \ "transactionNumber").read[String] ~ (__ \ "hasNext").read[Boolean] ~ (__ \ "gcsConsumptionInstallmentsEntitys").read[List[GCSConsumptionInstallmentsEntity]]
    ) (ConsumptionInstallmentsResult.apply _)

  implicit val consumptionInstallmentsResultWrites: Writes[ConsumptionInstallmentsResult] = (
    (__ \ "transactionNumber").write[String] ~ (__ \ "hasNext").write[Boolean] ~ (__ \ "gcsConsumptionInstallmentsEntitys").write[List[GCSConsumptionInstallmentsEntity]]
    ) (unlift(ConsumptionInstallmentsResult.unapply))
}

///**
//  * 公用的GCS返回码实体
//  *
//  * @param returnCode 返回码
//  */
//case class GCSReturnCodeResult(returnCode: String)
//
//object GCSReturnCodeResult {
//  implicit val gcsReturnCodeResultWrites: Writes[GCSReturnCodeResult] = Writes(gcsReturnCodeResult => Json.toJson(JsObject(Map("returnCode" -> JsString(gcsReturnCodeResult.returnCode)).toSeq)))
//}

/**
  *
  * @param tranSessionID gcsSessionId
  * @param reqChannelID  渠道编号
  * @param cardNo        卡号
  * @param currencyCode  币种
  */
case class AmountLimitParams(tranSessionID: String, reqChannelID: String, cardNo: String, currencyCode: String)

object AmountLimitParams {
  implicit val amountLimitParamsReads: Reads[AmountLimitParams] = (
    (__ \ "tranSessionID").read[String] ~ (__ \ "reqChannelID").read[String] ~ (__ \ "cardNo").read[String] ~ (__ \ "currencyCode").read[String]
    ) (AmountLimitParams.apply _)

  implicit val amountLimitParamsWrites: Writes[AmountLimitParams] = (
    (__ \ "tranSessionID").write[String] ~ (__ \ "reqChannelID").write[String] ~ (__ \ "cardNo").write[String] ~ (__ \ "currencyCode").write[String]
    ) (unlift(AmountLimitParams.unapply))
}

/**
  *
  * @param tranSessionID gcsSessionId
  * @param reqChannelID  渠道编号
  * @param idType        证件类型
  * @param idNum         证件号码
  */
case class CardInfosParams(tranSessionID: String, reqChannelID: String, idType: String, idNum: String)

object CardInfosParams {
  implicit val cardInfosParamsReads: Reads[CardInfosParams] = (
    (__ \ "tranSessionID").read[String] ~ (__ \ "reqChannelID").read[String] ~ (__ \ "idType").read[String] ~ (__ \ "idNum").read[String]
    ) (CardInfosParams.apply _)

  implicit val cardInfosParamsWrites: Writes[CardInfosParams] = (
    (__ \ "tranSessionID").write[String] ~ (__ \ "reqChannelID").write[String] ~ (__ \ "idType").write[String] ~ (__ \ "idNum").write[String]
    ) (unlift(CardInfosParams.unapply))
}

/**
  *
  * @param cardNo   卡号
  * @param mianFlag 主副卡标识
  */
case class CardInfosResult(cardNo: String, mianFlag: String)

object CardInfosResult {
  implicit val cardInfosResultReads: Reads[CardInfosResult] = (
    (__ \ "cardNo").read[String] ~ (__ \ "mianFlag").read[String]
    ) (CardInfosResult.apply _)

  implicit val cardInfosResultWrites: Writes[CardInfosResult] = (
    (__ \ "cardNo").write[String] ~ (__ \ "mianFlag").write[String]
    ) (unlift(CardInfosResult.unapply))
}

/**
  *
  * @param tranSessionID gcsSessionId
  * @param reqChannelID  渠道编号
  * @param cardNo        卡号
  * @param billSendType  账单寄送方式
  */
case class UpdateBillSendTypeParams(tranSessionID: String, reqChannelID: String, cardNo: String, billSendType: String)

object UpdateBillSendTypeParams {
  implicit val updateBillSendTypeParamsReads: Reads[UpdateBillSendTypeParams] = (
    (__ \ "tranSessionID").read[String] ~ (__ \ "reqChannelID").read[String] ~ (__ \ "cardNo").read[String] ~ (__ \ "billSendType").read[String]
    ) (UpdateBillSendTypeParams.apply _)

  implicit val updateBillSendTypeParamsWrites: Writes[UpdateBillSendTypeParams] = (
    (__ \ "tranSessionID").write[String] ~ (__ \ "reqChannelID").write[String] ~ (__ \ "cardNo").write[String] ~ (__ \ "billSendType").write[String]
    ) (unlift(UpdateBillSendTypeParams.unapply))
}

/**
  * 历史分期查询参数
  *
  * @param cardNo       卡号
  * @param startNumber  开始数量
  * @param selectNumber 查询数量
  */
case class HistoryInstallmentParams(tranSessionID: String, reqChannelID: String, cardNo: String, startNumber: String, selectNumber: String)

object HistoryInstallmentParams {
  implicit val historyInstallmentParamsReads: Reads[HistoryInstallmentParams] = (
    (__ \ "tranSessionID").read[String] ~ (__ \ "reqChannelID").read[String] ~ (__ \ "cardNo").read[String] ~ (__ \ "startNumber").read[String] ~ (__ \ "selectNumber").read[String]
    ) (HistoryInstallmentParams.apply _)

  implicit val historyInstallmentParamsWrites: Writes[HistoryInstallmentParams] = (
    (__ \ "tranSessionID").write[String] ~ (__ \ "reqChannelID").write[String] ~ (__ \ "cardNo").write[String] ~ (__ \ "startNumber").write[String] ~ (__ \ "selectNumber").write[String]
    ) (unlift(HistoryInstallmentParams.unapply))
}

/**
  * 历史分期查询结果
  *
  * @param transactionNumber 交易数量
  * @param isFollowUp        是否有下一页
  * @param entityList        历史分期查询实体列表
  */
case class HistoryInstallmentResult(transactionNumber: String, isFollowUp: Boolean, entityList: List[HistoryInstallmentEntity])

object HistoryInstallmentResult {
  implicit val historyInstallmentResultReads: Reads[HistoryInstallmentResult] = (
    (__ \ "transactionNumber").read[String] ~ (__ \ "isFollowUp").read[Boolean] ~ (__ \ "entityList").read[List[HistoryInstallmentEntity]]
    ) (HistoryInstallmentResult.apply _)

  implicit val historyInstallmentResultWrites: Writes[HistoryInstallmentResult] = (
    (__ \ "transactionNumber").write[String] ~ (__ \ "isFollowUp").write[Boolean] ~ (__ \ "entityList").write[List[HistoryInstallmentEntity]]
    ) (unlift(HistoryInstallmentResult.unapply))
}

/**
  * 历史分期查询实体
  *
  * @param cardNo                            卡号
  * @param instalmentOriginalTransactionDate 分期付款交易日期(页面：分期日期)
  * @param instalmentRuleDescription         分期付款计划描述(页面：分期描述)
  * @param currencyCode                      货币代码(页面：分期币种)
  * @param instalmentOriginalAmount          分期付款原始金额(页面：分期金额)
  * @param instalmentOriginalNumber          分期付款期数(页面：期数)
  * @param instalmentCompleteDate            分期付款完成日期(页面：完成日期)
  * @param instFeeFlag                       分期手续费收取方式
  * @param instalmentFirstPostingAmount      首次入帐金额
  * @param instalmentNextPostingAmount       下次入帐金额
  * @param instalmentPostedNumber            分期付款已入帐期数(页面：已入账期数)
  * @param instalmentReversalAmount          分期付款冲正金额(页面：已入账金额)
  * @param instalmentOutstandingNumber       分期付款剩余期数(页面：剩余未入账期数)
  * @param instalmentOutstandingAmount       分期付款剩余金额(页面：剩余未入账金额)
  * @param instalmentNextPostingDate         下次入帐日期
  */
case class HistoryInstallmentEntity(cardNo: String, instalmentOriginalTransactionDate: String, instalmentRuleDescription: String, currencyCode: String, instalmentOriginalAmount: String,
                                    instalmentOriginalNumber: String, instalmentCompleteDate: String, instFeeFlag: String, instalmentFirstPostingAmount: String,
                                    instalmentNextPostingAmount: String, instalmentPostedNumber: String, instalmentReversalAmount: String, instalmentOutstandingNumber: String,
                                    instalmentOutstandingAmount: String, instalmentNextPostingDate: String)

object HistoryInstallmentEntity {
  implicit val historyInstallmentEntityReads: Reads[HistoryInstallmentEntity] = (
    (__ \ "cardNo").read[String] ~ (__ \ "instalmentOriginalTransactionDate").read[String] ~ (__ \ "instalmentRuleDescription").read[String]
      ~ (__ \ "currencyCode").read[String] ~ (__ \ "instalmentOriginalAmount").read[String] ~ (__ \ "instalmentOriginalNumber").read[String] ~ (__ \ "instalmentCompleteDate").read[String]
      ~ (__ \ "instFeeFlag").read[String] ~ (__ \ "instalmentFirstPostingAmount").read[String] ~ (__ \ "instalmentNextPostingAmount").read[String] ~ (__ \ "instalmentPostedNumber").read[String]
      ~ (__ \ "instalmentReversalAmount").read[String] ~ (__ \ "instalmentOutstandingNumber").read[String] ~ (__ \ "instalmentOutstandingAmount").read[String] ~ (__ \ "instalmentNextPostingDate").read[String]
    ) (HistoryInstallmentEntity.apply _)

  implicit val historyInstallmentEntityWrites: Writes[HistoryInstallmentEntity] = (
    (__ \ "cardNo").write[String] ~ (__ \ "instalmentOriginalTransactionDate").write[String] ~ (__ \ "instalmentRuleDescription").write[String]
      ~ (__ \ "currencyCode").write[String] ~ (__ \ "instalmentOriginalAmount").write[String] ~ (__ \ "instalmentOriginalNumber").write[String] ~ (__ \ "instalmentCompleteDate").write[String]
      ~ (__ \ "instFeeFlag").write[String] ~ (__ \ "instalmentFirstPostingAmount").write[String] ~ (__ \ "instalmentNextPostingAmount").write[String] ~ (__ \ "instalmentPostedNumber").write[String]
      ~ (__ \ "instalmentReversalAmount").write[String] ~ (__ \ "instalmentOutstandingNumber").write[String] ~ (__ \ "instalmentOutstandingAmount").write[String] ~ (__ \ "instalmentNextPostingDate").write[String]
    ) (unlift(HistoryInstallmentEntity.unapply))
}
