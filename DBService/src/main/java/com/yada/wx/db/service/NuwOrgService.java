package com.yada.wx.db.service;


import com.yada.wx.db.service.model.NuwOrg;

import java.util.List;

/**
 * Created by QinQiang on 2016/4/6.
 */
public interface NuwOrgService {
    /**
     * 根据父机构ID查询子机构信息
     *
     * @param pOrgId 父机构ID
     * @return NuwOrg
     */
    public List<NuwOrg> selectByPOrgId(String pOrgId);
}
