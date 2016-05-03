package com.yada.wx.cbs

import com.yada.weixin.cb.server.MessageProc
import com.yada.wx.cb.data.service.CMDService
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.Future

/**
  * 命令处理
  */
class CmdProc(cmdService: CMDService, cmdBiz: CmdBiz) extends MessageProc[JsValue, CmdRespMessage] {


  override val filter: (JsValue) => Boolean = jv => {
    cmdService.exist((jv \ "Content").as[String])
  }
  override val requestCreator: (JsValue) => JsValue = jv => jv
  override val process: (JsValue) => Future[CmdRespMessage] = jv => Future.successful {
    cmdBiz.handle((jv \ "Content").as[String], (jv \ "FromUserName").as[String])
  }
  override val responseCreator: (JsValue, CmdRespMessage) => Option[JsValue] = (req, resp) => Option {
    resp match {
      case text: TextCmdRespMessage =>
        Json.obj(
          "ToUserName" -> (req \ "FromUserName").as[String],
          "FromUserName" -> (req \ "ToUserName").as[String],
          "CreateTime" -> System.currentTimeMillis() / 1000,
          "MsgType" -> "text",
          "Content" -> text.content
        )
      case news: NewsCmdRespMessage =>
        Json.obj(
          "ToUserName" -> (req \ "FromUserName").as[String],
          "FromUserName" -> (req \ "ToUserName").as[String],
          "CreateTime" -> System.currentTimeMillis() / 1000,
          "MsgType" -> "news",
          "ArticleCount" -> news.items.size.toString,
          "Articles" -> news.items.map(item => {
            Json.obj("Title" -> item.title,
              "Description" -> item.des,
              "PicUrl" -> item.picUrl,
              "Url" -> item.url)
          }).map(item => {
            Json.obj("item" -> item)
          })
        )
    }


  }
}


trait CmdRespMessage

case class TextCmdRespMessage(content: String) extends CmdRespMessage

case class NewsCmdRespMessage(items: List[NewsMessageItem]) extends CmdRespMessage

case class NewsMessageItem(title: String, des: String, picUrl: String, url: String)
