package com.yada.wechatbank.service.impl;

import java.util.List;

import com.yada.wechatbank.kafka.MessageProducer;
import com.yada.wechatbank.kafka.TopicEnum;
import com.yada.wechatbank.service.BookingService;
import com.yada.wx.db.service.dao.BookingDao;
import com.yada.wx.db.service.dao.NuwOrgDao;
import com.yada.wx.db.service.model.Booking;
import com.yada.wx.db.service.model.NuwOrg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 预约办卡Manager
 *
 * @author zm
 */
@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingDao bookingDao;
    @Autowired
    private NuwOrgDao nuwOrgDao;
    @Autowired
    private MessageProducer messageProducer;

    /**
     * 获取客户ID的Sequences，前置处理
     *
     * @return 序列值
     */
    @Override
    public String getSequences() {
        return bookingDao.getSequences();
    }

    /**
     * 预约办卡新增
     *
     * @param booking 预约办卡实体
     * @return 预约办卡结果
     */
    @Override
    public boolean insertBooking(Booking booking) {
        if (bookingDao.findByClientNameAndMobilePhone(booking.getClientName(), booking.getMobilePhone()) != null) {
            bookingDao.deleteByClientNameAndMobilePhone(booking.getClientName(), booking.getMobilePhone());
        }
        Booking res = bookingDao.save(booking);
        messageProducer.send(TopicEnum.EBANK_DO, "booking", booking);
        return res != null;
    }

    /**
     * 查询地区
     *
     * @param pOrgId 父级机构号
     * @return 机构列表
     */
    @Override
    public List<NuwOrg> selectNumOrgList(String pOrgId) {
        return nuwOrgDao.findByPOrgId(pOrgId);
    }

    /**
     * 判断预约办卡是否已存在
     *
     * @param clientName  用户名
     * @param mobilePhone 手机号
     * @return 预约结果
     */
    @Override
    public String isHaveBooking(String clientName, String mobilePhone) {
        List<Booking> bookingList = bookingDao.findByClientNameAndMobilePhone(clientName, mobilePhone);
        if (bookingList == null || bookingList.size() == 0) {
            return null;
        }
        return "true";
    }

}
