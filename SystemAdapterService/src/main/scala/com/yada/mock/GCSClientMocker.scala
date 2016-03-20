package com.yada.mock

import java.net.URL

import com.typesafe.scalalogging.slf4j.Logger
import com.yada.sdk.gcs.GCSClient
import org.slf4j.LoggerFactory
import GCSMockConfig._

class GCSClientMocker extends GCSClient(new URL("")) {
  private val log = Logger(LoggerFactory.getLogger(this.getClass))

  override def send(request: String): String = {
    log.info(s"send to GCS...\r\n$request")

    val regex_transactionID(transactionID) = request

    val resp = transactionID match {
      case `getBalanceTranID` =>
        val regex_cardNo(cardNo) = request
        val regex_Currency(currencyCode) = request
        balanceResult(cardNo + currencyCode)

      case `getBillingPeriodsTranID` =>
        val regex_cardNo(cardNo) = request
        billingPeriodsResult(cardNo)

      case `getBillingSummaryTranID` =>
        val regex_cardNo(statementNo, accountId) = request
        billingSummaryResult(billingSummaryResult(statementNo + accountId))

      case `getCurrencyCodesTranID` =>
        val regex_cardNo(cardNo) = request
        currencyCodesResult(cardNo)
      case _ => throw new NoSuchMethodException("不支持的交易类型")
    }

    log.info(s"rece from GCS...\r\n$resp")
    resp
  }
}

object GCSClientMocker {

  def apply(): GCSClient = {
    GCSClient.GLOBAL_GCS_CLIENT = new GCSClientMocker
    GCSClient.apply()
  }
}
