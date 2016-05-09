package com.yada.wx.around.dhtz

import com.yada.wx.around.JpaConfig
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean

@Configuration
@EnableJpaRepositories(value = Array("com.yada.wx.cb.data.service.jpa.dao"), enableDefaultTransactions = false)
class DhtzJpaConfig extends JpaConfig {
  @Bean
  def entityManagerFactory(): LocalContainerEntityManagerFactoryBean = {
    getLocalContainerEntityManagerFactoryBean
  }

  /**
    * 扫描的包名
    *
    * @return
    */
  override protected def packagesToScan: String = ???

  /**
    * 数据库配置文件关键字
    *
    * @return
    */
  override def dbKey: String = "dhtz"
}
