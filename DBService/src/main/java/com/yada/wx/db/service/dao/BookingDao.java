package com.yada.wx.db.service.dao;

import com.yada.wx.db.service.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by QinQiang on 2016/4/8.
 */
public interface BookingDao extends JpaRepository<Booking, String>, JpaSpecificationExecutor<Booking> {

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "DELETE FROM T_B_BOOKING WHERE CLIENT_NAME = :clientName AND PHONE = :phone")
    int deleteByClientNameAndPhone(@Param("clientName") String clientName, @Param("phone") String phone);

    List<Booking> findByClientNameAndPhone(String clientName, String phone);
}
