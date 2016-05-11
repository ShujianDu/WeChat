package com.yada.wx.around

import com.yada.wx.around.dhtz.DhtzJpaConfig
import com.yada.wx.around.syncdata.SyncDataJpaConfig
import org.springframework.context.annotation.AnnotationConfigApplicationContext

object SpringContext {
  val context = {
    new AnnotationConfigApplicationContext(classOf[DhtzJpaConfig], classOf[AppConfig],classOf[SyncDataJpaConfig])
  }
}
