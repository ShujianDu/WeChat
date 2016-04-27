package com.yada.wechatbank.service;

import com.yada.wx.db.service.model.Booking;
import com.yada.wx.db.service.model.NuwOrg;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 预约办卡测试
 * Created by Echo on 2016/4/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/applicationContext.xml")
public class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    private Booking booking;

    @Before
    public void init(){
        booking = new Booking();
        booking.setBookingId("111");
        booking.setMobilePhone("13800138000");
        booking.setApplyDt("20160426");
        booking.setAreaId("210000");
        booking.setCityId("100000");
        booking.setClientId("WC12345612111");
        booking.setClientName("test111");
        booking.setProvId("134556");
        booking.setServiceAddr("测试地址");
    }

    @Test
    public void testGetSequences(){
        String result = bookingService.getSequences();
        Assert.assertNotNull(result);
    }

    @Test
    public void testInsertBooking(){
        boolean result = bookingService.insertBooking(booking);
        Assert.assertEquals(true,result);
    }

    @Test
    public void testSelectNumOrgList(){
        List<NuwOrg> result = bookingService.selectNumOrgList(null);
        Assert.assertNotNull(result);
    }

    @Test
    public void isHaveBooking(){
        String result = bookingService.isHaveBooking("123","123");
        Assert.assertNull(result);
    }



}
