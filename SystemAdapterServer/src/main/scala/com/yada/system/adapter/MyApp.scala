package com.yada.system.adapter

import com.typesafe.scalalogging.slf4j.Logger
import com.yada.system.adapter.server.SystemAdapterServer
import org.slf4j.LoggerFactory

object MyApp extends App {
  val log = Logger(LoggerFactory.getLogger("MyApp"))
  SystemAdapterServer.start()
  log.info("server start...")
}
