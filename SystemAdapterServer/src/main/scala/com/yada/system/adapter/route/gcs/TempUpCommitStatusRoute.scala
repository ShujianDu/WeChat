package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{GCSServiceImpl, TemporaryUpCommitStatusParams}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class TempUpCommitStatusRoute extends Route {
  override def execute(json: String): String = {
    val params = Json.parse(json).as[TemporaryUpCommitStatusParams]
    val rs = GCSServiceImpl.getTemporaryUpCommitStatus(params)
    Json.toJson(rs).toString()
  }
}
