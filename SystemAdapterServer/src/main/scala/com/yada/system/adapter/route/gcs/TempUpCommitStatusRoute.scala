package com.yada.system.adapter.route.gcs

import com.yada.system.adapter.gcs.{GCSService, GCSServiceImpl, TemporaryUpCommitStatusParams}
import com.yada.system.adapter.route.Route
import play.api.libs.json.Json

class TempUpCommitStatusRoute(gCSService: GCSService = GCSServiceImpl)  extends Route{
  override def execute(json: String): String = {
    val params = Json.toJson(json).as[TemporaryUpCommitStatusParams]
    val rs = gCSService.getTemporaryUpCommitStatus(params)
    Json.toJson(rs).toString()
  }
}
