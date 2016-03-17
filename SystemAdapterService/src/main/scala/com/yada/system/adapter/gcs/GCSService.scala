package com.yada.system.adapter.gcs

trait GCSService {

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
  def getBalance(sessionID: String, channelID: String, cardNos: List[String]): List[GCSBalance]

  /**
    * 查账单周期
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param cardNo    卡号
    * @return
    */
  def getBillingPeriods(sessionId: String, channelId: String, cardNo: String): List[GCSBillingPeriods]

  /**
    * 账单摘要查询
    *
    * @param sessionId   gcsSessionId
    * @param channelId   渠道编号
    * @param statementNo 账期号
    * @param accountId   账户ID
    * @return
    */
  def getBillingSummary(sessionId: String, channelId: String, statementNo: String, accountId: String): GCSBillingSummary

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
  def getBillingDetails(sessionId: String, channelId: String, cardNo: String, currencyCode: String, queryType: String, startNum: String,
                        totalNum: String, startDate: String, endDate: String): List[GCSBillingDetail]

  /**
    * 币种查询
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param cardNo    卡号
    * @return 该卡的币种
    */
  def getCurrencyCodes(sessionId: String, channelId: String, cardNo: String): List[String]


  def getCardCurrencyCodeAndStyle(sessionId: String, channelId: String, cardNo: String)

  /**
    * 账单寄送方式查询
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param cardNo    卡号
    * @return
    */
  def getBillSendType(sessionId: String, channelId: String, cardNo: String): GCSBillSendType

  /**
    * 账单寄送方式修改
    *
    * @param sessionId    gcsSessionId
    * @param channelId    渠道编号
    * @param cardNo       卡号
    * @param billSendType 账单寄送方式
    * @return
    */
  def updateBillSendType(sessionId: String, channelId: String, cardNo: String, billSendType: String): Boolean

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
  def creditCardReportLost(sessionId: String, channelId: String, cardNo: String, idType: String, idNum: String, familyName: String, lossReason: String): Boolean

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
  def tempCreditCardReportLost(sessionId: String, channelId: String, cardNo: String, entyMethod: String, idNum: String, idType: String, familyName: String, lostReason: String): Boolean

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
  def relieveCreditCardTempReportLost(sessionId: String, channelId: String, cardNo: String, idNum: String, familyName: String, idType: String): Boolean

  /**
    * 根据卡号查询客户信息 - TS010201
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param cardNo    卡号
    * @return
    */
  def getCardHolderInfo(sessionId: String, channelId: String, cardNo: String): GCSCardHolderInfo

  /**
    * 获取预约办卡用户手机号
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param idType    证件类型
    * @param idNo      证件号
    * @return 返回手机号
    */
  def getMobilePhone(sessionId: String, channelId: String, idType: String, idNo: String): String

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
  //TODO 产品代码不是固定的？
  def getWbicCardInfo(sessionId: String, channelId: String, idNum: String, idType: String, productCode: String): String

  /**
    * 海淘卡挂失
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param cardNo    卡号
    * @return 返回是否挂失成功true/false
    */
  def wbicCardLost(sessionId: String, channelId: String, cardNo: String): Boolean

  /**
    * 为海淘卡用户发送短信
    * GCS-TS011113
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param cardNo    卡号
    * @return 返回是否发送成功 true/false
    */
  def wbicCardSendSMS(sessionId: String, channelId: String, cardNo: String): Boolean

  /** *
    * 根据证件号和类型查询客户手机号
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param idType    证件类型
    * @param idNum     证件号
    * @return 手机号
    */
  def getCustMobile(sessionId: String, channelId: String, idType: String, idNum: String): String

  /** *
    * 根据实体卡取得虚拟卡
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param cardNo 卡号
    * @return 虚拟卡卡号列表
    */
  def getVirtualCards(sessionId: String, channelId: String, cardNo: String): List[String]

