package com.yada.wx.cb.data.service.jpa

import java.util.Properties
import javax.persistence.EntityManagerFactory

import org.apache.tomcat.jdbc.pool.DataSourceFactory
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.{Database, HibernateJpaVendorAdapter}

/**
  * JPA的配置
  */
@Configuration
@EnableJpaRepositories(value = Array("com.yada.wx.cb.data.service.jpa.dao"),
  entityManagerFactoryRef = "entityManagerFactory")
@EnableAutoConfiguration(exclude = Array(classOf[JpaRepositoriesAutoConfiguration],
  classOf[HibernateJpaAutoConfiguration],
  classOf[DataSourceAutoConfiguration]))
class JpaConfig {
  @Bean(name = Array("entityManagerFactory"))
  def entityManagerFactory(): EntityManagerFactory = {
    val dataSourceFactory = new DataSourceFactory
    val dataSourceProperties = new Properties
    dataSourceProperties.put("driverClassName", "org.h2.Driver")
    dataSourceProperties.put("username", "sa")
    dataSourceProperties.put("password", "")
    dataSourceProperties.put("url", "jdbc:h2:~/test")
    val dataSource = dataSourceFactory.createDataSource(dataSourceProperties)
    val emf = new LocalContainerEntityManagerFactoryBean()
    emf.setDataSource(dataSource)
    emf.setPackagesToScan("com.yada.wx.cb.data.service.jpa.model")
    val adapter = new HibernateJpaVendorAdapter()
    adapter.setShowSql(true)
    adapter.setGenerateDdl(true)
    adapter.setDatabase(Database.H2)
    emf.setJpaVendorAdapter(adapter)
    emf.getObject
  }
}
