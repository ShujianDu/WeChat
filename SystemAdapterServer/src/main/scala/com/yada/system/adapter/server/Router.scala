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
    val pathPattern = path.substring(1,path.indexOf("/",1))
    pathPattern match {
      case "bcsp" =>
      case "gcs" =>

      case "points" =>
      case "tgw" =>
      case "directSale" =>
    }
  }
}

object Router{
  def apply(bCSPService: BCSPService, directSale: DirectSale, gCSService: GCSService, pointsService: PointsService, tGWService: TGWService): Router = new Router(bCSPService, directSale, gCSService, pointsService, tGWService)
}
