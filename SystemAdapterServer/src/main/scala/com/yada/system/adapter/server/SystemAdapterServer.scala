package com.yada.system.adapter.server

import com.typesafe.config.ConfigFactory

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.{ChannelInitializer, ChannelOption}
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.http.{HttpObjectAggregator, HttpServerCodec}
import io.netty.handler.logging.{LogLevel, LoggingHandler}

object SystemAdapterServer {

  def start(): Unit ={
    val port = ConfigFactory.load().getInt("systemAdapter.server.port")
    val ip = "22.7.16.95"

    val bossGroup = new NioEventLoopGroup(1)
    val workGroup = new NioEventLoopGroup()

    val b = new ServerBootstrap

    b.group(bossGroup, workGroup)
      .channel(classOf[NioServerSocketChannel])
      .option(ChannelOption.SO_BACKLOG, Int.box(256))
      .childHandler(new ChannelInitializer[SocketChannel] {
        override def initChannel(c: SocketChannel): Unit = {
          val pipeline = c.pipeline()
          pipeline.addLast(new LoggingHandler(LogLevel.INFO))
          pipeline.addLast(new HttpServerCodec())
            .addLast(new HttpObjectAggregator(1024 * 1024))
            .addLast(new SystemAdapterHandler)
        }
      }).bind(ip, port)
  }
}
