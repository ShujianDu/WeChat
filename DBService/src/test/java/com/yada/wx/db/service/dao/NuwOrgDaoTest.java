package com.yada.wx.db.service.dao;

import com.yada.wx.db.service.model.NuwOrg;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by QinQiang on 2016/4/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/applicationContext.xml")
@Transactional
public class NuwOrgDaoTest {

    @Autowired
    private NuwOrgDao nuwOrgDao;

    private String pOrgId = "110100";

    @Test
    public void testFindByPOrgId() {
        List<NuwOrg> nuwOrgList = nuwOrgDao.findByPOrgId(pOrgId);
        Assert.assertNotNull(nuwOrgList);
        Assert.assertEquals(9, nuwOrgList.size());
    }

}
