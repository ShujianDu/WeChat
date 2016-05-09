package com.yada.wx.cb.data.service.jpa

import javax.sql.DataSource

import com.typesafe.config.ConfigFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.{Database, HibernateJpaVendorAdapter}

/**
  * JPA的配置
  */
@Configuration
@EnableJpaRepositories(value = Array("com.yada.wx.cb.data.service.jpa.dao"), enableDefaultTransactions = false)
class JpaConfig {

  @Autowired
  var dataSource: DataSource = _

  @Bean
  def entityManagerFactory(): LocalContainerEntityManagerFactoryBean = {
    val showSQL = true
    val generateDDL = false
    val emf = new LocalContainerEntityManagerFactoryBean()
    emf.setDataSource(dataSource)
    emf.setPackagesToScan("com.yada.wx.cb.data.service.jpa.model")
    val adapter = new HibernateJpaVendorAdapter()
    adapter.setShowSql(showSQL)
    adapter.setGenerateDdl(generateDDL)
    adapter.setDatabase(Database.DEFAULT)
    emf.setJpaVendorAdapter(adapter)
    emf
  }
}
