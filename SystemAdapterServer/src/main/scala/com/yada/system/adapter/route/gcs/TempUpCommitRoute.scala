package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{GCSServiceImpl, GCSTemporaryUpCommitParams}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class TempUpCommitRoute extends Route {
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[GCSTemporaryUpCommitParams]
    val rs = GCSServiceImpl.temporaryUpCommit(params)
    Json.toJson(rs).toString()
  }
}
