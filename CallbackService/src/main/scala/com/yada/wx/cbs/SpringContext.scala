package com.yada.wx.cbs

import com.typesafe.config.ConfigFactory
import org.springframework.context.annotation.AnnotationConfigApplicationContext

/**
  * spring的上下文
  */
object SpringContext {
  val context = {
    val configFactory = ConfigFactory.load()
    new AnnotationConfigApplicationContext(
      Class.forName(configFactory.getString("spring.jpaConfig")),
      Class.forName(configFactory.getString("spring.dbConfig")),
      Class.forName(configFactory.getString("spring.rmiConfig")))
  }
}


