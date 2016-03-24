package com.yada.system.adapter.gcs

import com.yada.sdk.gcs.protocol.ErrorGCSReturnCodeException
import com.yada.sdk.gcs.protocol.impl._

import scala.collection.mutable.ListBuffer

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
  override def getVirtualCards(sessionId: String, channelId: String, cardNo: String): List[String] = {
    //一次最大查询99条
    val totalNum = "99"
    val rs = ListBuffer.empty[String]
    def query(startNo: String): List[String] = {
      val ts011031 = new TS011031(sessionId, channelId, cardNo, startNo, totalNum)
      val temp = ts011031.send
      rs ++ temp.pageListValues(Array("p0110311List"))
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
  override def getBillingDetails(sessionId: String, channelId: String, cardNo: String, currencyCode: String, queryType: String,
                                 startNum: String, totalNum: String, startDate: String, endDate: String): List[GCSBillingDetail] = {
    val ts010310 = new TS010310(sessionId, channelId, cardNo, currencyCode, queryType, startNum, totalNum, startDate, endDate)
    ts010310.send.pageListValues(v =>
      GCSBillingDetail(cardNo, currencyCode, v("transactionDate"), v("transactionAmount"), v("transactionDescription"), v("debitCreditCode"))
    )
  }

  /**
    * 币种查询
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param cardNo    卡号
    * @return 该卡的币种
    */
  override def getCurrencyCodes(sessionId: String, channelId: String, cardNo: String): List[String] = {
    val ts010102 = new TS010102(sessionId, channelId, cardNo)
    ts010102.send.pageListValues(Array("currencyCode")).map(m => m("currencyCode"))
  }

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
  override def getConsumptionInstallments(sessionId: String, channelId: String, cardNo: String, currencyCode: String,
                                          startNumber: String, selectNumber: String): (String, Boolean, List[GCSConsumptionInstallmentsEntity]) = {
    val ts011007 = new TS011007(sessionId, channelId, cardNo, currencyCode, startNumber, selectNumber)
    val result = ts011007.send
    val transactionNumber = result.pageValue("transactionNumber")
    val isFollowUp = result.pageValue("isFollowUp")
    val list = result.pageListValues(f => {
      GCSConsumptionInstallmentsEntity(f.getOrElse("cardNo", ""), f.getOrElse("transactionDate", ""), f.getOrElse("transactionAmount", ""),
        f.getOrElse("debitCreditCode", ""), f.getOrElse("transactionDescription", ""), f.getOrElse("accountID", ""), f.getOrElse("accountedID", ""),
        f.getOrElse("accountNoID", ""))
    })
    //isFollowUp ：1-有下一页，0-没有下一页
    (transactionNumber, isFollowUp == "1", list)
  }

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
  override def creditCardReportLost(sessionId: String, channelId: String, cardNo: String, idType: String, idNum: String,
                                    familyName: String, lossReason: String): Boolean = {
    val ts010052 = new TS010052(sessionId, channelId, cardNo, idType, idNum, familyName, lossReason)
    try {
      ts010052.send
      true
    } catch {
      case e: ErrorGCSReturnCodeException => false
    }
  }

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
    * @param lossReason 挂失原因
    * @return 返回是否临时挂失成功 true/false
    */
  override def tempCreditCardReportLost(sessionId: String, channelId: String, cardNo: String, entyMethod: String,
                                        idNum: String, idType: String, familyName: String, lossReason: String): Boolean = {
    val ts010059 = new TS010059(sessionId, channelId, cardNo, entyMethod, idNum, idType, familyName, lossReason)
    try {
      ts010059.send
      true
    } catch {
      case e: ErrorGCSReturnCodeException => false
    }
  }

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
  override def relieveCreditCardTempReportLost(sessionId: String, channelId: String, cardNo: String, idNum: String,
                                               familyName: String, idType: String): Boolean = {
    val ts010060 = new TS010060(sessionId, channelId, cardNo, idNum, familyName, idType)
    try {
      ts010060.send
      true
    } catch {
      case e: ErrorGCSReturnCodeException => false
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
    * @param sessionId                  gcsSessionId
    * @param channelId                  渠道编号
    * @param gcsTemporaryUpCommitParams 临时提额授权参数
    * @return 临时提额授权结果
    */
  override def temporaryUpCommit(sessionId: String, channelId: String, gcsTemporaryUpCommitParams: GCSTemporaryUpCommitParams): Boolean = {
    val ts220001 = new TS220001(sessionId, channelId, gcsTemporaryUpCommitParams)
    try {
      ts220001.send
      true
    } catch {
      case e: ErrorGCSReturnCodeException => false
    }
  }

  /**
    * 海淘卡挂失
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param cardNo    卡号
    * @return 返回是否挂失成功true/false
    */
  override def wbicCardLost(sessionId: String, channelId: String, cardNo: String): Boolean = {
    val ts010063 = new TS010063(sessionId, channelId, cardNo)
    try {
      ts010063.send
      true
    } catch {
      case e: ErrorGCSReturnCodeException => false
    }
  }

  /**
    * 为海淘卡用户发送短信
    * GCS-TS011113
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param cardNo    卡号
    * @return 返回是否发送成功 true/false
    */
  override def wbicCardSendSMS(sessionId: String, channelId: String, cardNo: String): Boolean = {
    val ts011113 = new TS011113(sessionId, channelId, cardNo)
    try {
      ts011113.send
      true
    } catch {
      case e: ErrorGCSReturnCodeException => false
    }
  }

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
  override def getTemporaryUpCommitStatus(sessionId: String, channelId: String, eosIDType: String, idNumber: String,
                                          cardNo: String, eosId: String): List[GCSCreditLimitTemporaryUpStatus] = {
    val ts140031 = new TS140031(sessionId, channelId, eosIDType, idNumber, cardNo, eosId)
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
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param params    参数实体
    * @return 消费分期试算结果
    */
  override def costConsumptionInstallment(sessionId: String, channelId: String, params: GCSConsumptionInstallmentParams): GCSConsumptionInstallmentResult = {
    val ts011172 = new TS011172(sessionId, channelId, params)
    val result = ts011172.send
    GCSConsumptionInstallmentResult(result.pageValue("instalAmount"), result.pageValue("instalmentFee"), result.pageValue("installmentsAlsoAmountFirst"),
      result.pageValue("installmentsAlsoAmountEach"), result.pageValue("billFeeMeans"), result.pageValue("installmentsNumber"))
  }

  /**
    * 账单分期费用试算
    *
    * @param sessionId                gcsSessionId
    * @param channelId                渠道编号
    * @param gcsBillInstallmentParams 账单分期费用试算参数
    * @return 账单分期费用试算结果
    */
  override def queryBillCost(sessionId: String, channelId: String, gcsBillInstallmentParams: GCSBillInstallmentParams): GCSBillInstallmentResult = {
    val ts011170 = new TS011170(sessionId, channelId, gcsBillInstallmentParams)
    val result = ts011170.send

    GCSBillInstallmentResult(result.pageValue("currentBillMinimum"), result.pageValue("installmentsfee"), result.pageValue("installmentsAlsoAmountFirst"),
      result.pageValue("installmentsAlsoAmountEach"), result.pageValue("currentBillSurplusAmount"), result.pageValue("billFeeMeans"), result.pageValue("installmentsNumber"))
  }

  /**
    * 根据卡号查询客户信息 - TS010201
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param cardNo    卡号
    * @return GCSCardHolderInfo
    */
  override def getCardHolderInfo(sessionId: String, channelId: String, cardNo: String): GCSCardHolderInfo = {
    val ts010201 = new TS010201(sessionId, channelId, cardNo)
    val result = ts010201.send

    GCSCardHolderInfo(result.pageValue("familyName"), result.pageValue("firstName"), result.pageValue("gender"), result.pageValue("mobileNo"))
  }

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
  override def authorizationConsumptionInstallment(sessionId: String, channelId: String, params: GCSConsumptionInstallmentParams): String = {
    val ts011173 = new TS011173(sessionId, channelId, params)
    ts011173.send.pageValue("authReturnCode")
  }

  /** *
    * 根据证件号和类型查询客户手机号
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param idType    证件类型
    * @param idNum     证件号
    * @return 手机号
    */
  override def getCustMobile(sessionId: String, channelId: String, idType: String, idNum: String): String = {
    val ts011101 = new TS011101(sessionId, channelId, idType, idNum)
    ts011101.send.pageValue("mobilePhone")
  }

  /**
    * 查询海淘卡
    *   -海淘卡只会有一张正常状态的卡片
    *
    * @param sessionId   gcsSessionId
    * @param channelId   渠道编号
    * @param idNum       证件号
    * @param idType      证件类型
    * @param productCode 产品代码
    * @return 返回海淘卡
    */
  override def getWbicCardInfo(sessionId: String, channelId: String, idNum: String, idType: String, productCode: String ="WBIC"): Option[String] = {
    val ts011111 = new TS011111(sessionId, channelId, idNum, idType, productCode)
    //查找卡状态为“ ”空格的卡集合，然后返回第一个元素
    ts011111.send.pageListValues[(String,String)](m=>
          (m("cardNo"),m("cardStatus"))
        ).filter(v => v._2.trim.length==0).map(m=>m._1).headOption
  }

  /**
    * 账单分期授权
    *
    * @param sessionId                gcsSessionId
    * @param channelId                渠道编号
    * @param gcsBillInstallmentParams 账单分期授权参数
    * @return GCS返回码
    */
  override def billInstallment(sessionId: String, channelId: String, gcsBillInstallmentParams: GCSBillInstallmentParams): String = {
    val ts011171 = new TS011171(sessionId, channelId, gcsBillInstallmentParams)
    ts011171.send.pageValue("authReturnCode")
  }

  /**
    * 根据证件类型和证件号查询所有卡信息
    *
    * @param sessionId gcsSessionId
    * @param channelId 渠道编号
    * @param idType    证件类型
    * @param idNum     证件号码
    * @return (cardNo,主付卡标识)的集合
    */
  override def getCardInfos(sessionId: String, channelId: String, idType: String, idNum: String): List[(String, String)] = {
    val giveUpCardStatus = List("AC","ACCC","CANC")
    val giveUpCardPre = List("3","4","5","6")

    val listBuffer = ListBuffer.empty[(String,String)]

    //循环查询所有的数据
    def query(startNum:String): List[(String, String)] ={
      val ts011005 = new TS011005(sessionId,channelId,None,Some(idType),Some(idNum),startNum,"10")
      val result = ts011005.send
      listBuffer ++ result.pageListValues(m=>{
        (m("cardNo"),m("cardStatus"),m("mainFlag"))
      }).filter(v=> !giveUpCardStatus.contains(v._2) && !giveUpCardPre.contains(v._1.head.toString)).map(m=>(m._1,m._3))

      result.pageValue("isHaveNext") match {
        case "1" =>
          query(startNum +1)
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
