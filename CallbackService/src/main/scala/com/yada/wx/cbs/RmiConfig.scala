package com.yada.wx.cbs

import com.typesafe.config.ConfigFactory
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.remoting.rmi.RmiProxyFactoryBean

@Configuration
class RmiConfig {

  @Bean
  def getIAccessTokenTool: RmiProxyFactoryBean = {
    val config = ConfigFactory.load()
    val tokenURL = config.getString("token.url")
    val lookupStubOnStartup = config.getBoolean("token.lookupStubOnStartup")
    val refreshStubOnConnectFailure = config.getBoolean("token.refreshStubOnConnectFailure")
    val bean = new RmiProxyFactoryBean
    bean.setServiceUrl(tokenURL)
    bean.setServiceInterface(classOf[IAccessTokenTool])
    bean.setLookupStubOnStartup(lookupStubOnStartup)
    bean.setRefreshStubOnConnectFailure(refreshStubOnConnectFailure)
    bean
  }
}