  /**
    * 临时提额评测
    *
    * @param sessionId   gcsSessionId
    * @param channelId   渠道编号
    * @param certType    客户证件类型
    * @param certNum     客户证件号码
    * @param phoneNumber 手机号
    * @param cardNo      卡号
    * @return 临时提升评测结果
    */
  def creditLimitTemporaryUpReview(sessionId: String, channelId: String, certType: String, certNum: String,
                                   phoneNumber: String, cardNo: String): GCSCreditLimitTemporaryUpReviewResult

  /**
    * 临时提额提交
    *
    * @param sessionId         gcsSessionId
    * @param channelId         渠道编号
    * @param eosID             工单ID
    * @param eosCustomerName   客户姓名
    * @param eosCustomerIdType 客户证件类型
    * @param certNum           客户证件号码
    * @param phoneNumber       手机号
    * @param cardNo            卡号
    * @param eosPreAddLimit    客户期望额度
    * @param eosStarLimitDate  增额生效开始日期
    * @param eosEndlimitdate   增额生效结束日期
    * @param cardStyle         产品类型
    * @param issuingBranchId   发卡行号
    * @param pmtCreditLimit    当前卡的永久额度
    * @return 临时提升评测结果
    */
  def temporaryUpCommit(sessionId: String, channelId: String, eosID: String, eosCustomerName: String, eosCustomerIdType: String,
                        certNum: String, phoneNumber: String, cardNo: String, eosPreAddLimit: String, eosStarLimitDate: String,
                        eosEndlimitdate: String, cardStyle: String, issuingBranchId: String, pmtCreditLimit: String): Boolean

  /**
    * 信用卡额度临时提额调整状态查询
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param eosIdtype 证件类型
    * @param idNumber  证件号码
    * @param cardNo    卡号
    * @param eosId     工作单ID
    * @return 信用卡额度临时提升调整状态列表
    */
  def getTemporaryUpCommitStatus(sessionId: String, channelId: String, eosIdtype: String, idNumber: String, cardNo: String, eosId: String): List[GCSCreditLimitTemporaryUpStatus]

  //TODO 卡户分期接口存在疑问！ 根据卡号或者证件号查询客户信息及卡信息存在疑问
  //  def getAccountSummaryInfo

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
  def getConsumptionInstallments(sessionId: String, channelId: String, cardNo: String, currencyCode: String, startNumber: String, selectNumber: String): (String, Boolean, List[GCSConsumptionInstallmentsEntity])

  /**
    * 查询消费分期试算结果
    * transactionNo - 3110( POS消费还原)
    * mcc -“5311”
    * installmentPlan - “EP01”
    * merchantID - “0000000”
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param parms     参数实体
    * @return
    */
  def costConsumptionInstallment(sessionId: String, channelId: String, parms: GCSConsumptionInstallmentParms): GCSConsumptionInstallmentCost

  /**
    * 消费分期授权
    * transactionNo - “3110”( POS消费还原)
    * mcc -“5311”
    * installmentPlan - “EP01”
    * merchantID - “0000000”
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param parms     参数实体
    * @return GCS返回码
    */
  def authorizationConsumptionInstallment(sessionId: String, channelId: String, parms: GCSConsumptionInstallmentParms): String

}

/**
  *
  * @param cardNo                    卡号
  * @param currencyCode              币种
  * @param limitCount                总授信额度
  * @param avaliableCount            总可用额
  * @param preCashAdvanceCreditLimit 取现可用额度
  */
case class GCSBalance(cardNo: String, currencyCode: String, limitCount: String, avaliableCount: String, preCashAdvanceCreditLimit: String)

/**
  *
  * @param accountId       账户ID
  * @param currencyCode    币种
  * @param periodStartDate 账期开始日期
  * @param periodEndDate   账期结束日期
  * @param statementNo     账期号
  */
case class GCSBillingPeriods(accountId: String, currencyCode: String, periodStartDate: String, periodEndDate: String, statementNo: String)

