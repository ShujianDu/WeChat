package com.yada.wx.db.service.dao;

import com.yada.wx.db.service.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by QinQiang on 2016/4/8.
 */
public interface BookingDao extends JpaRepository<Booking, String>, JpaSpecificationExecutor<Booking> {

    // TODO QQ 更新JPA版本或添加SQL语句
    int deleteByClientNameAndPhone(@Param("clientName") String clientName, @Param("phone") String phone);

    Booking findByClientNameAndPhone(String clientName, String phone);
}
