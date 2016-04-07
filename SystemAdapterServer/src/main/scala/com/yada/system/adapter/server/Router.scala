package com.yada.system.adapter.server

import com.yada.system.adapter.bcsp.BCSPService
import com.yada.system.adapter.directsale.DirectSale
import com.yada.system.adapter.gcs.GCSService
import com.yada.system.adapter.points.PointsService
import com.yada.system.adapter.tgw.TGWService
import io.netty.handler.codec.http.FullHttpRequest

class Router(bCSPService: BCSPService ,directSale:DirectSale,gCSService: GCSService,pointsService: PointsService,tGWService: TGWService) {

  def dispatch(httpRequest: FullHttpRequest): Unit ={
    //TODO 路由
    val path = httpRequest.getUri
    path match {
      case "/bcsp" =>
      case "/gcs" =>
      case "/points" =>
      case "/tgw" =>
      case "/directSale" =>
    }
  }
}
