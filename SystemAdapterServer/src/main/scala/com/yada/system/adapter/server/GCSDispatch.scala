package com.yada.system.adapter.server

import com.yada.system.adapter.gcs.{BillingSummaryParams, CardNoParams, GCSService, GCSServiceImpl}
import io.netty.handler.codec.http.FullHttpRequest
import play.api.libs.json.Json


class GCSDispatch(gCSService: GCSService) {
  def dispatch(json: String, httpRequest: FullHttpRequest): String = {

    val path = httpRequest.getUri.substring(5)
    path match {
      case "balance" =>
        val cardNoParams = Json.parse(json).as[CardNoParams]
        val balances = gCSService.getBalance(cardNoParams)
        Json.toJson(balances).toString()
      case "billingPeriods" =>
        val cardNoParams = Json.parse(json).as[CardNoParams]
        val billingPeriodsList = gCSService.getBillingPeriods(cardNoParams)
        Json.toJson(billingPeriodsList).toString()
      case "billingSummary" =>
        val billingSummary = Json.parse(json).as[BillingSummaryParams]
        val billingSummaryList = gCSService.getBillingSummary(billingSummary)
        Json.toJson(billingSummaryList).toString()
    }
  }
}

object GCSDispatch {
  private val gCSService: GCSService = new GCSServiceImpl

  def apply(): GCSDispatch = new GCSDispatch(gCSService)
}
