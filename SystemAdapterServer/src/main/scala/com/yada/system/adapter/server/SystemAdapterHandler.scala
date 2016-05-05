package com.yada.system.adapter.server

import java.nio.charset.StandardCharsets

import io.netty.buffer.Unpooled
import io.netty.channel.{ChannelFutureListener, ChannelHandlerContext, SimpleChannelInboundHandler}
import io.netty.handler.codec.http.{DefaultFullHttpResponse, FullHttpRequest, HttpHeaders, HttpResponseStatus}

class SystemAdapterHandler extends SimpleChannelInboundHandler[FullHttpRequest] {
  override def channelRead0(context: ChannelHandlerContext, req: FullHttpRequest): Unit = {
    val content = Dispatcher.dispatch(req)
    val resp = makeResponse(content, req)
    val f = context.writeAndFlush(resp)
    if (!HttpHeaders.isKeepAlive(resp)) f.addListener(ChannelFutureListener.CLOSE)
  }

  private def makeResponse(msg: String, req: FullHttpRequest) = {
    val buf = Unpooled.copiedBuffer(msg, StandardCharsets.UTF_8)
    val resp = new DefaultFullHttpResponse(req.getProtocolVersion, HttpResponseStatus.OK, buf)
    resp.headers().add(HttpHeaders.Names.CONTENT_TYPE, "text/plain; encoding=utf-8")
    resp.headers().add(HttpHeaders.Names.CONTENT_LENGTH, resp.content().readableBytes())
    if (HttpHeaders.isKeepAlive(req))
      resp.headers().add(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE)
    resp
  }
}
