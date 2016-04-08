package com.yada.wx.db.service.impl;

import com.yada.wx.db.service.NuwOrgService;
import com.yada.wx.db.service.dao.NuwOrgDao;
import com.yada.wx.db.service.model.NuwOrg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by QinQiang on 2016/4/8.
 */
@Service
@Transactional
public class NuwOrgServiceImpl implements NuwOrgService {

    @Autowired
    private NuwOrgDao nuwOrgDao;

    @Override
    public List<NuwOrg> selectByPOrgId(String pOrgId) {
        return nuwOrgDao.selectByPOrgId(pOrgId);
    }

}
