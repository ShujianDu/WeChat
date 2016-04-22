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
    * 查询账单明细
    *
    * @param billingDetailsParams BillingDetailsParams
    * @return
    */
  def getBillingDetails(billingDetailsParams: BillingDetailsParams): List[BillingDetailResult]

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
  def getBillSendType(cardNoParams: CardNoParams): BillSendTypeResult

  /**
    * 账单寄送方式修改
    *
    * @param updateBillSendTypeParams UpdateBillSendTypeParams
    * @return
    */
  def updateBillSendType(updateBillSendTypeParams: UpdateBillSendTypeParams): BooleanResult

  /**
    * 信用卡挂失-永久挂失
    *
    * @param creditCardReportLostParams CreditCardReportLostParams
    * @return 返回是否挂失成功 true/false
    */
  def creditCardReportLost(creditCardReportLostParams: CreditCardReportLostParams): BooleanResult

  /**
    * 信用卡临时挂失
    *
    * @param tempCreditCardReportLostParams TempCreditCardReportLostParams
    * @return 返回是否临时挂失成功 true/false
    */
  def tempCreditCardReportLost(tempCreditCardReportLostParams: TempCreditCardReportLostParams): BooleanResult

  /**
    * 解除临时挂失
    *
    * @param relieveCreditCardTempReportLost RelieveCreditCardTempReportLost
    * @return 返回是否解除临时挂失成功
    */
  def relieveCreditCardTempReportLost(relieveCreditCardTempReportLost: RelieveCreditCardTempReportLostParams): BooleanResult

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
  def getMobilePhone(mobilePhoneParams: MobilePhoneParams): MobilePhoneResult

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
  def wbicCardLost(cardNoParams: CardNoParams): BooleanResult

  /**
    * 为海淘卡用户发送短信
    * GCS-TS011113
    *
    * @param cardNoParams 卡号参数
    * @return 返回是否发送成功 true/false
    */
  def wbicCardSendSMS(cardNoParams: CardNoParams): BooleanResult

  /** *
    * 根据证件号和类型查询客户手机号
    *
    * @param custMobileParams CustMobileParams
    * @return 手机号
    */
  def getCustMobile(custMobileParams: CustMobileParams): MobilePhoneResult

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
  def temporaryUpCommit(gcsTemporaryUpCommitParams: GCSTemporaryUpCommitParams): BooleanResult

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
  def authorizationConsumptionInstallment(params: GCSConsumptionInstallmentParams): GCSReturnCodeResult

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
  def billInstallment(gcsBillInstallmentParams: GCSBillInstallmentParams): GCSReturnCodeResult

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
}

trait GCSBase

/**
  * 公用的卡号参数
  *
  * @param sessionID GCSsessionID
  * @param channelID GCS渠道号
  * @param cardNo    卡号
  */
case class CardNoParams(sessionID: String, channelID: String, cardNo: String)

object CardNoParams {
  implicit val cardNoParamsReads: Reads[CardNoParams] = (
    (__ \ "sessionID").read[String] ~ (__ \ "channelID").read[String] ~ (__ \ "cardNo").read[String]
    ) (CardNoParams.apply _)

  implicit val cardNoParamsWrites: Writes[CardNoParams] = (
    (__ \ "sessionID").write[String] ~ (__ \ "channelID").write[String] ~ (__ \ "cardNo").write[String]
    ) (unlift(CardNoParams.unapply))
}

/**
  * 公用的卡号结果
  *
  * @param cardNo 卡号
  */
case class CardNoResult(cardNo: String)

/**
  * 卡余额
  *
  * @param cardNo                     卡号
  * @param currencyCode               币种
  * @param wholeCreditLimit           总授信额度
  * @param periodAvailableCreditLimit 总可用额
  * @param preCashAdvanceCreditLimit  取现可用额度
  */
case class BalanceResult(cardNo: String, currencyCode: String, wholeCreditLimit: String, periodAvailableCreditLimit: String, preCashAdvanceCreditLimit: String) extends GCSBase

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
  * @param familyName 客户姓
  * @param firstName  客户名
  * @param gender     性别
  * @param mobileNo   手机号码
  * @param postalCode 邮政编码
  * @param workUnitName 单位名称
  * @param workUnitPhone 单位电话
  * @param mailBox 电子邮箱
  * @param homeAddressPhone 住宅电话
  * @param billAddressLine 账单地址由账单地址1,账单地址2,账单地址3拼接完成
  */
