package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{CreditLimitTemporaryUpReviewParams, GCSService, GCSServiceImpl}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json


class LimitTemporaryUpRoute(gCSService: GCSService = GCSServiceImpl)  extends Route{
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[CreditLimitTemporaryUpReviewParams]
    val rs = gCSService.creditLimitTemporaryUpReview(params)
    Json.toJson(rs).toString()
  }
}
