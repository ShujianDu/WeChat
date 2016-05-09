package com.yada.wx.around

import com.yada.wx.around.client.HttpClient
import org.springframework.context.annotation.{Bean, Configuration}

/**
  * Created by QinQiang on 2016/5/9.
  */
@Configuration
class AppConfig {
  @Bean
  def entityManagerFactory(): HttpClient = {
    val httpClient = new HttpClient("http://22.7.14.148:8080", 10000, 10000)
    httpClient
  }
}
