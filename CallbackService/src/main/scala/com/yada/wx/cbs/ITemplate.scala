package com.yada.wx.cbs

import java.net.URLEncoder

import com.typesafe.config.ConfigFactory
import com.yada.wx.cb.data.service.jpa.dao.{MsgComDao, NewsComDao}
import com.yada.wx.cb.data.service.jpa.model.{MsgCom, NewsCom}

/**
  * 图文信息模板
  */
trait ITemplate {
  private val (imageDomain, eBankDomain, applyActivityDomain, oauth2Domain, oauth2RedirectURL, weChatEBank) = {
    val cf = ConfigFactory.load()
    (cf.getString("domain.image"),
      cf.getString("domain.ebank"),
      cf.getString("domain.applyActivity"),
      cf.getString("domain.oauth2"),
      cf.getString("domain.oauth2RedirectURL"),
      cf.getString("domain.wechatebank"))
  }

  protected def msgComDao: MsgComDao = SpringContext.context.getBean(classOf[MsgComDao])

  protected def newsComDao: NewsComDao = SpringContext.context.getBean(classOf[NewsComDao])

  /**
    * 创建响应信息
    *
    * @param findMsgCom    查询MsgCom
    * @param findNewsCom   查询NewsCom
    * @param normalReplace 普通的字符替换
    * @param repeatReplace 重复的字符替换
    * @return
    */
  protected def createRespMsg(findMsgCom: () => MsgCom, findNewsCom: String => List[NewsCom], normalReplace: String => String, repeatReplace: String => List[String]): CmdRespMessage = {
    val msgCom = findMsgCom()
    msgCom.msg_type match {
      case "1" => // 文本信息
        val c = replace(notEmpty(msgCom.content), normalReplace, repeatReplace).replaceOauth2.replaceApplyActivity
        TextCmdRespMessage(c)
      case "3" => // 图文信息
        val newsList = findNewsCom(msgCom.msg_id)
        val itemList = newsList.map(newCom => {
          val title = replace(notEmpty(newCom.title), normalReplace, repeatReplace)
          val des = replace(notEmpty(newCom.description), normalReplace, repeatReplace).replaceOauth2.replaceApplyActivity
          val picurl = notEmpty(newCom.picurl).replacePic
          val url = notEmpty(newCom.pic_link_url).replaceOauth2.replaceApplyActivity
          NewsMessageItem(title, des, picurl, url)
        })
        NewsCmdRespMessage(itemList)
    }
  }

  private def notEmpty(msg: String): String = if (msg == null) "" else msg


  implicit class ReplaceURL(url: String) {
    implicit def replaceOauth2: String = {
      val tURL = (oauth2RedirectURL + url).replace("$_{realmName}", eBankDomain).replace("$_{wechatebank}", weChatEBank)
      oauth2Domain.replace("$_{redirectURI}", URLEncoder.encode(tURL, "UTF-8"))
    }

    implicit def replaceApplyActivity: String = url.replace("$_{applyactivity}", applyActivityDomain)

    implicit def replacePic: String = url.replace("$_{realmName}", imageDomain)

  }

  /**
    *
    * @param template      模板
    * @param normalReplace 普通替换
    * @param repeatReplace 循环体替换
    * @return
    */
  private def replace(template: String, normalReplace: String => String, repeatReplace: String => List[String]): String = {
    val content = new StringBuilder
    val start = template.indexOf("<#>")
    val end = template.indexOf("<_#>")
    if (start != -1 && end != -1) {
      content.append(template.substring(0, start))
      val mTemplate = template.substring(start + 3, end)
      repeatReplace(mTemplate).foreach(item => {
        content.append(item)
      })
      content.append(template.substring(end + 4))
      normalReplace(content.toString())
    } else {
      val rp = repeatReplace(template)
      normalReplace(if (rp.isEmpty) template else rp.head)
    }
  }
}
