package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{CreditLimitTemporaryUpReviewParams, GCSServiceImpl}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json


class LimitTemporaryUpRoute extends Route {
  override def execute(json: String): String = {
    val params = Json.parse(json).as[CreditLimitTemporaryUpReviewParams]
    val rs = GCSServiceImpl.creditLimitTemporaryUpReview(params)
    Json.toJson(rs).toString()
  }
}
