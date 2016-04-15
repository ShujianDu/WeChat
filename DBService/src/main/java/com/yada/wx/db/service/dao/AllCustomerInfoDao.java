package com.yada.wx.db.service.dao;

import com.yada.wx.db.service.model.AllCustomerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 推送管理
 * Created by QinQiang on 2016/4/15.
 */
public interface AllCustomerInfoDao extends JpaRepository<AllCustomerInfo, Long>, JpaSpecificationExecutor<AllCustomerInfo> {

    List<AllCustomerInfo> findByIdentityNo(String identityNo);

    /**
     * 根据证件号修改通知
     * @param notice 动户通知
     * @param billNotice 账单通知
     * @param repaymentNotice 还款提醒通知
     * @param identityNo 证件号
     * @return int
     */
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "UPDATE WXDH_T_B_ALL_CUSTOMER_INFO SET NOTICE = :notice, BILL_NOTICE = :billNotice, REPAYMENT_NOTICE = :repaymentNotice WHERE IDENTITY_NO = :identityNo")
    int updateNoticeByIdentityNo(@Param("notice") String notice, @Param("billNotice") String billNotice, @Param("repaymentNotice") String repaymentNotice, @Param("identityNo") String identityNo);
}
