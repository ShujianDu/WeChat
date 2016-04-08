package com.yada.wx.db.service.dao;

import com.yada.wx.db.service.model.ChinabankInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by QinQiang on 2016/4/8.
 */
public interface ChinabankInfoDao extends JpaRepository<ChinabankInfo, String>, JpaSpecificationExecutor<ChinabankInfo> {

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "with a as ( select t.*, (power(abs(t.latitude - :latitude), 2)+power(abs(t.longitude - :longitude), 2)) itude " +
            "from t_b_chinabank_info t where t.latitude!=0 and t.longitude!=0 and t.latitude is not null and t.longitude is not null order by itude asc) " +
            "select * from a where rownum<=10")
    List<ChinabankInfo> findByLatitudeAndLongitude(@Param("latitude") String latitude, @Param("longitude") String longitude);
}
