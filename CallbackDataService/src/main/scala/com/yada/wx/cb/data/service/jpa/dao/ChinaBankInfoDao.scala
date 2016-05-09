package com.yada.wx.cb.data.service.jpa.dao

import com.yada.wx.cb.data.service.jpa.model.ChinaBankInfo
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

trait ChinaBankInfoDao extends CrudRepository[ChinaBankInfo, String] {
  @Query(nativeQuery = true, value = "with a as (\n\tselect t.*,(power(abs(t.latitude- :latitude), 2)+power(abs(t.longitude- :longitude), 2)) itude\n\tfrom t_b_chinabank_info t\n\twhere t.latitude!=0 and t.longitude!=0 and t.latitude is not null \n\tand\tt.longitude is not null order by itude asc)\nselect * from a where rownum<=10")
  def findNearBanks(@Param("latitude") latitude: String, @Param("longitude") longitude: String): java.util.List[ChinaBankInfo]
}
