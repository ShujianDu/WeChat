package com.yada.wx.around

import com.yada.wx.around.dhtz.DhtzJpaConfig
import org.springframework.context.annotation.AnnotationConfigApplicationContext

object SpringContext {
  val context = {
    new AnnotationConfigApplicationContext(classOf[DhtzJpaConfig])
  }
}
