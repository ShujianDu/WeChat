package com.yada.wx.db.service.dao;

import com.yada.wx.db.service.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by QinQiang on 2016/4/8.
 */
public interface BookingDao extends JpaRepository<Booking, String>, JpaSpecificationExecutor<Booking> {
    
}