case class CardHolderInfoResult(familyName: String, firstName: String, gender: String, mobileNo: String,postalCode:String,workUnitName:String,
                                workUnitPhone:String,mailBox:String,homeAddressPhone:String,billAddressLine:String)

object CardHolderInfoResult {
  implicit val cardHolderInfoResultReads: Reads[CardHolderInfoResult] = (
    (__ \ "familyName").read[String] ~ (__ \ "firstName").read[String] ~ (__ \ "gender").read[String] ~ (__ \ "mobileNo").read[String]
      ~ (__ \ "postalCode").read[String]~ (__ \ "workUnitName").read[String]~ (__ \ "workUnitPhone").read[String]~ (__ \ "mailBox").read[String]~ (__ \ "homeAddressPhone").read[String]
      ~ (__ \ "billAddressLine").read[String]
    ) (CardHolderInfoResult.apply _)

  implicit val cardHolderInfoResultWrites: Writes[CardHolderInfoResult] = (
    (__ \ "familyName").write[String] ~ (__ \ "firstName").write[String] ~ (__ \ "gender").write[String] ~ (__ \ "mobileNo").write[String]~ (__ \ "postalCode").write[String]
      ~ (__ \ "workUnitName").write[String]~ (__ \ "workUnitPhone").write[String]~ (__ \ "mailBox").write[String]~ (__ \ "homeAddressPhone").write[String]~ (__ \ "billAddressLine").write[String]
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
  */
case class GCSConsumptionInstallmentsEntity(cardNo: String, transactionDate: String, transactionAmount: String, debitCreditCode: String, transactionDescription: String,
                                            accountID: String, accountedID: String, accountNoID: String, originalCurrencyCode: String, originalTransactionAmount: String)

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

/**
  * 消费分期参数
  *
  * @param sessionID          gcsSessionId
  * @param reqChannelID       交易请求渠道标识
  * @param accountKeyOne      帐户键值1--原消费中账户ID。请使用TS011007查询后的“accountID”域值
  * @param accountKeyTwo      帐户键值2--原消费中入账账户ID。请使用TS011007查询后的“accountedID”域值
  * @param currencyCode       币种--原消费币种
  * @param billDateNo         帐期号--原消费帐期号
  * @param transactionAmount  交易金额
  * @param cardNo             卡号
  * @param accountNoID        账号id。请使用TS011007查询后的“accountNoID”域值
  * @param installmentPeriods 分期付款期数。“3”、“6”、“9”、“12”、“18”、“24”、“36”
  * @param isfeeFlag          是否分期收取手续费
  *
  */
case class GCSConsumptionInstallmentParams(sessionID: String,
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
                                           channelID: String)

/**
  * 账单分期上下限结果
  *
  * @param currencyCode 币种
  * @param minAmount    账单分期金额下限真实
  * @param maxAmount    账单分期金额上限
  * @param respCode     返回码
  * @param accountId    账户ID
  * @param accountNo    账户号码
  */
case class GCSAmountLimit(currencyCode: String, minAmount: String, maxAmount: String, respCode: String, accountId: String, accountNo: String)

/**
  * 账单分期参数
  * 用于账单分期试算和账单分期授权2个接口中
  *
  * @param tranSessionID       gcsSessionId
  * @param reqChannelID        渠道编号
  * @param accountId           账户ID
  * @param accountNumber       帐户号码(使用TS011006查询后的“accountNo”域值)
  * @param currencyCode        币种
  * @param billLowerAmount     账单分期下限金额
  * @param billActualAmount    账单分期实际金额
  * @param installmentsNumber  分期期数
  * @param feeInstallmentsFlag 手续费分期标识(1---标识手续费分期收取，0---标识手续费一次性收取)
  * @param channelID           渠道ID（目前渠道标识为1位长的字符，存放在channelId 的第一位，渠道标识的具体含义待业务提供）
  */
case class GCSBillInstallmentParams(tranSessionID: String, reqChannelID: String, accountId: String, accountNumber: String, currencyCode: String, billLowerAmount: String,
                                    billActualAmount: String, installmentsNumber: String, feeInstallmentsFlag: String,
                                    channelID: String)

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

/**
  * 临时提额授权参数
  *
  * @param sessionId         GCSSessionId
  * @param channelId         GCS渠道号
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
case class GCSTemporaryUpCommitParams(sessionId: String, channelId: String, eosCustomerName: String, eosCustomerIdType: String,
                                      certNum: String, phoneNumber: String, cardNo: String, eosCurrency: String, eosPreAddLimit: String, eosStarLimitDate: String,
                                      eosEndLimitDate: String, cardStyle: String, issuingBranchId: String, pmtCreditLimit: String)

/**
  *
  * @param sessionId   gcsSessionId
  * @param channelId   渠道编号
  * @param statementNo 账期号
  * @param accountId   账户ID
  */
case class BillingSummaryParams(sessionId: String, channelId: String, statementNo: String, accountId: String)


object BillingSummaryParams {
  implicit val billingSummaryReads: Reads[BillingSummaryParams] = (
    (__ \ "sessionID").read[String] ~ (__ \ "channelID").read[String] ~ (__ \ "statementNo").read[String] ~ (__ \ "accountId").read[String]) (BillingSummaryParams.apply _)

  implicit val billingSummaryWrites: Writes[BillingSummaryParams] = (
    (__ \ "sessionID").write[String] ~ (__ \ "channelID").write[String] ~ (__ \ "statementNo").write[String] ~ (__ \ "accountId").write[String]) (unlift(BillingSummaryParams.unapply))
}

/**
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
  */
case class BillingDetailsParams(sessionId: String, channelId: String, cardNo: String, currencyCode: String, queryType: String, startNum: String,
                                totalNum: String, startDate: String, endDate: String)

/**
  *
  * @param currencyCode 币种
  */
case class CurrencyCodeResult(currencyCode: String)

/**
  *
  * @param currencyCodes 币种列表
  * @param productType   卡片产品类型
  */
case class CardCurrencyCodeAndStyleResult(currencyCodes: List[CurrencyCodeResult], productType: String)

/**
  *
  * @param billSendType 账单寄送方式
  */
case class BillSendTypeResult(billSendType: String)

/**
  * 公用的boolean结果
  *
  * @param isSuccess 是否成功
  */
case class BooleanResult(isSuccess: Boolean)

/**
  *
  * @param sessionId  gcsSessionId
  * @param channelId  渠道编号
  * @param cardNo     卡号
  * @param idType     证件类型
  * @param idNum      证件号
  * @param familyName 姓-海外的只有性，国内是姓名
  * @param lossReason 挂失原因
  */
case class CreditCardReportLostParams(sessionId: String, channelId: String, cardNo: String, idType: String, idNum: String, familyName: String, lossReason: String)

/**
  *
  * @param sessionId  gcsSessionId
  * @param channelId  渠道编号
  * @param cardNo     卡号
  * @param entyMethod 卡号录入方式 01-手工  07-接触 98-非接
  * @param idNum      证件号
  * @param idType     证件类型
  * @param familyName 姓-海外的只有性，国内是姓名
  * @param lostReason 挂失原因
  */
case class TempCreditCardReportLostParams(sessionId: String, channelId: String, cardNo: String, entyMethod: String, idNum: String, idType: String, familyName: String, lostReason: String)

/**
  *
  * @param sessionId  gcsSessionId
  * @param channelId  渠道编号
  * @param cardNo     卡号
  * @param idNum      证件号
  * @param familyName 姓-海外的只有性，国内是姓名
  * @param idType     证件类型
  */
case class RelieveCreditCardTempReportLostParams(sessionId: String, channelId: String, cardNo: String, idNum: String, familyName: String, idType: String)

/**
  *
  * @param sessionId gcsSessionId
  * @param channelId 渠道编号
  * @param idType    证件类型
  * @param idNo      证件号
  */
case class MobilePhoneParams(sessionId: String, channelId: String, idType: String, idNo: String)

/**
  * 公用的手机号结果
  *
  * @param mobilePhoneNo 手机号
  */
case class MobilePhoneResult(mobilePhoneNo: String)

/**
  *
  * @param sessionId   gcsSessionId
  * @param channelId   渠道编号
  * @param idNum       证件号
  * @param idType      证件类型
  * @param productCode 产品代码
  */
case class WbicCardInfoParams(sessionId: String, channelId: String, idNum: String, idType: String, productCode: String = "WBIC")

/**
  *
  * @param wbicCardNo 海淘卡卡号
  */
case class WbicCardInfoResult(wbicCardNo: Option[String])

/**
  *
  * @param sessionId gcsSessionId
  * @param channelId 渠道编号
  * @param idType    证件类型
  * @param idNum     证件号
  */
case class CustMobileParams(sessionId: String, channelId: String, idType: String, idNum: String)

/**
  *
  * @param sessionId   gcsSessionId
  * @param channelId   渠道编号
  * @param certType    客户证件类型
  * @param certNum     客户证件号码
  * @param phoneNumber 手机号
  * @param cardNo      卡号
  * @param currencyNo  币种
  */
case class CreditLimitTemporaryUpReviewParams(sessionId: String, channelId: String, certType: String, certNum: String,
                                              phoneNumber: String, cardNo: String, currencyNo: String)

/**
  *
  * @param sessionId gcsSessionId
  * @param channelId 渠道编号
  * @param cardNo    卡号
  */
case class TemporaryUpCommitStatusParams(sessionId: String,
                                         channelId: String,
                                         cardNo: String)

/**
  *
  * @param sessionId    gcsSessionId
  * @param channelId    渠道编号
  * @param cardNo       卡号
  * @param currencyCode 币种
  * @param startNumber  起始条数
  * @param selectNumber 显示条数
  */
case class ConsumptionInstallmentsParams(sessionId: String, channelId: String, cardNo: String, currencyCode: String, startNumber: String, selectNumber: String)

/**
  *
  * @param transactionNumber                 交易数量
  * @param hasNext                           是否有下一页
  * @param gcsConsumptionInstallmentsEntitys 交易集合
  */
case class ConsumptionInstallmentsResult(transactionNumber: String, hasNext: Boolean, gcsConsumptionInstallmentsEntitys: List[GCSConsumptionInstallmentsEntity])

/**
  * 公用的GCS返回码实体
  *
  * @param returnCode 返回码
  */
case class GCSReturnCodeResult(returnCode: String)

/**
  *
  * @param sessionId    gcsSessionId
  * @param channelId    渠道编号
  * @param cardNo       卡号
  * @param currencyCode 币种
  */
case class AmountLimitParams(sessionId: String, channelId: String, cardNo: String, currencyCode: String)

/**
  *
  * @param sessionId gcsSessionId
  * @param channelId 渠道编号
  * @param idType    证件类型
  * @param idNum     证件号码
  */
case class CardInfosParams(sessionId: String, channelId: String, idType: String, idNum: String)

/**
  *
  * @param cardNo   卡号
  * @param mianFlag 主副卡标识
  */
case class CardInfosResult(cardNo: String, mianFlag: String)

/**
  *
  * @param sessionId    gcsSessionId
  * @param channelId    渠道编号
  * @param cardNo       卡号
  * @param billSendType 账单寄送方式
  */
case class UpdateBillSendTypeParams(sessionId: String, channelId: String, cardNo: String, billSendType: String)

/**
  * 历史分期查询参数
  *
  * @param cardNo       卡号
  * @param startNumber  开始数量
  * @param selectNumber 查询数量
  */
case class HistoryInstallmentParams(sessionId: String, channelId: String, cardNo: String, startNumber: String, selectNumber: String)

/**
  * 历史分期查询结果
  *
  * @param transactionNumber 交易数量
  * @param isFollowUp        是否有下一页
  * @param entityList        历史分期查询实体列表
  */
case class HistoryInstallmentResult(transactionNumber: String, isFollowUp: Boolean, entityList: List[HistoryInstallmentEntity])

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
