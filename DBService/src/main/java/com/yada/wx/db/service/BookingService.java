package com.yada.wx.db.service;


import com.yada.wx.db.service.model.Booking;

import java.util.List;

/**
 * Created by QinQiang on 2016/4/6.
 */
public interface BookingService {
    /**
     * 新增预约办卡
     *
     * @param booking
     * @return
     */
    public boolean insertBooking(Booking booking);

    /**
     * 删除预约办卡By客户姓名&联系电话
     *
     * @param clientName 客户姓名
     * @param phone      联系电话
     * @return Booking
     */
    public boolean deleteByClientNameAndPhone(String clientName, String phone);

    /**
     * 获取预约办卡信息By客户姓名&联系电话
     *
     * @param clientName 客户姓名
     * @param phone      联系电话
     * @return Booking
     */
    public List<Booking> selectByClientNameAndPhone(String clientName, String phone);
}
