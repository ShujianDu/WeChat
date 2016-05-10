package com.yada.wx.around.dhtz.jpa.dao


import com.yada.wx.around.dhtz.jpa.model.AllCustomerInfo
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param


/**
  * Created by QinQiang on 2016/5/9.
  */
trait AllCustomerInfoDao extends CrudRepository[AllCustomerInfo, String] {
  /**
    * 根据openId删除
    */
  def deleteByOpenId(openId: String): Int

  /**
    * 根据证件号修改通知
    *
    * @param notice          动户通知
    * @param billNotice      账单通知
    * @param repaymentNotice 还款提醒通知
    * @param identityNo      证件号
    * @return Int
    */
  @Modifying(clearAutomatically = true)
  @Query(nativeQuery = true, value = "UPDATE T_B_ALL_CUSTOMER_INFO SET NOTICE = :notice, BILL_NOTICE = :billNotice, REPAYMENT_NOTICE = :repaymentNotice WHERE IDENTITY_NO = :identityNo")
  def updateNoticeByIdentityNo(@Param("notice") notice: String, @Param("billNotice") billNotice: String, @Param("repaymentNotice") repaymentNotice: String, @Param("identityNo") identityNo: String): Int
}
