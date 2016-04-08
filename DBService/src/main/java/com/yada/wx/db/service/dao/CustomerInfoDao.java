package com.yada.wx.db.service.dao;

import com.yada.wx.db.service.model.CustomerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by QinQiang on 2016/4/6.
 */
public interface CustomerInfoDao extends JpaRepository<CustomerInfo, String>, JpaSpecificationExecutor<CustomerInfo> {

    CustomerInfo findByOpenId(String openId);

    List<CustomerInfo> findByIdentityNo(String identityNo);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "UPDATE T_B_CUSTOMER_INFO SET IDENTITY_TYPE = :identityType WHERE IDENTITY_NO = :identityNo")
    int updateIdentityTypeByIdentityNo(@Param("identityNo") String identityNo, @Param("identityType") String identityType);

}
