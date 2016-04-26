package com.yada.wx.cb.data.service

import com.yada.wx.cb.data.service.jpa.JpaConfig
import org.springframework.context.annotation.AnnotationConfigApplicationContext

/**
  * spring的上下文
  */
object SpringContext {
  val context = new AnnotationConfigApplicationContext(classOf[JpaConfig])
}
