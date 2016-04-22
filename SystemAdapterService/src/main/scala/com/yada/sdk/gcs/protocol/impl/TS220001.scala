package com.yada.sdk.gcs.protocol.impl

import java.text.SimpleDateFormat
import java.util.{Calendar, Random}

import com.yada.sdk.gcs.GCSClient
import com.yada.sdk.gcs.protocol.{GCSReq, RandomGenerate}
import com.yada.system.adapter.gcs.GCSTemporaryUpCommitParams

/**
  * 个人信用卡额度调整申请交易
  *
  * @param tranSessionID       交易会话标识，用来表示客户访问身份
  * @param reqChannelID        交易请求渠道标识
  * @param eosCustomerName     客户姓名
  * @param eosCustomerIDType   客户证件类型
  * @param eosCustomerIDNumber 证件类型号码
  * @param eosPhoneNumber      手机号码
  * @param eosCardNo           信用卡卡号
  * @param eosCurrency         币种。人民币=》CNY
  * @param eosPreAddLimit      客户期望额度
  * @param eosStarLimitDate    增额生效开始日期。YYYY-MM-DD
  * @param eosEndLimitDate     结束时间以工单为准，如果结束时间小于或等于当前系统时间，系统拒绝。长增可以不送结束日期。YYYY-MM-DD
  * @param cardStyle           产品类型。1个人；2白金；3公务卡
  * @param issuingBranchId     发卡行号
  * @param pmtCreditLimit      当前卡的永久额度
  * @param eosType             工单类型
  * @param eosReason           增额原因。
  *                            01客户主动
  *                            02银行主动，客户余额不足
  *                            03银行主动，客户线下跨境首次交易
  *                            04银行主动，客户当前额度使用率大于80%
  *                            （当工单类型为1、4的时候此项必填）
  * @param eosEmergencyDegree  紧急程度。1：高（白金）2：中（紧急） 3：低（一般）
  * @param eosCustomerType     客户预设额度类型。1-增额类 2-减额类（目前业务范围为增额类）
  * @param eosRequestPath      工单渠道标示。
  *                            01总行NUW；
  *                            02总行CSR；
  *                            03总行CRM；
  *                            04分行落地终端；
  *                            05网银；
  *                            06微信；
  *                            07财富OCRM；
  *                            08 ACCSRLCMS；
  *                            09缤纷生活；
  *
  */
class TS220001(tranSessionID: String,
               reqChannelID: String,
               eosCustomerName: String,
               eosCustomerIDType: String,
               eosCustomerIDNumber: String,
               eosPhoneNumber: String,
               eosCardNo: String,
               eosCurrency: String,
               eosPreAddLimit: String,
               eosStarLimitDate: String,
               eosEndLimitDate: String,
               cardStyle: String,
               issuingBranchId: String,
               pmtCreditLimit: String,
               eosType: String = "02",
               eosReason: String = "01",
               eosEmergencyDegree: String = "2",
               eosCustomerType: String = "1",
               eosRequestPath: String = "06")(implicit gcsClient: GCSClient = GCSClient.GLOBAL) extends GCSReq(gcsClient) {

  private val datetime = Calendar.getInstance.getTime
  setPageProps("eosType", eosType)
  setPageProps("eosReason", eosReason)
  setPageProps("eosEmergencyDegree", eosEmergencyDegree)
  setPageProps("eosCustomerType", eosCustomerType)
  setPageProps("eosCustomerName", eosCustomerName)
  setPageProps("eosCustomerIdType", eosCustomerIDType)
  setPageProps("eosCustomerIdNumber", eosCustomerIDNumber)
  setPageProps("eosPhoneNumber", eosPhoneNumber)
  setPageProps("eosCardNo", eosCardNo)
  setPageProps("eosCurrency", eosCurrency)
  setPageProps("eosPreAddLimit", eosPreAddLimit)
  setPageProps("eosStarLimitDate", eosStarLimitDate)
  setPageProps("eosEndlimitdate", eosEndLimitDate)
  setPageProps("eosRequestPath", eosRequestPath)
  setPageProps("eosImpTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(datetime))
  setPageProps("cardStyle", cardStyle)
  setPageProps("issuingBranchId", issuingBranchId)
  setPageProps("pmtCreditLimit", pmtCreditLimit)
  setPageProps("eosId", f"$eosRequestPath$eosType${new SimpleDateFormat("yyyyMMdd").format(datetime)}${RandomGenerate.get}08d")

  override def transactionID: String = "220001"

  override def requestChannelId: String = reqChannelID

  override def transactionSessionId: String = tranSessionID

  override def pageKey: String = "RQ220001"

  override def transactionCode: String = "220001"
}
