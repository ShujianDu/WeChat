package com.yada.wx.db.service.dao;

import com.yada.wx.db.service.model.InstallmentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by QinQiang on 2016/4/8.
 */
public interface InstallmentInfoDao extends JpaRepository<InstallmentInfo, String>, JpaSpecificationExecutor<InstallmentInfo> {

}
