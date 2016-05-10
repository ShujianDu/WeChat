package com.yada.wx.around.dhtz.biz

import java.text.SimpleDateFormat
import java.util.Date

import com.alibaba.fastjson.JSON
import com.typesafe.config.ConfigFactory
import com.yada.wx.around.SpringContext
import com.yada.wx.around.client.HttpClient
import com.yada.wx.around.client.model._
import com.yada.wx.around.dhtz.jpa.dao.AllCustomerInfoDao
import com.yada.wx.around.dhtz.jpa.model.AllCustomerInfo
import com.yada.wx.around.model.{CustomerInfoModel, NoticeModel, UnBindingModel}

import scala.collection.convert.{WrapAsJava, WrapAsScala}

/**
  * Created by QinQiang on 2016/5/10.
  */
class DhtzBiz(allCustomerInfoDao: AllCustomerInfoDao = SpringContext.context.getBean(classOf[AllCustomerInfoDao]),
              httpClient: HttpClient = SpringContext.context.getBean(classOf[HttpClient])) {

  val (gcsSessionId, gcsChannelId, getCardHolderInfoUrl, getCardInfosUrl, getVirtualCardsUrl, getCardCurrencyCodeAndStyleUrl) = {
    val config = ConfigFactory.load()
    (config.getString("gcs.gcsSessionId"), config.getString("gcs.gcsChannelId"), config.getString("gcs.getCardHolderInfoUrl"),
      config.getString("gcs.getCardInfosUrl"), config.getString("gcs.getVirtualCardsUrl"),
      config.getString("gcs.getCardCurrencyCodeAndStyleUrl"))
  }

  /**
    * 保存客户信息
    *
    * @param value
    */
  def saveAllCustomerInfo(value: String): Unit = {
    // 将Json转换为对象
    val customerInfo = JSON.parseObject(value, classOf[CustomerInfoModel]).data
    // 根据openId删除已有数据
    val count = allCustomerInfoDao.deleteByOpenId(customerInfo.openId)
    if (count != 0) {
      // TODO QQ记录日志
    }
    // 通过卡号查询客户信息
    val cardHolderInfo = getCardHolderInfoByCardNo(customerInfo.defCardNo)
    // 通过证件类型、证件号获取卡列表
    val cardInfoList = getCardInfosByIdentityTypeAndNo(customerInfo.identityType, customerInfo.identityNo)
    // 遍历卡列表通过卡号获取虚拟卡列表
    val allCustomerInfoList = cardInfoList.map(x => {
      // 获取卡产品类型
      val cardStyle = getCardStyleByCardNo(x.getCardNo)
      // 通过卡号获取虚拟卡
      val virtualCards = getVirtualCardsByCardNo(x.getCardNo)
      val virtualCardsCustomerList = virtualCards.map(y => {
        // 组装虚拟卡客户信息
        val allCustomerInfo = new AllCustomerInfo
        allCustomerInfo.openId = customerInfo.openId
        allCustomerInfo.identityType = customerInfo.identityType
        allCustomerInfo.identityNo = customerInfo.identityNo
        allCustomerInfo.mobilePhone = customerInfo.mobilePhone
        allCustomerInfo.familyName = cardHolderInfo.getFamilyName
        allCustomerInfo.firstName = cardHolderInfo.getFirstName
        allCustomerInfo.sex = cardHolderInfo.getGender
        allCustomerInfo.bindingDate = new SimpleDateFormat("yyyyMMdd").format(new Date)
        allCustomerInfo.cardNo = y
        allCustomerInfo.cardType = ""
        allCustomerInfo.cardLastFourNumber = y.substring(y.length - 4)
        allCustomerInfo.style = cardStyle
        allCustomerInfo.isDefault = "1"
        allCustomerInfo.accId = "24"
        allCustomerInfo.notice = "1"
        allCustomerInfo.add1 = "VIRTUAL"
        allCustomerInfo.billNotice = "1"
        allCustomerInfo.repaymentNotice = "1"
        allCustomerInfo
      })
      // 组装客户信息并保存
      val allCustomerInfo = new AllCustomerInfo
      allCustomerInfo.openId = customerInfo.openId
      allCustomerInfo.identityType = customerInfo.identityType
      allCustomerInfo.identityNo = customerInfo.identityNo
      allCustomerInfo.mobilePhone = customerInfo.mobilePhone
      allCustomerInfo.familyName = cardHolderInfo.getFamilyName
      allCustomerInfo.firstName = cardHolderInfo.getFirstName
      allCustomerInfo.sex = cardHolderInfo.getGender
      allCustomerInfo.bindingDate = new SimpleDateFormat("yyyyMMdd").format(new Date)
      allCustomerInfo.cardNo = x.getCardNo
      allCustomerInfo.cardType = ""
      allCustomerInfo.cardLastFourNumber = x.getCardNo.substring(x.getCardNo.length - 4)
      allCustomerInfo.style = cardStyle
      allCustomerInfo.isDefault = if (x.getCardNo.equals(customerInfo.defCardNo)) "0" else "1"
      allCustomerInfo.accId = "24"
      allCustomerInfo.notice = "1"
      allCustomerInfo.add1 = x.getMianFlag
      allCustomerInfo.billNotice = "1"
      allCustomerInfo.repaymentNotice = "1"

      virtualCardsCustomerList :+ allCustomerInfo
    }).flatMap(x => x)
    val list = WrapAsJava.asJavaIterable(allCustomerInfoList)
    allCustomerInfoDao.save(list)
  }

  /**
    * 解除绑定
    *
    * @param value
    */
  def unBinding(value: String): Unit = {
    // 将Json转换为对象
    val unBinding = JSON.parseObject(value, classOf[UnBindingModel])
    // 根据openId删除已有数据
    val count = allCustomerInfoDao.deleteByOpenId(unBinding.openID)
    // TODO QQ记录日志
  }

  /**
    * 修改推送配置
    *
    * @param value
    */
  def updateNotice(value: String): Unit = {
    // 将Json转换为对象
    val notice = JSON.parseObject(value, classOf[NoticeModel]).data
    allCustomerInfoDao.updateNoticeByIdentityNo(notice.notice, notice.billNotice, notice.repaymentNotice, notice.identityNo)
  }

  /**
    * 通过卡号获取持卡人信息
    *
    * @param cardNo 卡号
    * @return
    */
  private def getCardHolderInfoByCardNo(cardNo: String): CardHolderInfo = {
    val param = Map("tranSessionID" -> gcsSessionId, "reqChannelID" -> gcsChannelId, "cardNo" -> cardNo)
    httpClient.send(getCardHolderInfoUrl, param, classOf[CardHolderInfoResp]).getData
  }


  /**
    * 通过证件类型证件号获取卡列表
    *
    * @param identityType
    * @param identityNo
    * @return 卡信息列表
    */
  private def getCardInfosByIdentityTypeAndNo(identityType: String, identityNo: String): List[CardInfo] = {
    val param = Map("tranSessionID" -> gcsSessionId, "reqChannelID" -> gcsChannelId, "idType" -> identityType, "idNum" -> identityNo)
    val data = httpClient.send(getCardInfosUrl, param, classOf[CardInfoResp]).getData
    WrapAsScala.asScalaBuffer(data).toList
  }

  /**
    * 通过卡号获取虚拟卡信息
    *
    * @param cardNo 卡号
    * @return 虚拟卡信息
    */
  private def getVirtualCardsByCardNo(cardNo: String): List[String] = {
    val param = Map("tranSessionID" -> gcsSessionId, "reqChannelID" -> gcsChannelId, "cardNo" -> cardNo)
    val data = httpClient.send(getVirtualCardsUrl, param, classOf[ListStringResp]).getData
    WrapAsScala.asScalaBuffer(data).toList
  }

  /**
    * 通过卡号获取卡产品类型
    *
    * @param cardNo 卡号
    * @return 卡产品类型
    */
  private def getCardStyleByCardNo(cardNo: String): String = {
    val param = Map("tranSessionID" -> gcsSessionId, "reqChannelID" -> gcsChannelId, "cardNo" -> cardNo)
    val data = httpClient.send(getCardCurrencyCodeAndStyleUrl, param, classOf[CardCurrencyCodeAndStyleResp]).getData
    data.getProductType
  }
}