/**
  *
  * @param periodStartDate  账期开始日期
  * @param periodEndDate    账期结束日期/账单日期
  * @param paymentDueDate   到期还款日
  * @param closingBalance   本期结欠金额(应还款额：)
  * @param currencyCode     币种
  * @param minPaymentAmount 最小还款额
  */
//TODO 最小还款额 是否没有用到！
case class GCSBillingSummary(periodStartDate: String, periodEndDate: String, paymentDueDate: String, closingBalance: String, currencyCode: String, minPaymentAmount: String)

/**
  *
  * @param cardNo                 卡号
  * @param currencyCode           币种
  * @param transactionDate        交易日期
  * @param transactionAmount      交易金额
  * @param transactionDescription 交易描述
  * @param debitCreditCode        借记,贷记标记  "DEBT"表示:借方 (比如：网上消费这种就是借方) "CRED"表示:贷方 "NMON"表示:非金融交易
  */
//TODO debitCreditCode 改成枚举
case class GCSBillingDetail(cardNo: String, currencyCode: String, transactionDate: String, transactionAmount: String, transactionDescription: String, debitCreditCode: String)

/**
  *
  * @param cardNo       卡号
  * @param billSendType 账单寄送方式
  */
case class GCSBillSendType(cardNo: String, billSendType: String)

/**
  *
  * @param familyName 客户姓
  * @param firstName  客户名
  * @param gender     性别
  * @param mobileNo   手机号码
  */
case class GCSCardHolderInfo(familyName: String, firstName: String, gender: String, mobileNo: String)

/**
  *
  * @param approve         授权是否批准
  * @param amount          建议额度
  * @param cardStyle       产品类型（1，个人；2，白金；3，公务卡）
  * @param issuingBranchId 发卡行号
  * @param creditLimit     当前卡整体信用额度
  * @param pmtCreditLimit  当前卡的永久额度
  */
case class GCSCreditLimitTemporaryUpReviewResult(approve: String, amount: String, cardStyle: String, issuingBranchId: String, creditLimit: String, pmtCreditLimit: String)

/**
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
  * @param cardNo                 卡号
  * @param transactionDate        交易日期
  * @param transactionAmount      交易金额
  * @param debitCreditCode        借方、贷方(交易类型)
  * @param transactionDescription 交易描述
  * @param accountID              账户ID
  * @param accountedID            入账账户ID
  * @param accountNoID            账户ID -供消费分期费用试算上送用
  */
case class GCSConsumptionInstallmentsEntity(cardNo: String, transactionDate: String, transactionAmount: String, debitCreditCode: String, transactionDescription: String,
                                            accountID: String, accountedID: String, accountNoID: String)

/**
  *
  * @param cardNo                      卡号
  * @param installmentAmount           分期金额
  * @param installmentFee              分期手续费
  * @param installmentsAlsoAmountFirst 分期后每期应还金额-首期
  * @param installmentsAlsoAmountEach  分期后每期应还金额-后每期
  * @param billFeeMeans                分期手续费收取方式
  * @param installmentsNumber          分期期数
  * @param currencyCode                币种
  */
case class GCSConsumptionInstallmentCost(cardNo: String, installmentAmount: String, installmentFee: String, installmentsAlsoAmountFirst: String, installmentsAlsoAmountEach: String,
                                         billFeeMeans: String, installmentsNumber: String, currencyCode: String)

/**
  * @param accountKeyOne      帐户键值1--原消费中账户ID
  * @param accountKeyTwo      帐户键值2--原消费中入账账户ID
  * @param currencyCode       币种--原消费币种
  * @param billDateNo         帐期号--原消费帐期号
  * @param transactionAmount  交易金额
  * @param cardNo             卡号
  * @param accountNoId        账号id
  * @param installmentPeriods 分期付款期数
  * @param isfeeFlag          是否分期收取手续费
  */
case class GCSConsumptionInstallmentParms(accountKeyOne: String, accountKeyTwo: String, currencyCode: String,
                                          billDateNo: String, transactionNo: String, transactionAmount: String, cardNo: String, accountNoId: String,
                                          installmentPeriods: String, isfeeFlag: String)