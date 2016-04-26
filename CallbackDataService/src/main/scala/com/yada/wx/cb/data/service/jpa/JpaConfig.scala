package com.yada.wx.cb.data.service.jpa

import java.util.Properties

import com.typesafe.config.ConfigFactory
import com.yada.wx.cb.data.service.common.DatabaseEncrypt
import org.apache.tomcat.jdbc.pool.DataSourceFactory
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.{Database, HibernateJpaVendorAdapter}

import scala.io.Source

/**
  * JPA的配置
  */
@Configuration
@EnableJpaRepositories(value = Array("com.yada.wx.cb.data.service.jpa.dao"), enableDefaultTransactions = false)
class JpaConfig {

  @Bean
  def entityManagerFactory(): LocalContainerEntityManagerFactoryBean = {
    val config = ConfigFactory.load()
    val jdbcPath = config.getString("jdbc.path")
    val encrypt = config.getBoolean("jdbc.encipher")
    val dsf = new DataSourceFactory
    val dsp = new Properties
    dsp.load(Source.fromFile(jdbcPath, "UTF-8").reader())
    if (encrypt) {
      val e = new DatabaseEncrypt
      dsp.setProperty("username", e.decrypt(dsp.getProperty("username")))
      dsp.setProperty("password", e.decrypt(dsp.getProperty("password")))
    }
    val dataSource = dsf.createDataSource(dsp)
    val emf = new LocalContainerEntityManagerFactoryBean()
    emf.setDataSource(dataSource)
    emf.setPackagesToScan("com.yada.wx.cb.data.service.jpa.model")
    val adapter = new HibernateJpaVendorAdapter()
    adapter.setShowSql(true)
    adapter.setGenerateDdl(true)
    adapter.setDatabase(Database.H2)
    emf.setJpaVendorAdapter(adapter)
    emf
  }
}
