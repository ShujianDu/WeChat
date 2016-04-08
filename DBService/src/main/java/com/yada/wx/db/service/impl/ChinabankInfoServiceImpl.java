package com.yada.wx.db.service.impl;

import com.yada.wx.db.service.ChinabankInfoService;
import com.yada.wx.db.service.dao.ChinabankInfoDao;
import com.yada.wx.db.service.model.ChinabankInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by QinQiang on 2016/4/8.
 */
@Service
@Transactional
public class ChinabankInfoServiceImpl implements ChinabankInfoService {

    @Autowired
    private ChinabankInfoDao chinabankInfoDao;

    @Override
    public List<ChinabankInfo> findNearestLocation(String latitude, String longitude) {
        List<ChinabankInfo> list = chinabankInfoDao.findByLatitudeAndLongitude(latitude, longitude);
        return list;
    }
}
