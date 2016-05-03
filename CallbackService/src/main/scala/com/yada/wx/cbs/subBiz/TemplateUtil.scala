package com.yada.wx.cbs.subBiz

/**
  * Created by locky on 2016/5/3.
  */
object TemplateUtil {
  /**
    *
    * @param template      模板
    * @param normalReplace 普通替换
    * @param repeatReplace 循环体替换
    * @return
    */
  def replace(template: String, normalReplace: String => String, repeatReplace: String => List[String]): String = {
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
      repeatReplace(normalReplace(template)).head
    }
  }
}
