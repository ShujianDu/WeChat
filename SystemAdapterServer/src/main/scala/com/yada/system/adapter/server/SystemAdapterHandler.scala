package com.yada.system.adapter.server

import io.netty.channel.{ChannelHandlerContext, SimpleChannelInboundHandler}
import io.netty.handler.codec.http.FullHttpRequest

class SystemAdapterHandler extends SimpleChannelInboundHandler[FullHttpRequest] {
  override def channelRead0(channelHandlerContext: ChannelHandlerContext, i: FullHttpRequest): Unit = {
  }
}
