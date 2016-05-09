package com.yada.system.adapter.gcs

import org.scalatest.{FlatSpec, Matchers}
import play.api.libs.json.Json

class GCSTest extends FlatSpec with Matchers {

  val gCSService = new GCSServiceImpl
  val tranSessionID = "93c4399ad67d925fa40d0693adb0a222"
  val reqChannelID = "WX01"


  "GetConsumptionInstallments" should "success" in{
    val consumptionInstallmentsParams = ConsumptionInstallmentsParams(tranSessionID, reqChannelID, "377677523143733","CNY", "1", "10")
    val rs = gCSService.getConsumptionInstallments(consumptionInstallmentsParams)
    println(rs.hasNext)
    println(rs.gcsConsumptionInstallmentsEntitys.head.accountedID)
    println(rs.gcsConsumptionInstallmentsEntitys.head.accountID)
    println(rs.gcsConsumptionInstallmentsEntitys.head.accountNoID)
  }

  "costConsumptionInstallment" should "success" in{
    val params = GCSConsumptionInstallmentParams(tranSessionID,reqChannelID,
      "001A021306500928",
      "001A021306500928",
      "CNY",
      "40",
      "2",
      "000000000001300000",
      "377677523143733",
      "1297812597499142",
      "6",
      "1",
      "A")
    val rs = gCSService.costConsumptionInstallment(params)
    println(rs)
  }

}
