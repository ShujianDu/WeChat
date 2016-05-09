package com.yada.wx.around.dhtz.jpa.dao

import com.yada.wx.around.dhtz.jpa.model.AllCustomerInfo
import org.springframework.data.repository.CrudRepository

/**
  * Created by QinQiang on 2016/5/9.
  */
trait AllCustomerInfoDao extends CrudRepository[AllCustomerInfo, String] {

}
