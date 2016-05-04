package com.yada.wx.cbs

import com.yada.weixin.cb.server.MessageProc
import com.yada.wx.cb.data.service.{CMDService, SpringContext}
import com.yada.wx.cb.data.service.jpa.dao.CommandDao
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.Future

/**
  * 命令处理
  */
class CmdProc() extends MessageProc[JsValue, CmdRespMessage] {
  private[cbs] var cmdDao: CommandDao = SpringContext.context.getBean(classOf[CommandDao])
  private[cbs] var cmdBiz: CmdBiz = new CmdBiz()

  override val filter: (JsValue) => Boolean = jv => {
    (jv \ "MsgType").as[String] == "text" && cmdDao.exists((jv \ "Content").as[String])
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

/**
  * 命令响应信息
  */
trait CmdRespMessage

/**
  * 文本类型响应
  *
  * @param content 文本内容
  */
case class TextCmdRespMessage(content: String) extends CmdRespMessage

/**
  * 图文信息响应
  *
  * @param items 图文信息列表
  */
case class NewsCmdRespMessage(items: List[NewsMessageItem]) extends CmdRespMessage

/**
  * 图文信息
  *
  * @param title  图文消息标题
  * @param des    图文消息描述
  * @param picUrl 图片链接
  * @param url    点击图文消息跳转链接
  */
case class NewsMessageItem(title: String, des: String, picUrl: String, url: String)
