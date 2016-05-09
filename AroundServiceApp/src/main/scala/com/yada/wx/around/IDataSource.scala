package com.yada.wx.around

import java.util.Properties
import javax.sql.DataSource

import com.typesafe.config.ConfigFactory
import org.apache.tomcat.jdbc.pool.DataSourceFactory

trait IDataSource {
  def dbKey: String

  def get(): DataSource = {
    val cf = ConfigFactory.load()
    val dsf = new DataSourceFactory
    val dsp = new Properties
    dsp.setProperty("driverClassName", cf.getString(s"db.$dbKey.driverClassName"))
    dsp.setProperty("url", cf.getString(s"db.$dbKey.url"))
    if (cf.getBoolean(s"db.$dbKey.encipher")) {
      val de = new DatabaseEncrypt
      de.decrypt(cf.getString(s"db.$dbKey.username")) -> de.decrypt(cf.getString(s"db.$dbKey.password"))
    } else {
      cf.getString(s"db.$dbKey.username") -> cf.getString(s"db.$dbKey.password")
    } match {
      case (username, password) =>
        dsp.setProperty("username", username)
        dsp.setProperty("password", password)
    }
    dsp.setProperty("maxActive", cf.getString(s"db.$dbKey.maxActive"))
    dsp.setProperty("maxIdle", cf.getString(s"db.$dbKey.maxIdle"))
    dsp.setProperty("defaultAutoCommit", cf.getString(s"db.$dbKey.defaultAutoCommit"))
    dsp.setProperty("timeBetweenEvictionRunsMillis", cf.getString(s"db.$dbKey.timeBetweenEvictionRunsMillis"))
    dsp.setProperty("timeBetweenEvictionRunsMillis", cf.getString(s"db.$dbKey.timeBetweenEvictionRunsMillis"))
    dsp.setProperty("minEvictableIdleTimeMillis", cf.getString(s"db.$dbKey.minEvictableIdleTimeMillis"))
    dsp.setProperty("testOnBorrow", cf.getString(s"db.$dbKey.testOnBorrow"))
    dsp.setProperty("validationQuery", cf.getString(s"db.$dbKey.validationQuery"))
    dsf.createDataSource(dsp)
  }
}
