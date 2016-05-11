package com.yada.wx.around.syncdata

import com.yada.wx.around.JpaConfig
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean

@Configuration
@EnableJpaRepositories(value = Array("com.yada.wx.around.syncdata.jpa.dao"), enableDefaultTransactions = false, entityManagerFactoryRef = "syncDataEntityManagerFactory")
class SyncDataJpaConfig extends JpaConfig {

  @Bean
  def syncDataEntityManagerFactory(): LocalContainerEntityManagerFactoryBean = {
    getLocalContainerEntityManagerFactoryBean
  }

  /**
    * 扫描的包名
    *
    * @return
    */
  override protected def packagesToScan: String = "com.yada.wx.around.syncdata.jpa.model"

  /**
    * 数据库配置文件关键字
    *
    * @return
    */
  override def dbKey: String = "syncdata"
}
