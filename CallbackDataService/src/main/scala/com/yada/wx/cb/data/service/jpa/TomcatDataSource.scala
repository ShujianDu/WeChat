package com.yada.wx.cb.data.service.jpa

import java.util.Properties
import javax.sql.DataSource

import com.typesafe.config.ConfigFactory
import com.yada.wx.cb.data.service.common.DatabaseEncrypt
import org.apache.tomcat.jdbc.pool.DataSourceFactory
import org.springframework.context.annotation.{Bean, Configuration}

/**
  * tomcat连接池数据源
  */
@Configuration
class TomcatDataSource {
  @Bean
  def getDataSource: DataSource = {
    val cf = ConfigFactory.load()
    val dsf = new DataSourceFactory
    val dsp = new Properties
    dsp.setProperty("driverClassName", cf.getString("db.tomcat.driverClassName"))
    dsp.setProperty("url", cf.getString("db.tomcat.url"))
    if (cf.getBoolean("db.tomcat.encipher")) {
      val de = new DatabaseEncrypt
      de.decrypt(cf.getString("db.tomcat.username")) -> de.decrypt(cf.getString("db.tomcat.password"))
    } else {
      cf.getString("db.tomcat.username") -> cf.getString("db.tomcat.password")
    } match {
      case (username, password) =>
        dsp.setProperty("username", username)
        dsp.setProperty("password", password)
    }
    dsp.setProperty("maxActive", cf.getString("db.tomcat.maxActive"))
    dsp.setProperty("maxIdle", cf.getString("db.tomcat.maxIdle"))
    dsp.setProperty("defaultAutoCommit", cf.getString("db.tomcat.defaultAutoCommit"))
    dsp.setProperty("timeBetweenEvictionRunsMillis", cf.getString("db.tomcat.timeBetweenEvictionRunsMillis"))
    dsp.setProperty("timeBetweenEvictionRunsMillis", cf.getString("db.tomcat.timeBetweenEvictionRunsMillis"))
    dsp.setProperty("minEvictableIdleTimeMillis", cf.getString("db.tomcat.minEvictableIdleTimeMillis"))
    dsp.setProperty("testOnBorrow", cf.getString("db.tomcat.testOnBorrow"))
    dsp.setProperty("validationQuery", cf.getString("db.tomcat.validationQuery"))
    dsf.createDataSource(dsp)
  }
}
