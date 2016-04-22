package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{GCSService, GCSServiceImpl, GCSTemporaryUpCommitParams}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class TempUpCommitRoute(gCSService: GCSService = GCSServiceImpl) extends Route {
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[GCSTemporaryUpCommitParams]
    val rs = gCSService.temporaryUpCommit(params)
    Json.toJson(rs).toString()
  }
}
