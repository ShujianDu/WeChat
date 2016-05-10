package com.yada.wx.cbs

import java.net.URLEncoder

import com.typesafe.config.ConfigFactory
import com.yada.weixin.api.message.CallbackMessage.Names._
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

  private val (title, picUrl, url, mapURL, showNum, domainEbank, domainPic) = {
    val config = ConfigFactory.load()
    (config.getString("nearBank.title"),
      config.getString("nearBank.picURL"),
      config.getString("nearBank.URL"),
      config.getString("nearBank.mapURL"),
      config.getInt("nearBank.showNum"),
      config.getString("domain.ebank"),
      config.getString("domain.image"))
  }

  override val filter: (JsValue) => Boolean = jv => {
    (jv \ MsgType).as[String].equalsIgnoreCase(MSG_TYPE.Event) && (jv \ Event).as[String].equalsIgnoreCase(EVENT_TYPE.Location)
  }
  override val requestCreator: (JsValue) => JsValue = jv => jv
  override val process: (JsValue) => Future[CmdRespMessage] = jv => Future.successful {
    val latitude = (jv \ Latitude).toString().replace("\"", "")
    val longitude = (jv \ Longitude).toString().replace("\"", "")
    val banks = chinaBankInfoDao.findNearBanks(latitude, longitude)
    val item = NewsMessageItem(title, "", picUrl.replace("$_{realmName}", domainPic), url)
    val bs = WrapAsScala.asScalaBuffer(banks).toList
    val is = bs.map(item => {
      val url = s"${mapURL}latitude=${item.latitude}&longitude=${item.longitude}&userLatitude=$latitude" +
        s"&userLongitude=$longitude&name=${URLEncoder.encode(item.name, "UTF-8")}&Description=${URLEncoder.encode(item.addr, "UTF-8")}"
      NewsMessageItem(item.name, item.addr, "", url.replace("$_{realmName}", domainEbank))
    })
    val items = item +: is
    NewsCmdRespMessage(items.take(showNum))
  }
  override val responseCreator: (JsValue, CmdRespMessage) => Option[JsValue] = (req, resp) => Option {
    CmdRespMessage.toJson(req, resp)
  }
}
