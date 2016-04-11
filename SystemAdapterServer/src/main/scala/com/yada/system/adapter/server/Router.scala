package com.yada.system.adapter.server

import java.nio.charset.StandardCharsets

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
    val json = httpRequest.content().toString(StandardCharsets.UTF_8)

    pathPattern match {
      case "bcsp" =>
        BCSPDispatch.dispatch(json)
      case "gcs" =>
        GCSDispatch.apply().dispatch(json,httpRequest)
      case "points" =>
        PointsDispatch.dispatch(json,httpRequest)
      case "tgw" =>
        TGWDispatch.dispatch(json,httpRequest)
      case "directSale" =>
    }
  }
}

object Router{
  def apply(bCSPService: BCSPService, directSale: DirectSale, gCSService: GCSService, pointsService: PointsService, tGWService: TGWService): Router = new Router(bCSPService, directSale, gCSService, pointsService, tGWService)
}
