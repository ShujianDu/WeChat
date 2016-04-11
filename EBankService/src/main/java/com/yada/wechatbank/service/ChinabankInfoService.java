package com.yada.wechatbank.service;


import com.yada.wx.db.service.model.ChinabankInfo;

import java.util.List;

/**
 * Created by QinQiang on 2016/4/6.
 */
public interface ChinabankInfoService {
    /**
     * 根据纬度和经度查询附近中行网店
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @return ChinabankInfo
     */
    public List<ChinabankInfo> findNearestLocation(String latitude, String longitude);
}
