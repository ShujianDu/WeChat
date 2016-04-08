package com.yada.wx.db.service.impl;

import com.yada.wx.db.service.InstallmentInfoService;
import com.yada.wx.db.service.dao.InstallmentInfoDao;
import com.yada.wx.db.service.model.InstallmentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by QinQiang on 2016/4/8.
 */
@Service
@Transactional
public class InstallmentInfoServiceImpl implements InstallmentInfoService {

    @Autowired
    private InstallmentInfoDao installmentInfoDao;

    @Override
    public boolean insertInstallmentInfo(InstallmentInfo info) {
        try {
            installmentInfoDao.save(info);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
