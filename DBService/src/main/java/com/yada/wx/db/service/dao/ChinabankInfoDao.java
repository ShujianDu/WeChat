package com.yada.wx.db.service.dao;

import com.yada.wx.db.service.model.ChinabankInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by QinQiang on 2016/4/8.
 */
public interface ChinabankInfoDao extends JpaRepository<ChinabankInfo, String>, JpaSpecificationExecutor<ChinabankInfo> {
}
