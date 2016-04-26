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
public interface BookingDao extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {

    Long deleteByClientNameAndMobilePhone(String clientName, String mobilePhone);

    List<Booking> findByClientNameAndMobilePhone(String clientName, String mobilePhone);

    @Query(nativeQuery = true, value = "select SEQ_T_B_BOOKING.Nextval from dual")
    String getSequences();
}
