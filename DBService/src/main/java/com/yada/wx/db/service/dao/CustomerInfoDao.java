package com.yada.wx.db.service.dao;

import com.yada.wx.db.service.model.CustomerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by QinQiang on 2016/4/6.
 */
public interface CustomerInfoDao extends JpaRepository<CustomerInfo, String>, JpaSpecificationExecutor<CustomerInfo> {

    CustomerInfo findByOpenId(String openId);

    List<CustomerInfo> findByIdentityNo(String identityNo);

}
