import com.yada.system.adapter.gcs.GCSConsumptionInstallmentParams
import play.api.libs.json.Json

val json =
  """{"accountKeyOne":"001A021306500928",
    |"accountKeyTwo":"001A021306500928",
    |"accountNoID":"1297812597499142",
    |"billDateNo":"40",
    |"cardNo":"377677523143733",
    |"currencyCode":"CNY",
    |"installmentPeriods":"6",
    |"isfeeFlag":"1",
    |"reqChannelID":"WX01",
    |"tranSessionID":"93c4399ad67d925fa40d0693adb0a222",
    |"transactionAmount":"+00000000001300000",
    |"transactionNo":"2"}""".stripMargin

val b = Json.parse(json).as[GCSConsumptionInstallmentParams]