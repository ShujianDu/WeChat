package com.yada.wx.db.service.dao;

import com.yada.wx.db.service.model.NuwOrg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by QinQiang on 2016/4/8.
 */
public interface NuwOrgDao extends JpaRepository<NuwOrg, String>, JpaSpecificationExecutor<NuwOrg> {
    List<NuwOrg> findByPOrgId(String pOrgId);
}
