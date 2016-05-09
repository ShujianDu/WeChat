package com.yada.wx.cbs

import java.net.URLEncoder

import com.typesafe.config.ConfigFactory
import com.yada.weixin.api.message.CallbackMessage
import com.yada.weixin.api.message.CallbackMessage.Names.{EVENT_TYPE, MSG_TYPE}
import com.yada.weixin.cb.server.MessageProc
import com.yada.wx.cb.data.service.jpa.dao.ChinaBankInfoDao
import play.api.libs.json.JsValue

import scala.collection.convert.WrapAsScala
import scala.concurrent.Future

/**
  * 附近网点
  */
class NearBankProc extends MessageProc[JsValue, CmdRespMessage] {
  private val chinaBankInfoDao: ChinaBankInfoDao = SpringContext.context.getBean(classOf[ChinaBankInfoDao])

  private val (title, picUrl, url, mapURL, showNum) = {
    val config = ConfigFactory.load()
    (config.getString("nearBank.title"),
      config.getString("nearBank.picURL"),
      config.getString("nearBank.URL"),
      config.getString("nearBank.mapURL"),
      config.getInt("nearBank.showNum"))
  }

  override val filter: (JsValue) => Boolean = jv => {
    (jv \ CallbackMessage.Names.MsgType).toString() == MSG_TYPE.Event && (jv \ CallbackMessage.Names.Event).toString() == EVENT_TYPE.Location
  }
  override val requestCreator: (JsValue) => JsValue = jv => jv
  override val process: (JsValue) => Future[CmdRespMessage] = jv => Future.successful {
    val latitude = (jv \ CallbackMessage.Names.Latitude).toString()
    val longitude = (jv \ CallbackMessage.Names.Longitude).toString()
    val banks = chinaBankInfoDao.findNearBanks(latitude, longitude)
    val item = NewsMessageItem(title, "", picUrl, url)
    val bs = WrapAsScala.asScalaBuffer(banks).toList
    val is = bs.map(item => {
      val url = s"${mapURL}latitude=${item.latitude}&longitude=${item.longitude}&userLatitude=$latitude" +
        s"&userLongitude=$longitude&name=${URLEncoder.encode(item.name, "UTF-8")}&Description=${URLEncoder.encode(item.addr, "UTF-8")}"
      NewsMessageItem(item.name, item.addr, "", url)
    })
    val items = item +: is
    NewsCmdRespMessage(items.take(showNum))
  }
  override val responseCreator: (JsValue, CmdRespMessage) => Option[JsValue] = (req, resp) => Option {
    CmdRespMessage.toJson(req, resp)
  }
}
