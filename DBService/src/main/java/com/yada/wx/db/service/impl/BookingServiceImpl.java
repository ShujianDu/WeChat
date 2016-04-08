package com.yada.wx.db.service.impl;

import com.yada.wx.db.service.BookingService;
import com.yada.wx.db.service.dao.BookingDao;
import com.yada.wx.db.service.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by QinQiang on 2016/4/8.
 */
@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingDao bookingDao;

    @Override
    public boolean insertBooking(Booking booking) {
        try {
            bookingDao.save(booking);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteByClientNameAndPhone(String clientName, String phone) {
        try {
            bookingDao.deleteByClientNameAndPhone(clientName, phone);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Booking> selectByClientNameAndPhone(String clientName, String phone) {
        List<Booking> list = bookingDao.findByClientNameAndPhone(clientName, phone);
        return list;
    }
}
