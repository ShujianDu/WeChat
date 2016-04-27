package com.yada.wx.cb.data.service.jpa

import java.util.Properties
import javax.sql.DataSource

import com.typesafe.config.ConfigFactory
import org.apache.tomcat.jdbc.pool.DataSourceFactory
import org.springframework.context.annotation.{Bean, Configuration}

/**
  * 简单的数据源
  */
@Configuration
class SimpleDataSource {

  @Bean
  def getDataSource: DataSource = {
    val cf = ConfigFactory.load()
    val dsf = new DataSourceFactory
    val dsp = new Properties
    dsp.setProperty("driverClassName", cf.getString("db.simple.driverClassName"))
    dsp.setProperty("url", cf.getString("db.simple.url"))
    dsp.setProperty("username", cf.getString("db.simple.username"))
    dsp.setProperty("password", cf.getString("db.simple.password"))
    dsf.createDataSource(dsp)
  }
}
