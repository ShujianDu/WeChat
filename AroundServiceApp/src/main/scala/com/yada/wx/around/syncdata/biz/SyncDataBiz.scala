package com.yada.wx.around.syncdata.biz

import java.text.SimpleDateFormat
import java.util.Date

import com.alibaba.fastjson.JSON
import com.typesafe.config.ConfigFactory
import com.yada.wx.around.SpringContext
import com.yada.wx.around.client.HttpClient
import com.yada.wx.around.client.model.{CardCurrencyCodeAndStyleResp, _}
import com.yada.wx.around.model.CustomerInfoModel
import com.yada.wx.around.syncdata.jpa.dao.{CustomerInfoDao, CustomerInfoExtDao, CustomerVirtualCardsDao}
import com.yada.wx.around.syncdata.jpa.model.{CustomerInfo, CustomerInfoExt, CustomerVirtualCards}

import scala.collection.convert.{WrapAsJava, WrapAsScala}

/**
  * Created by QinQiang on 2016/5/10.
  */
class SyncDataBiz(customerInfoDao: CustomerInfoDao = SpringContext.context.getBean(classOf[CustomerInfoDao]),
                  customerInfoExtDao: CustomerInfoExtDao = SpringContext.context.getBean(classOf[CustomerInfoExtDao]),
                  customerVirtualCardsDao: CustomerVirtualCardsDao = SpringContext.context.getBean(classOf[CustomerVirtualCardsDao]),
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
    * @param value JSON
    */
  def syncCustomerInfo(value: String): Unit = {
    // 将Json转换为对象
    val customerInfoModel = JSON.parseObject(value, classOf[CustomerInfoModel]).data
    // 通过证件号获取已绑定信息
    val customerInfoList = customerInfoDao.findByIdentityNo(customerInfoModel.identityNo)
    // 遍历openId删除已绑定信息
    customerInfoList.map(x => {
      customerVirtualCardsDao.deleteByOpenId(x.openId)
      customerInfoExtDao.deleteByOpenId(x.openId)
      customerInfoDao.deleteByOpenId(x.openId)
    })
    // 通过证件类型、证件号获取卡信息列表
    val cardInfos = getCardInfosByIdentityTypeAndNo(customerInfoModel.identityType, customerInfoModel.identityNo)
    // 通过卡号查询客户信息
    val cardHolderInfo = getCardHolderInfoByCardNo(cardInfos(0).getCardNo)
    // 构建实体并保存
    val customerInfo = new CustomerInfo
    customerInfo.openId = customerInfoModel.openId
    customerInfo.wechatNo = ""
    customerInfo.identityNo = customerInfoModel.identityNo
    customerInfo.bindingDate = new SimpleDateFormat("yyyyMMdd").format(new Date)
    customerInfo.customerId = ""
    customerInfo.accId = "24"
    customerInfo.mobilePhone = customerInfoModel.mobilePhone
    customerInfo.sex = cardHolderInfo.getGender
    customerInfo.familyName = cardHolderInfo.getFamilyName
    customerInfo.firstName = cardHolderInfo.getFirstName
    customerInfo.add1 = ""
    customerInfo.add2 = ""
    customerInfo.add3 = ""
    customerInfo.add4 = ""
    customerInfo.add5 = ""
    customerInfo.add6 = ""
    customerInfo.notice = ""
    customerInfo.identityType = customerInfoModel.identityType
    customerInfoDao.save(customerInfo)
  }

  /**
    * 保存卡信息(实体卡和虚拟卡)
    *
    * @param value JSON
    */
  def syncCustomerInfoExtAndVirtualCards(value: String): Unit = {
    // 将Json转换为对象
    val customerInfoModel = JSON.parseObject(value, classOf[CustomerInfoModel]).data
    // 通过openId获取客户信息
    val customerInfo = customerInfoDao.findByOpenId(customerInfoModel.openId)

    // 通过卡号查询客户信息
    val cardHolderInfo = getCardHolderInfoByCardNo(customerInfoModel.defCardNo)
    // 通过证件类型、证件号获取卡列表
    val cardInfoList = getCardInfosByIdentityTypeAndNo(customerInfoModel.identityType, customerInfoModel.identityNo)

    // 遍历卡列表通过卡号获取虚拟卡列表
    val customerInfoExtList = cardInfoList.map(x => {
      // 获取卡产品类型
      val cardCurrencyCodeAndStyle = getCardCurrencyCodeAndStyleResp(x.getCardNo)
      // 通过卡号获取虚拟卡
      val virtualCards = getVirtualCardsByCardNo(x.getCardNo)
      val virtualCardsCustomerList = virtualCards.map(y => {
        val customerVirtualCards = new CustomerVirtualCards
        customerVirtualCards.cardNo = y
        customerVirtualCards.openId = customerInfoModel.openId
        customerVirtualCards.mobilePhone = customerInfoModel.mobilePhone
        customerVirtualCards.sex = cardHolderInfo.getGender
        customerVirtualCards.familyName = cardHolderInfo.getFamilyName
        customerVirtualCards.firstName = cardHolderInfo.getFirstName
        customerVirtualCards.updateDate = new SimpleDateFormat("yyyyMMdd").format(new Date)
        customerVirtualCards.cardLastFourNumber = y.substring(x.getCardNo.length - 4)
        customerVirtualCards.notice = "1"
        customerVirtualCards.accId = "24"
        customerVirtualCards
      })

      val customerInfoExt = new CustomerInfoExt
      customerInfoExt.cardNo = x.getCardNo
      customerInfoExt.cardType = ""
      customerInfoExt.updateDate = new SimpleDateFormat("yyyyMMdd").format(new Date)
      customerInfoExt.infoId = customerInfo.id.toString
      customerInfoExt.openId = customerInfoModel.openId
      customerInfoExt.customerId = ""
      customerInfoExt.isDefault = if (x.getCardNo.equals(customerInfoModel.defCardNo)) "0" else "1"
      customerInfoExt.currency = WrapAsScala.asScalaBuffer(cardCurrencyCodeAndStyle.getCurrencyCodes).toList.mkString(",")
      customerInfoExt.style = cardCurrencyCodeAndStyle.getProductType
      //      customerInfoExt.dataDt = ""
      //      customerInfoExt.billDate = ""
      //      customerInfoExt.repayDate = ""
      //      customerInfoExt.curType01 = ""
      //      customerInfoExt.crtTermBal01 = ""
      //      customerInfoExt.lowestRepayNum01 = ""
      //      customerInfoExt.curType02 = ""
      //      customerInfoExt.crtTermBal02 = ""
      //      customerInfoExt.lowestRepayNum02 = ""
      //      customerInfoExt.reminderTime = ""
      customerInfoExt.reminderFlag = "0"
      customerInfoExt.notice = "1"
      customerInfoExt.cardLastFourNumber = x.getCardNo.substring(x.getCardNo.length - 4)
      customerInfoExt.mainFlag = x.getMianFlag

      (customerInfoExt, virtualCardsCustomerList)
    })

    for (customerInfoAll <- customerInfoExtList) {
      val customerInfoExt = customerInfoExtDao.save(customerInfoAll._1)
      for (customerVirtualCards <- customerInfoAll._2) {
        customerVirtualCards.mainCardId = customerInfoExt.id.toString
      }
      val list = WrapAsJava.asJavaIterable(customerInfoAll._2)
      customerVirtualCardsDao.save(list)
    }

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
  private def getCardCurrencyCodeAndStyleResp(cardNo: String): CardCurrencyCodeAndStyle = {
    val param = Map("tranSessionID" -> gcsSessionId, "reqChannelID" -> gcsChannelId, "cardNo" -> cardNo)
    httpClient.send(getCardCurrencyCodeAndStyleUrl, param, classOf[CardCurrencyCodeAndStyleResp]).getData
  }
}