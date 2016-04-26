package com.yada.wx.db.service.dao;

import com.yada.wx.db.service.model.Booking;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 预约办卡Dao测试
 * Created by QinQiang on 2016/4/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/applicationContext.xml")
@Transactional
public class BookingDaoTest {

    @Autowired
    private BookingDao bookingDao;

    private Booking booking;
    private String clientName = "秦始皇";
    private String mobilePhone = "18888888888";


    @Before
    public void init() {
        String seqId = bookingDao.getSequences();
        booking = new Booking();
        booking.setBookingId(seqId);
        booking.setClientId("WC" + seqId);
        booking.setClientName(clientName);
        booking.setMobilePhone(mobilePhone);
        booking.setProvId("111");
        booking.setCityId("222");
        booking.setAreaId("333");
        booking.setServiceAddr("北京市长安街甲1号");
        booking.setApplyDt("20160426");

        booking = bookingDao.saveAndFlush(booking);
    }

    @Test
    public void testFindByClientNameAndMobilePhone() {
        List<Booking> bookingList = bookingDao.findByClientNameAndMobilePhone(clientName, mobilePhone);
        Assert.assertEquals(1, bookingList.size());
        Assert.assertEquals(booking.getClientId(), bookingList.get(0).getClientId());
        Assert.assertEquals(clientName, bookingList.get(0).getClientName());
        Assert.assertEquals(mobilePhone, bookingList.get(0).getMobilePhone());
    }

    @Test
    public void testDeleteByClientNameAndMobilePhone() {
        long num = bookingDao.deleteByClientNameAndMobilePhone(clientName, mobilePhone);
        Assert.assertEquals(1, num);
        List<Booking> bookingList = bookingDao.findByClientNameAndMobilePhone(clientName, mobilePhone);
        Assert.assertEquals(0, bookingList.size());
    }
}
