package com.yada.wx.around

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.{Database, HibernateJpaVendorAdapter}

trait JpaConfig extends IDataSource {

  def getLocalContainerEntityManagerFactoryBean: LocalContainerEntityManagerFactoryBean = {
    val emf = new LocalContainerEntityManagerFactoryBean()
    emf.setDataSource(get())
    emf.setPackagesToScan(packagesToScan)
    val adapter = new HibernateJpaVendorAdapter()
    adapter.setShowSql(showSQL)
    adapter.setGenerateDdl(generateDDL)
    adapter.setDatabase(Database.DEFAULT)
    emf.setJpaVendorAdapter(adapter)
    emf
  }

  protected def showSQL = true

  protected def generateDDL = false

  protected def packagesToScan: String
}
