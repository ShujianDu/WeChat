package com.yada.wechatbank.service;

import com.yada.wx.db.service.model.Booking;
import com.yada.wx.db.service.model.NuwOrg;

import java.util.List;

/**
 * Created by Echo on 2016/4/11.
 */
public interface BookingService {


    /**
     * 获取客户ID的Sequences，前置处理
     * @return Sequences
     */
    String getSequences();

    /**
     * 预约办卡新增
     * @param booking 预约办卡实体
     * @return 预约办卡结果
     */
    boolean insertBooking(Booking booking);
    /**
     * 查询地区
     * @param pOrgId pOrgId
     * @return 地区列表
     */
    List<NuwOrg> selectNumOrgList(String pOrgId);

    /**
     * 判断预约办卡是否已存在
     * @param clientName 客户姓名
     * @param mobilePhone 手机号
     * @return 判断预约办卡是否存在结果
     */
    String isHaveBooking(String clientName, String mobilePhone);

}
