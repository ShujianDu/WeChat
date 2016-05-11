package com.yada.wx.db.service.dao;

import com.yada.wx.db.service.model.AuthInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Tx on 2016/5/10.
 */
public interface AuthInfoDao extends JpaRepository<AuthInfo, Long>, JpaSpecificationExecutor<AuthInfo> {
    AuthInfo findByAuthCode(String authCode);
    void deleteByAuthCode(String authCode);
}
