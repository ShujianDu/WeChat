package com.yada.system.adapter.server

import java.nio.charset.StandardCharsets

import io.netty.channel.{ChannelHandlerContext, SimpleChannelInboundHandler}
import io.netty.handler.codec.http.FullHttpRequest

class SystemAdapterHandler extends SimpleChannelInboundHandler[FullHttpRequest] {
  override def channelRead0(channelHandlerContext: ChannelHandlerContext, i: FullHttpRequest): Unit = {
    println(i.getUri)
    val path = i.getUri
    val pathPattern = path.substring(1,path.indexOf("/",1))
    println(pathPattern)
    println(i.content().toString(StandardCharsets.UTF_8))
  }
}
